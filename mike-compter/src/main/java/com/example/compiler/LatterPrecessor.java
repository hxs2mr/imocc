package com.example.compiler;

import com.example.annotations.AppRegisterGenerator;
import com.example.annotations.EnterGenerator;
import com.example.annotations.PayEntryGenerator;
import com.google.auto.service.AutoService;

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
 * Created by microtech on 2017/11/17.
 */

@AutoService(Processor.class)
public class LatterPrecessor extends AbstractProcessor{
    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        generateEntryCode(roundEnvironment);
        generatePayEntryCode(roundEnvironment);
        generateAppRegisterCode(roundEnvironment);
        return true;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        final Set<String> types = new LinkedHashSet<>();
        final Set<Class<? extends Annotation>> supportAnnotations = getSupportedAnnotations();
        for (Class<? extends Annotation> annotation:supportAnnotations){
            types.add(annotation.getCanonicalName());
        }
        return types;
    }

    private  Set<Class<? extends Annotation>> getSupportedAnnotations(){
        final Set<Class<? extends Annotation>> annotations = new LinkedHashSet<>();
        annotations.add(EnterGenerator.class);
        annotations.add(PayEntryGenerator.class);
        annotations.add(AppRegisterGenerator.class);
        return annotations;
    }
    private void scan(RoundEnvironment env, Class<? extends Annotation> annoation, AnnotationValueVisitor visitor){
        for (Element typeElement:env.getElementsAnnotatedWith(annoation)){
            final List<? extends AnnotationMirror> annotationMirrors = typeElement.getAnnotationMirrors();

            for(AnnotationMirror annotationMirror:annotationMirrors){
                final Map<? extends ExecutableElement,? extends AnnotationValue> elementValues
                        = annotationMirror.getElementValues();

                for (Map.Entry<? extends ExecutableElement,? extends AnnotationValue> entry:elementValues.entrySet()){
                    entry.getValue().accept(visitor,null);
                }
            }
        }
    }
    private void generateEntryCode(RoundEnvironment env){
            final EntryVisitor entryVisitor = new EntryVisitor();
        entryVisitor.setFiler(processingEnv.getFiler());
        scan(env,EnterGenerator.class,entryVisitor);
    }
    private void generatePayEntryCode(RoundEnvironment env){
        final PayEntryVisitor payEntryVisitor = new PayEntryVisitor();
        payEntryVisitor.setFiler(processingEnv.getFiler());
        scan(env,PayEntryGenerator.class,payEntryVisitor);
    }
    private void generateAppRegisterCode(RoundEnvironment env){
        final AppRegisterVisitor entryVisitor = new AppRegisterVisitor();
        entryVisitor.setFiler(processingEnv.getFiler());
        scan(env,AppRegisterGenerator.class,entryVisitor);
    }
}
