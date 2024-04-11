package org.chengrong.onlinemusic.tools;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class BCryptTest {
    public static void main(String[] args) {
        String password = "123456";
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String newPassword = bCryptPasswordEncoder.encode(password);
        System.out.println("加密的密码为：" + newPassword);

        boolean same_password_result = bCryptPasswordEncoder.matches(password, newPassword);
        System.out.println("正确对比结果: " + same_password_result);
        boolean other_password_result = bCryptPasswordEncoder.matches("851815", newPassword);
        System.out.println("错误对比结果: " + other_password_result);
    }
}
