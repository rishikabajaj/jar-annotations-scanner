package com.datastealth;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD, ElementType.FIELD})
@interface MyAnnotation {}

@MyAnnotation
public class AnnotatedClass {

    @MyAnnotation
    private String field;

    @MyAnnotation
    public void method() {}
}
