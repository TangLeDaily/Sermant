package com.huaweicloud.agentcore.tests.common;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 实例注解，用于座位注解拦截的示例
 *
 * @author luanwenfei
 * @since 2022-10-13
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface TestAnnotationB {
}
