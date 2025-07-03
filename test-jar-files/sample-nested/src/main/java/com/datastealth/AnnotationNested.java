package com.datastealth;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@interface Special {}

public class AnnotationNested {
    @Special
    public void onlyMethod() {}
}
