package com.ys.yoosir.zzshow.di.scope;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;

import javax.inject.Scope;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @version 1.0
 * @author   yoosir
 * Created by Administrator on 2016/12/23 0023.
 */
@Scope
@Documented
@Retention(RUNTIME)
public @interface ContextLifeScope {

    String value() default "Application";

}
