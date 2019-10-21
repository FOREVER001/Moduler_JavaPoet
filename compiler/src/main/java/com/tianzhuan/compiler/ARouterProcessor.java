package com.tianzhuan.compiler;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import com.tianzhuan.annotation.ARouter;

import java.io.IOException;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedOptions;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;

@AutoService(Processor.class)
@SupportedAnnotationTypes({"com.tianzhuan.annotation.ARouter"})
@SupportedSourceVersion(SourceVersion.RELEASE_7)
@SupportedOptions("content")
public class ARouterProcessor extends AbstractProcessor {
    private Elements elementsUtils;
    private Types typesUtils;
    private Messager messager;
    private Filer filer;


    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        elementsUtils=processingEnv.getElementUtils();
        typesUtils=processingEnv.getTypeUtils();
        messager=processingEnv.getMessager();
        filer=processingEnv.getFiler();
        String content = processingEnv.getOptions().get("content");
        messager.printMessage(Diagnostic.Kind.NOTE,content);
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        if(set.isEmpty())return false;
        //获取所有被@ARouter注解的类节点
        Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(ARouter.class);
        for (Element element : elements) {
            //类节点上一个节点：包节点
            String packageName = elementsUtils.getPackageOf(element).getQualifiedName().toString();
            //获取简单类名
            String className = element.getSimpleName().toString();
            messager.printMessage(Diagnostic.Kind.NOTE,"被@ARouter注解的类有："+className);
            //最终生成的类文件名
            String finalClassName = className + "$$ARouter";
            ARouter aRouter = element.getAnnotation(ARouter.class);

            //方法生成
     //  public static Class<?> findTargetClass(String path){
       //  return path.equalsIgnoreCase("/app/MainActivity")?MainActivity.class :null;
            MethodSpec main = MethodSpec.methodBuilder("findTargetClass")
                    .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                    .returns(Class.class)
                    .addParameter(String.class, "path")
                    .addStatement("return path.equalsIgnoreCase($S) ? $T.class :null",
                            aRouter.path(), ClassName.get((TypeElement) element))
                    .build();

            //类生成
//            public class XActivity$$ARouter {
            TypeSpec typeSpec = TypeSpec.classBuilder(finalClassName)
                    .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                    .addMethod(main)
                    .build();
            //包生成
            //package com.tianzhuan.net_modular_javapoet.test;
            JavaFile javaFile = JavaFile.builder(packageName, typeSpec)
                    .build();

            try {
                javaFile.writeTo(filer);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
