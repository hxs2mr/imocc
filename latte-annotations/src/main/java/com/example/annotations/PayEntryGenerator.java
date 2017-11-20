package com.example.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by microtech on 2017/11/17.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.SOURCE)//源码注解  打包之后不占空间编译期间
public @interface PayEntryGenerator {
    String packageName();
    Class<?> payEntryTemplete();
}
