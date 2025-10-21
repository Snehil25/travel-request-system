package com.tx.api.test.automate.security;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Getter
@Builder
public class BasicAuth {
    private String username;
    private String password;
}
