package com.huaweicloud.agentcore.tests.application;

/**
 * 测试类匹配相关功能
 *
 * @author luanwenfei
 * @since 2022-10-22
 */
public class ClassMatchersTest {
    public void testClassMatchers(){
        AnnotationTest.staticFunction(false);
        AnnotationsTest.staticFunction(false);
        SuperTypeTest.staticFunction(false);
        SuperTypesTest.staticFunction(false);
        PrefixNameTest.staticFunction(false);
        NameInfixTest.staticFunction(false);
        NameTestSuffix.staticFunction(false);
    }
}
