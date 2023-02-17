package com.mw.middleware.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommonUtilTest {

    @Test
    void isValidIp() {
        // 测试 CommonUtil.isValidIp

        // 测试空字符串
        assertFalse(CommonUtil.isValidIp(""));

        // 测试null
        assertFalse(CommonUtil.isValidIp(null));

        // 测试非法ip
        assertFalse(CommonUtil.isValidIp("127.0.0.256"));

        // 测试非法ip
        assertFalse(CommonUtil.isValidIp("12"));

        // 测试非法ip
        assertFalse(CommonUtil.isValidIp("ss"));

    }

    @Test
    void isValidPort() {
        // 测试 CommonUtil.isValidPort

        // 测试空字符串
        assertFalse(CommonUtil.isValidPort(""));

        // 测试null
        assertFalse(CommonUtil.isValidPort(null));

        // 测试非法port
        assertFalse(CommonUtil.isValidPort("65536"));

        // 测试非法port
        assertFalse(CommonUtil.isValidPort("ss"));
    }
}