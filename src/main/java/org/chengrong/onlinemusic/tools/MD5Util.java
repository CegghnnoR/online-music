package org.chengrong.onlinemusic.tools;

import org.apache.commons.codec.digest.DigestUtils;

public class MD5Util {
    // 定义一个固定的盐值
    private static final String salt = "myFixedSaltValue123!";

    public static String md5(String src) {
        return DigestUtils.md5Hex(src);
    }

    /**
     * 第一次加密：模拟前端自己加密，然后传到后端
     * @param inputPass
     * @return
     */
    public static String inputPassToFormPass(String inputPass) {
        String str = "" + salt.charAt(1) + salt.charAt(3) + inputPass
                + salt.charAt(5) + salt.charAt(6);
        return md5(str);
    }

    /**
     * 第 2 次加密
     * @param formPass 前端加密过的密码
     * @param salt 用户数据库中的盐值
     * @return
     */
    public static String formPassToDBPass(String formPass, String salt) {
        String str = "" + salt.charAt(0) + salt.charAt(2) + formPass + salt.charAt(5)
                + salt.charAt(4);
        return md5(str);
    }

    /**
     * 上面两个函数合到一起
     * @param inputPass
     * @param saltDB
     * @return
     */
    public static String inputPassToDBPass(String inputPass, String saltDB) {
        String formPass = inputPassToFormPass(inputPass);
        String dbPass = formPassToDBPass(formPass, saltDB);
        return dbPass;
    }

    public static void main(String[] args) {
        System.out.println("第 1 次加密：" + inputPassToFormPass("123456"));
        System.out.println("第 2 次加密：" + formPassToDBPass(inputPassToFormPass("123456"), salt));
        System.out.println("进行 2 次加密：" + inputPassToDBPass("123456", salt));
    }
}
