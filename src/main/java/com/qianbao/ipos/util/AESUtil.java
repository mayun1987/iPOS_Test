package com.qianbao.ipos.util;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * @Description: AES工具类
 */
public class AESUtil {
    private static final String ALGORITHM = "AES";
    private static final String ALGORITHM_PADDING = "AES/CBC/PKCS5Padding";

    private static final String DEFAULT_CHATSET = "utf-8";

    public static final String VIPARA = "1269571569321021";

    /**
     * 加密
     *
     * @param content   待加密内容
     * @param secretKey 16位密钥因子
     * @return 加密内容
     * @throws Exception
     */
    public static String encrypt(String content, String secretKey) throws Exception {
        IvParameterSpec zeroIv = new IvParameterSpec(VIPARA.getBytes());
        SecretKeySpec key = new SecretKeySpec(secretKey.getBytes(), ALGORITHM);
        //创建密码器
        Cipher cipher = Cipher.getInstance(ALGORITHM_PADDING);
        byte[] byteContent = content.getBytes(DEFAULT_CHATSET);
        cipher.init(Cipher.ENCRYPT_MODE, key, zeroIv);// 初始化  
        byte[] result = cipher.doFinal(byteContent);
        return Base64.encodeBase64String(result);
    }

    /**
     * 解密
     *
     * @param content   解密字符数组
     * @param secretKey 密钥对象
     * @return 解密内容
     * @throws Exception
     */
    public static String decrypt(String content, String secretKey) throws Exception {
        IvParameterSpec zeroIv = new IvParameterSpec(VIPARA.getBytes());
        SecretKeySpec key = new SecretKeySpec(secretKey.getBytes(), ALGORITHM);
        //创建密码器
        Cipher cipher = Cipher.getInstance(ALGORITHM_PADDING);
        cipher.init(Cipher.DECRYPT_MODE, key, zeroIv);
        byte[] result = cipher.doFinal(Base64.decodeBase64(content));
        return new String(result);
    }

}
