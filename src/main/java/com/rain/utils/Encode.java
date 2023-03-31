package com.rain.utils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Encode {

	String encodingKey() default "ligF4j1mbc4aPh5@0LPgvdpjdksaj$fhsd8=";
}
