package com.tx.api.test.automate.utils;

import com.tx.api.test.automate.security.BasicAuth;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserContext {
    private String authType;
    private BasicAuth basicAuth;
    private boolean traceLogEnabled;
    private boolean authEnabled;
}
