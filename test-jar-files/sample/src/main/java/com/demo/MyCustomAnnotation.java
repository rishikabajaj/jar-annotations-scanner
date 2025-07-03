package com.demo;

// File: MyCustomAnnotation.java
import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD, ElementType.FIELD})
public @interface MyCustomAnnotation {
    String value() default "default value";
}
