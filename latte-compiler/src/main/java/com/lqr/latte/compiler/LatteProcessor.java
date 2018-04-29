package com.lqr.latte.compiler;

import com.google.auto.service.AutoService;
import com.lqr.latte.annotations.AppRegisterGenerator;
import com.lqr.latte.annotations.EntryGenerator;
import com.lqr.latte.annotations.PayEntryGenerator;

import java.lang.annotation.Annotation;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.AnnotationValueVisitor;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;

/**
 * 创建者：CSDN_LQR
 * 描述：文件生成器注解处理器
 */
@AutoService(Processor.class)
public class LatteProcessor extends AbstractProcessor {

    /**
     * 指定工程中要处理的注解类
     */
    private Set<Class<? extends Annotation>> getSupportedAnnotations() {
        final Set<Class<? extends Annotation>> annotations = new LinkedHashSet<>();
        annotations.add(EntryGenerator.class);
        annotations.add(PayEntryGenerator.class);
        annotations.add(AppRegisterGenerator.class);
        return annotations;
    }

    /**
     * 指定工程中要处理的注解类类型
     */
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> types = new LinkedHashSet<>();
        final Set<Class<? extends Annotation>> supportAnnotations = getSupportedAnnotations();
        for (Class<? extends Annotation> annotation : supportAnnotations) {
            types.add(annotation.getCanonicalName());
        }
        return types;
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment environment) {
        generateEntryCode(environment);
        generatePayEntryCode(environment);
        generateAppRegisterCode(environment);
        return true;
    }

    private void scan(RoundEnvironment env,
                      Class<? extends Annotation> annotation,
                      AnnotationValueVisitor visitor) {
        // 1、获取到工程使用了指定注解的所有元素并遍历
        for (Element typeElement : env.getElementsAnnotatedWith(annotation)) {
            // 2、得到这些元素中的注解镜像集合
            final List<? extends AnnotationMirror> annotationMirrors =
                    typeElement.getAnnotationMirrors();
            // 3、遍历注解镜像，将注解属性值交给visitor解析处理
            for (AnnotationMirror annotationMirror : annotationMirrors) {
                final Map<? extends ExecutableElement, ? extends AnnotationValue> elementValues
                        = annotationMirror.getElementValues();
                for (Map.Entry<? extends ExecutableElement, ? extends AnnotationValue> entry :
                        elementValues.entrySet()) {
                    entry.getValue().accept(visitor, null);
                }
            }
        }
    }

    /**
     * 生成微信入口类文件
     */
    private void generateEntryCode(RoundEnvironment env) {
        final EntryVisitor visitor = new EntryVisitor();
        visitor.setFiler(processingEnv.getFiler());
        scan(env, EntryGenerator.class, visitor);
    }

    /**
     * 生成微信支付入口类文件
     */
    private void generatePayEntryCode(RoundEnvironment env) {
        final PayEntryVisitor visitor = new PayEntryVisitor();
        visitor.setFiler(processingEnv.getFiler());
        scan(env, PayEntryGenerator.class, visitor);
    }

    /**
     * 生成微信文件接收器文件
     */
    private void generateAppRegisterCode(RoundEnvironment env) {
        final AppRegisterVisitor visitor = new AppRegisterVisitor();
        visitor.setFiler(processingEnv.getFiler());
        scan(env, AppRegisterGenerator.class, visitor);
    }
}
