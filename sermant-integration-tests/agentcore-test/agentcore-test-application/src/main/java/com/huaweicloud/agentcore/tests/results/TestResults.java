package com.huaweicloud.agentcore.tests.results;

/**
 * 对agentcore集成测试的结果
 *
 * @author luanwenfei
 * @since 2022-10-22
 */
public class TestResults {
    /**
     * Start:Test Load Configuration
     */
    public static final TestCase LOAD_BOOTSTRAP_CONFIGURATION = 
            new TestCase("Test load bootstrap configuration.");

    public static final TestCase LOAD_AGENT_CONFIGURATION = 
            new TestCase("Test load bootstrap configuration.");

    public static final TestCase LOAD_PLUGIN_CONFIGURATION = 
            new TestCase("Test load bootstrap configuration.");

    /**
     * Start:Test Dynamic Config
     */
    public static final TestCase ZK_CREATE_NODE = new TestCase("Test watching create node in zookeeper.");

    public static final TestCase ZK_DELETE_NODE = new TestCase("Test watching delete node in zookeeper.");

    public static final TestCase ZK_MODIFY_NODE = new TestCase("Test watching modify node in zookeeper.");

    public static final TestCase KIE_CREATE_NODE = new TestCase("Test watching create node in kie.");

    public static final TestCase KIE_DELETE_NODE = new TestCase("Test watching delete node in kie.");

    public static final TestCase KIE_MODIFY_NODE = new TestCase("Test watching modify node in kie.");

    /**
     * Start:Test Class Matcher
     */
    public static final TestCase MATCHER_CLASS_BY_ANNOTATION = new TestCase("Test matcher class by single-annotation.");

    public static final TestCase MATCHER_CLASS_BY_ANNOTATIONS = new TestCase("Test matcher class by multi-annotation.");

    public static final TestCase MATCHER_CLASS_BY_CLASS_NAME_EXACTLY =
        new TestCase("Test matcher class by the exact class-name.");

    public static final TestCase MATCHER_CLASS_BY_CLASS_NAME_PREFIX =
        new TestCase("Test matcher class by the class-name's prefix.");

    public static final TestCase MATCHER_CLASS_BY_CLASS_NAME_INFIX =
        new TestCase("Test matcher class by the class-name's infix.");

    public static final TestCase MATCHER_CLASS_BY_CLASS_NAME_SUFFIX =
        new TestCase("Test matcher class by the class-name's suffix.");

    public static final TestCase MATCHER_CLASS_BY_SUPER_TYPE = new TestCase("Test matcher class by single-superType.");

    public static final TestCase MATCHER_CLASS_BY_SUPER_TYPES = new TestCase("Test matcher class by multi-superType.");

    /**
     * Start:Test Method Matcher
     */
    public static final TestCase MATCHER_METHOD_BY_ANNOTATION =
        new TestCase("Test matcher method by single-annotation.");

    public static final TestCase MATCHER_METHOD_BY_ANNOTATIONS =
        new TestCase("Test matcher method by multi-annotation.");

    public static final TestCase MATCHER_METHOD_BY_METHOD_NAME_EXACTLY =
        new TestCase("Test matcher method by the exact method-name.");

    public static final TestCase MATCHER_METHOD_BY_METHOD_NAME_PREFIX =
        new TestCase("Test matcher method by the method-name's prefix.");

    public static final TestCase MATCHER_METHOD_BY_METHOD_NAME_INFIX =
        new TestCase("Test matcher method by the method-name's infix.");

    public static final TestCase MATCHER_METHOD_BY_METHOD_NAME_SUFFIX =
        new TestCase("Test matcher method by the method-name's suffix.");

    public static final TestCase MATCHER_CONSTRUCTOR = new TestCase("Test matcher constructor of class.");

    public static final TestCase MATCHER_STATIC_METHODS = new TestCase("Test matcher static methods of class.");

    public static final TestCase MATCHER_METHOD_BY_RETURN_TYPE = new TestCase("Test matcher method by return type.");

    public static final TestCase MATCHER_METHOD_BY_ARGUMENTS_COUNT =
        new TestCase("Test matcher method by the count of arguments.");

    public static final TestCase MATCHER_METHOD_BY_ARGUMENTS_TYPE =
        new TestCase("Test matcher method by the type of arguments.");

    /**
     * Start:Test Ability To Enhance.
     */
    public static final TestCase MODIFY_MEMBER_FIELDS = new TestCase("Test modify the member fields of object.");

    public static final TestCase MODIFY_STATIC_FIELDS = new TestCase("Test modify the static fields of object.");

    public static final TestCase MODIFY_ARGUMENTS = new TestCase("Test modify the arguments of method.");

    public static final TestCase MODIFY_RESULT = new TestCase("Test modify the result of method.");

    public static final TestCase SKIP_METHOD = new TestCase("Test skip the method.");
}
