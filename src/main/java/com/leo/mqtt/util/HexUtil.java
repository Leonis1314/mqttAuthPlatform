package com.leo.mqtt.util;

import org.apache.commons.codec.binary.Hex;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

/**
 * reference apache commons <a 
 * href="http://commons.apache.org/codec/">http://commons.apache.org/codec/</a> 
 * Java中byte用二进制表示占用8位，而我们知道16进制的每个字符需要用4位二进制位来表示。
 所以我们就可以把每个byte转换成两个相应的16进制字符，即把byte的高4位和低4位分别转换成相应的16进制字符H和L，并组合起来得到byte转换到16进制字符串的结果new String(H) + new String(L)。

 同理，相反的转换也是将两个16进制字符转换成一个byte，原理同上。

 根据以上原理，我们就可以将byte[] 数组转换为16进制字符串了，当然也可以将16进制字符串转换为byte[]数组了。
 * @author zhanghp
 *  
 */  
public class HexUtil {
  
    /** 
     * 用于建立十六进制字符的输出的小写字符数组 
     */  
    private static final char[] DIGITS_LOWER = { '0', '1', '2', '3', '4', '5',  
            '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };  
  
    /** 
     * 用于建立十六进制字符的输出的大写字符数组 
     */  
    private static final char[] DIGITS_UPPER = { '0', '1', '2', '3', '4', '5',  
            '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };  
  
    /** 
     * 将字节数组转换为十六进制字符数组 
     *  
     * @param data 
     *            byte[] 
     * @return 十六进制char[] 
     */  
    public static char[] encodeHex(byte[] data) {  
        return encodeHex(data, true);  
    }  
  
    /** 
     * 将字节数组转换为十六进制字符数组 
     *  
     * @param data 
     *            byte[] 
     * @param toLowerCase 
     *            <code>true</code> 传换成小写格式 ， <code>false</code> 传换成大写格式 
     * @return 十六进制char[] 
     */  
    public static char[] encodeHex(byte[] data, boolean toLowerCase) {  
        return encodeHex(data, toLowerCase ? DIGITS_LOWER : DIGITS_UPPER);  
    }  
  
    /** 
     * 将字节数组转换为十六进制字符数组 
     *  
     * @param data 
     *            byte[] 
     * @param toDigits 
     *            用于控制输出的char[] 
     * @return 十六进制char[] 
     */  
    protected static char[] encodeHex(byte[] data, char[] toDigits) {  
        int l = data.length;  
        char[] out = new char[l << 1];
        // two characters form the hex value.  
        for (int i = 0, j = 0; i < l; i++) {  
            out[j++] = toDigits[(0xF0 & data[i]) >>> 4];  
            out[j++] = toDigits[0x0F & data[i]];  
        }  
        return out;  
    }  
  
    /** 
     * 将字节数组转换为十六进制字符串 
     *  
     * @param data 
     *            byte[] 
     * @return 十六进制String 
     */  
    public static String encodeHexStr(byte[] data) {  
        return encodeHexStr(data, true);  
    }  
  
    /** 
     * 将字节数组转换为十六进制字符串 
     *  
     * @param data 
     *            byte[] 
     * @param toLowerCase 
     *            <code>true</code> 传换成小写格式 ， <code>false</code> 传换成大写格式 
     * @return 十六进制String 
     */  
    public static String encodeHexStr(byte[] data, boolean toLowerCase) {  
        return encodeHexStr(data, toLowerCase ? DIGITS_LOWER : DIGITS_UPPER);  
    }  
  
    /** 
     * 将字节数组转换为十六进制字符串 
     *  
     * @param data 
     *            byte[] 
     * @param toDigits 
     *            用于控制输出的char[] 
     * @return 十六进制String 
     */  
    protected static String encodeHexStr(byte[] data, char[] toDigits) {  
        return new String(encodeHex(data, toDigits));  
    }  
  
    /** 
     * 将十六进制字符数组转换为字节数组 
     *  
     * @param data 
     *            十六进制char[] 
     * @return byte[] 
     * @throws RuntimeException 
     *             如果源十六进制字符数组是一个奇怪的长度，将抛出运行时异常 
     */  
    public static byte[] decodeHex(char[] data) {  
  
        int len = data.length;  
  
        if ((len & 0x01) != 0) {  
            throw new RuntimeException("Odd number of characters.");  
        }  
  
        byte[] out = new byte[len >> 1];  
  
        // two characters form the hex value.  
        for (int i = 0, j = 0; j < len; i++) {  
            int f = toDigit(data[j], j) << 4;  
            j++;  
            f = f | toDigit(data[j], j);  
            j++;  
            out[i] = (byte) (f & 0xFF);  
        }  
  
        return out;  
    }  
  
    /** 
     * 将十六进制字符转换成一个整数 
     *  
     * @param ch 
     *            十六进制char 
     * @param index 
     *            十六进制字符在字符数组中的位置 
     * @return 一个整数 
     * @throws RuntimeException 
     *             当ch不是一个合法的十六进制字符时，抛出运行时异常 
     */  
    protected static int toDigit(char ch, int index) {  
        int digit = Character.digit(ch, 16);  
        if (digit == -1) {  
            throw new RuntimeException("Illegal hexadecimal character " + ch  
                    + " at index " + index);  
        }  
        return digit;  
    }

    /***
     *  利用Apache的工具类实现SHA-256加密
     * @param str 加密后的报文
     * @return
     */
    public static String getSHA256Str(String str){
        MessageDigest messageDigest;
        String encdeStr = "";
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
            byte[] hash = messageDigest.digest(str.getBytes("UTF-8"));
            encdeStr = Hex.encodeHexString(hash);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return encdeStr;
    }

    /**
     * 加盐方法
     * @time 2017-11-11 下午8:45:04
     * @return
     */
    public static String generate() {
        Random r = new Random();
        StringBuilder sb = new StringBuilder(16);
        sb.append(r.nextInt(99999999)).append(r.nextInt(99999999));
        int len = sb.length();
        if (len < 16) {
            for (int i = 0; i < 16 - len; i++) {
                sb.append("0");
            }
        }
        return sb.toString();
    }

    public static void main(String[] args) {  
        String srcStr = "待转换字符串";  
        String encodeStr = encodeHexStr(srcStr.getBytes());  
        String decodeStr = new String(decodeHex(encodeStr.toCharArray()));  
        System.out.println("转换前：" + srcStr);  
        System.out.println("转换后：" + encodeStr);  
        System.out.println("还原后：" + decodeStr);  
    }  
  
}  