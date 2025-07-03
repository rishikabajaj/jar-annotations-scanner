package com.demo;

// File: AnnotatedExample.java
@MyCustomAnnotation("This is a custom annotation on class")
@Deprecated
public class Example {

    @MyCustomAnnotation("Custom annotation on a field")
    private String name;

    @Deprecated
    public void oldMethod() {
        System.out.println("This method is deprecated.");
    }

    @MyCustomAnnotation("Custom annotation on a method")
    public void customAnnotatedMethod() {
        System.out.println("This method uses a custom annotation.");
    }
}
