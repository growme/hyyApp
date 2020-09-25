package com.ccnet.admin.bh.utils;

import java.lang.reflect.Array;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.binary.StringUtils;
import org.apache.commons.codec.digest.DigestUtils;


public class PasswordHelper {

	
	/**
	 * 36个小写字母和数字
	 */
	public static final char[] N36_CHARS = { '0', '1', '2', '3', '4', '5', '6',
			'7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
			'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w',
			'x', 'y', 'z' };
	
	 private static final int DEFAULT_ITERATIONS = 1;
	 private static final String DEFAULT_ALGORITHM ="md5";
	 private final static int hashIterations = 2;
	 
	 private static SecureRandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();
	
	 /**
	 * MD5简单加密
	 * 
	 * @param password
	 * @return
	 */
	public static String MD5(String password) {
		return DigestUtils.md5Hex(password).toString();
	}

	
	/**
	 * 指定长度的随机数
	 * @param limit
	 * @return
	 */
	public static String getRandom(int limit) {
		return  org.apache.commons.lang.RandomStringUtils.random(4,N36_CHARS);
	}
	
	
	/**
	 * SHA加密
	 * 
	 * @param password
	 * @return
	 */
	public static String SHA(String password) {
		return DigestUtils.shaHex(password);
	}
	
	/**
	 * 密码MD5散列2次
	 * @param salt
	 * @param password
	 * @return
	 */
	public static String encryptPassword(String salt,String password){
		String newPassword =toHex(hash(StringUtils.getBytesUtf8(password),StringUtils.getBytesUtf8(salt),hashIterations));
		return newPassword;
	}
	
	/**
	 * 随机生成加盐字符串
	 * @return
	 */
	public static String generateSalt(){
		return toHex(randomNumberGenerator.nextBytes());
	}
	
	
	/**
	 * 验证密码
	 *
	 * @param password  明文密码
	 * @param salt      加盐字符
	 * @param passwdDB  加密后的密码
	 * @return Boolean
	 */
	public static Boolean validPassword(String password, String salt, String passwdDB){
		if(org.apache.commons.lang3.StringUtils.isNotBlank(password)) {
			String encryPassword = PasswordHelper.encryptPassword(salt, password);
			return passwdDB.equals(encryPassword);
		}
		return false;
	}
	
	
	
	protected static MessageDigest getDigest(String algorithm) {
        try {
            return MessageDigest.getInstance(algorithm);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e.getMessage());
        }
     }
	
    protected static byte[] hash(byte[] bytes){
        return hash(bytes, null, DEFAULT_ITERATIONS,DEFAULT_ALGORITHM);
    }
    
    protected static byte[] hash(byte[] bytes, byte[] salt,int hashIterations){
        return hash(bytes, salt, hashIterations,DEFAULT_ALGORITHM);
    }
    protected static byte[] hash(byte[] bytes, byte[] salt,String algorithm){
        return hash(bytes, salt, DEFAULT_ITERATIONS,algorithm);
    }
    protected static byte[] hash(byte[] bytes, byte[] salt, int hashIterations,String algorithm){
        MessageDigest digest = getDigest(algorithm);
        if (isNotEmptyArray(salt)) {
            digest.reset();
            digest.update(salt);
        }
        byte[] hashed = digest.digest(bytes);
        int iterations = hashIterations - 1; //already hashed once above
        //iterate remaining number:
        for (int i = 0; i < iterations; i++) {
            digest.reset();
            hashed = digest.digest(hashed);
        }
        return hashed;
    }
    
    protected static String toHex(byte[] byteArr) {
         if(isNotEmptyArray(byteArr)) {
        	return Hex.encodeHexString(byteArr);
         }
         return null;
    }
	
	/**
	 * 判断任何对象的数组是否为空数组
	 * 
	 * @param arrayObj
	 *            数组对象
	 * @return boolean
	 */
	private static boolean isEmptyArray(Object arrayObj) {
		return null == arrayObj || Array.getLength(arrayObj) == 0;
	}

	/**
	 * 判断任何对象的数组是否不为空数组
	 * 
	 * @param arrayObj
	 *            数组对象
	 * @return boolean
	 */
	private static boolean isNotEmptyArray(Object arrayObj) {
		return !isEmptyArray(arrayObj);
	}
    
	
	
	
	public static class  SecureRandomNumberGenerator{
		
		protected static final int DEFAULT_NEXT_BYTES_SIZE = 16; //16 bytes == 128 bits (a common number in crypto)

	    private int defaultNextBytesSize;
	    private SecureRandom secureRandom;

	    /**
	     * Creates a new instance with a default backing {@link SecureRandom SecureRandom} and a
	     * {@link #getDefaultNextBytesSize() defaultNextBytesSize} of {@code 16}, which equals 128 bits, a size commonly
	     * used in cryptographic algorithms.
	     */
	    public SecureRandomNumberGenerator() {
	        this.defaultNextBytesSize = DEFAULT_NEXT_BYTES_SIZE;
	        this.secureRandom = new SecureRandom();
	    }

	    /**
	     * Seeds the backing {@link SecureRandom SecureRandom} instance with additional seed data.
	     *
	     * @param bytes the seed bytes
	     * @see SecureRandom#setSeed(byte[])
	     */
	    public void setSeed(byte[] bytes) {
	        this.secureRandom.setSeed(bytes);
	    }

	    /**
	     * Returns the {@link SecureRandom SecureRandom} backing this instance.
	     *
	     * @return the {@link SecureRandom SecureRandom} backing this instance.
	     */
	    public SecureRandom getSecureRandom() {
	        return secureRandom;
	    }

	    /**
	     * Sets the {@link SecureRandom SecureRandom} to back this instance.
	     *
	     * @param random the {@link SecureRandom SecureRandom} to back this instance.
	     * @throws NullPointerException if the method argument is null
	     */
	    public void setSecureRandom(SecureRandom random) throws NullPointerException {
	        if (random == null) {
	            throw new NullPointerException("SecureRandom argument cannot be null.");
	        }
	        this.secureRandom = random;
	    }

	    /**
	     * Returns the size of the generated byte array for calls to {@link #nextBytes() nextBytes()}.  Defaults to
	     * {@code 16}, which equals 128 bits, a size commonly used in cryptographic algorithms.
	     *
	     * @return the size of the generated byte array for calls to {@link #nextBytes() nextBytes()}.
	     */
	    public int getDefaultNextBytesSize() {
	        return defaultNextBytesSize;
	    }

	    /**
	     * Sets the size of the generated byte array for calls to {@link #nextBytes() nextBytes()}. Defaults to
	     * {@code 16}, which equals 128 bits, a size commonly used in cryptographic algorithms.
	     *
	     * @param defaultNextBytesSize the size of the generated byte array for calls to {@link #nextBytes() nextBytes()}.
	     * @throws IllegalArgumentException if the argument is 0 or negative
	     */
	    public void setDefaultNextBytesSize(int defaultNextBytesSize) throws IllegalArgumentException {
	        if ( defaultNextBytesSize <= 0) {
	            throw new IllegalArgumentException("size value must be a positive integer (1 or larger)");
	        }
	        this.defaultNextBytesSize = defaultNextBytesSize;
	    }

	    public byte[] nextBytes() {
	        return nextBytes(getDefaultNextBytesSize());
	    }

	    public byte[] nextBytes(int numBytes) {
	        if (numBytes <= 0) {
	            throw new IllegalArgumentException("numBytes argument must be a positive integer (1 or larger)");
	        }
	        byte[] bytes = new byte[numBytes];
	        this.secureRandom.nextBytes(bytes);
	        return bytes;
	    }
		
	}
	
	public static void main(String[] args) {
		String salt = generateSalt();
		String password = "12345";
		System.out.println(salt);
		System.out.println(encryptPassword("e44d0fbe4ccfa2f9ba8f09e7056e4d63",password));
	}
}
