package main;

import java.lang.annotation.*;  
// import java.lang.reflect.*;  
  
@Retention(RetentionPolicy.RUNTIME)  
@Target(ElementType.METHOD)  
public @interface TestCase{}