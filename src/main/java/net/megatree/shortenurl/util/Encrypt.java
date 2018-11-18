package net.megatree.shortenurl.util;

import org.junit.Test;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * Created by mythss on 2018-11-18.
 */
public class Encrypt {

    private static final char[]                  CHARS_TABLE    = {'J', 'h', 'R', 's', '7', 'u', '9', 'p', 'b', '1', 'g', 'D', 'j', 'U', 'V', 'l', 'y', 'i', 'T', 'c', 'P', 'd', 'a', 'H', 'n', 'F', 'x', '2', 'X', 'A', 'Y', 'W', '5', 'm', 'C', 'z', '0', 'G', 't', 'v', '4', 'o', 'r', 'Z', '3', 'e', 'q', 'S', 'N', 'L', 'O', 'K', 'B', '8', 'k', 'I', 'f', 'E', 'Q', '6', 'w', 'M'};
    private static final Map<Character, Integer> MAP_CHAR_INDEX = new HashMap<>(128);

    static {
        for (int i = 0; i < CHARS_TABLE.length; i++) {
            MAP_CHAR_INDEX.put(CHARS_TABLE[i], i);
        }
    }

    /**
     * 62进制转10进制
     *
     * @param str
     * @return
     */
    public static long _62_to_10(String str) {
        if (str == null) {
            return -1;
        }

        long multiple = 1;
        long result = 0;
        for (int i = str.length() - 1; i >= 0; i--) {
            char c = str.charAt(i);
            int index = MAP_CHAR_INDEX.get(c);

            result += index * multiple;
            multiple *= 62;
        }
        return result;
    }

    /**
     * 10进制转62进制
     *
     * @param num
     * @return
     */
    public static String _10_to_62(long num) {
        StringBuilder result = new StringBuilder();

        long divisor = 1;
        while (divisor * 62 <= num) {
            divisor *= 62;
        }
        //得到了小于num的62的最高次幂
        //num/divisor 商作为下标去进制表找对应字符，
        //同时余数作为下次被除数
        for (; divisor >= 1; divisor /= 62) {
            result.append(CHARS_TABLE[(int) (num / divisor)]);
            num %= divisor;
        }

        return result.toString();
    }

    /**
     * 判断短链接是否合法
     *
     * @param str
     * @return
     */
    public static boolean isValid(String str) {
        if (str == null || str.length() == 0) {
            return false;
        }

        if (str.length() > 6) {
            return false;
        }

        for (int i = 0; i < str.length(); i++) {
            if (!MAP_CHAR_INDEX.containsKey(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static String md5(String str) {
        if (str == null || str.length() == 0) {
            throw new IllegalArgumentException("String to encript cannot be null or zero length");
        }
        StringBuffer hexString = new StringBuffer();
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes());
            byte[] hash = md.digest();
            for (int i = 0; i < hash.length; i++) {
                if ((0xff & hash[i]) < 0x10) {
                    hexString.append("0").append(Integer.toHexString((0xFF & hash[i])));
                } else {
                    hexString.append(Integer.toHexString(0xFF & hash[i]));
                }
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return hexString.toString();
    }


    @Test
    public void test() {
        shuffle();
    }

    @Test
    public void test2() {
//        System.out.println(_10_to_62(999999999000000L));
        System.out.println(_62_to_10("h"));
    }

    private void shuffle() {
        List<Character> list = new ArrayList(100);
        for (int i = 0; i < CHARS_TABLE.length; i++) {
            list.add(CHARS_TABLE[i]);
        }
        Collections.shuffle(list);
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        Iterator ite = list.iterator();
        while (ite.hasNext()) {
            String s = ite.next().toString();
            sb.append("'").append(s).append("',");
        }
        System.out.println(sb.toString());
    }

}
