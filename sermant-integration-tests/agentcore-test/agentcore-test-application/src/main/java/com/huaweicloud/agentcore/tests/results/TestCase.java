package com.huaweicloud.agentcore.tests.results;

/**
 * agentcore集成测试用例
 *
 * @author luanwenfei
 * @since 2022-10-22
 */
public class TestCase {
    private String description;

    private boolean result;

    public TestCase(String description) {
        this.description = description;
        this.result = false;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "{'" + description + '\'' +
                ", result=" + result +
                '}';
    }
}
