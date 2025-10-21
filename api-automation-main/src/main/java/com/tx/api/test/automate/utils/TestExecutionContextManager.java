package com.tx.api.test.automate.utils;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TestExecutionContextManager {

    private static final ThreadLocal<TestExecutionContext> threadLocalTestContext = new ThreadLocal<>();

    public static TestExecutionContext getTestExecutionContext() {
        return threadLocalTestContext.get();
    }

    public static void setTestExecutionContext(TestExecutionContext testExecutionContext) {
        threadLocalTestContext.set(testExecutionContext);
    }

    public static void unload() {
        threadLocalTestContext.remove();
    }

}
