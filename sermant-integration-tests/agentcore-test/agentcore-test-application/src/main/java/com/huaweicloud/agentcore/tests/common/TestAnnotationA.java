package com.huaweicloud.agentcore.tests.common;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 示例注解，用于作为注解拦截的示例
 *
 * @author luanwenfei
 * @version 1.0.0
 * @since 2022-10-13
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface TestAnnotationA {
}
