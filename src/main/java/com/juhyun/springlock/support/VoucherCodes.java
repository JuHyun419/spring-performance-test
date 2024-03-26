package com.juhyun.springlock.support;

import java.util.concurrent.ThreadLocalRandom;

public class VoucherCodes {

    private static final ThreadLocalRandom RANDOM = ThreadLocalRandom.current();

    public static String generate(CodeConfig codeConfig) {
        StringBuilder sb = new StringBuilder();
        char[] chars = codeConfig.getCharset().toCharArray();

        // TODO: check prefix nullable

        for (int i = 0; i < codeConfig.getLength(); i++) {
            sb.append(chars[RANDOM.nextInt(chars.length)]);
        }

        return sb.toString();
    }

}
