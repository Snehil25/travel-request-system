package com.tx.api.test.automate.utils;

public class UserContextManager {
    private static final ThreadLocal<UserContext> threadLocalUserContext = new ThreadLocal<>();

    public static UserContext getUserContext() {
        return threadLocalUserContext.get();
    }

    public static void setUserContext(UserContext userContext) {
        threadLocalUserContext.set(userContext);
    }

    public static void unload() {
        threadLocalUserContext.remove();
    }
}
