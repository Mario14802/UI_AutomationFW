package com.automationExercise.drivers;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/*\
 * simple @ to distinguish between UI Test adn API tests
 * as the api do not need drivers of screen or recorder
 *
 * */


@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface UITest {
}
