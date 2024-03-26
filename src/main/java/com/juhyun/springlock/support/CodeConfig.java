package com.juhyun.springlock.support;

import lombok.Getter;

@Getter
public class CodeConfig {

    private static final String NUMBERS = "0123456789";
    private static final int DEFAULT_LENGTH = 12;

    private final int length;
    private final String charset;
    private final String prefix;

    public CodeConfig(String prefix) {
        this.length = DEFAULT_LENGTH;
        this.charset = NUMBERS;
        this.prefix = prefix;
    }
}
