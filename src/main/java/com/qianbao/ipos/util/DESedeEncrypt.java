package com.qianbao.ipos.util;

import java.security.Key;
import java.util.UUID;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;

import org.apache.commons.codec.binary.Base64;

/**
 * 3des加密解密
 *
 * @author lishiwei
 *
 */
public class DESedeEncrypt {

    /**
     * 密钥算法
     */
    private static final String KEY_ALGORITHM = "DESede";
    private static final String DEFAULT_CIPHER_ALGORITHM = "DESede/ECB/ISO10126Padding";
    public static final String FINANCE_CIPHER_ALGORITHM = "DESede/ECB/PKCS5Padding";//财务对账填充方式

    /**
     * 转换密钥
     *
     * @param key 二进制密钥
     * @return Key 密钥
     * @throws Exception
     */
    private static Key initKey(byte[] key) throws Exception {
        DESedeKeySpec desKey = new DESedeKeySpec(key);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(KEY_ALGORITHM);
        return keyFactory.generateSecret(desKey);
    }

    /**
     * 加密
     *
     * @param data 待加密数据
     * @param key 二进制密钥
     * @return byte[] 加密数据
     * @throws Exception
     */
    public static String encrypt(String key, String data) throws Exception {
        return encrypt(key.getBytes(), data.getBytes(), DEFAULT_CIPHER_ALGORITHM);
    }


    /**
     * 加密
     *
     * @param data 待加密数据
     * @param key 二进制密钥
     * @param cipherAlgorithm 加密算法/工作模式/填充方式
     * @return byte[] 加密数据
     * @throws Exception
     */
    public static String encrypt(byte[] key, byte[] data, String cipherAlgorithm) throws Exception {
        Key k = initKey(key);
        byte[] tmp = encrypt(k, data, cipherAlgorithm);
        return Base64.encodeBase64String(tmp);
    }

    /**
     * 加密
     *
     * @param data 待加密数据
     * @param key 密钥
     * @param cipherAlgorithm 加密算法/工作模式/填充方式
     * @return byte[] 加密数据
     * @throws Exception
     */
    public static byte[] encrypt(Key key, byte[] data, String cipherAlgorithm) throws Exception {
        // 实例化
        Cipher cipher = Cipher.getInstance(cipherAlgorithm);
        // 使用密钥初始化，设置为加密模式
        cipher.init(Cipher.ENCRYPT_MODE, key);
        // 执行操作
        return cipher.doFinal(data);
    }

    /**
     * 解密
     *
     * @param data 待解密数据
     * @param key 二进制密钥
     * @return byte[] 解密数据
     * @throws Exception
     */
    public static String decrypt(String key, String data) throws Exception {
        return decrypt(key.getBytes(), Base64.decodeBase64(data), DEFAULT_CIPHER_ALGORITHM);
    }

    /**
     * 解密
     *
     * @param data 待解密数据
     * @param key 二进制密钥
     * @param cipherAlgorithm 加密算法/工作模式/填充方式
     * @return byte[] 解密数据
     * @throws Exception
     */
    public static String decrypt(byte[] key, byte[] data, String cipherAlgorithm) throws Exception {
        Key k = initKey(key);
        return decrypt(k, data, cipherAlgorithm);
    }

    /**
     * 解密
     *
     * @param data 待解密数据
     * @param key 密钥
     * @param cipherAlgorithm 加密算法/工作模式/填充方式
     * @return byte[] 解密数据
     * @throws Exception
     */
    public static String decrypt(Key key, byte[] data, String cipherAlgorithm) throws Exception {
        Cipher cipher = Cipher.getInstance(cipherAlgorithm);
        cipher.init(Cipher.DECRYPT_MODE, key);
        return new String(cipher.doFinal(data));
    }

    /**
     * 生成密钥key
     *
     * @return
     */
    public static String generateKey(String targat) {
        String uuid = UUID.randomUUID().toString();
        return targat + Base64.encodeBase64String(uuid.getBytes());
    }

    public static byte[] initKey() throws Exception {
        KeyGenerator kg = KeyGenerator.getInstance(KEY_ALGORITHM);
        kg.init(168);
        SecretKey secretKey = kg.generateKey();
        return secretKey.getEncoded();
    }

    public static String initKeyStr() throws Exception {
        return Base64.encodeBase64String(initKey());
    }

}
