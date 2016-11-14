package com.jkb.mrcampus.utils;

/**
 * 数字转换的工具类
 * Created by JustKiddingBaby on 2016/11/14.
 */

public class NumberSwitchUtils {
    static String[] units = {"", "十", "百", "千", "万", "十万", "百万", "千万", "亿",
            "十亿", "百亿", "千亿", "万亿"};
    static char[] numArray = {'零', '一', '二', '三', '四', '五', '六', '七', '八', '九'};

    public static String foematInteger(int num) {
        char[] val = String.valueOf(num).toCharArray();
        int len = val.length;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < len; i++) {
            String m = val[i] + "";
            int n = Integer.valueOf(m);
            boolean isZero = n == 0;
            String unit = units[(len - 1) - i];
            if (isZero) {
                if ('0' == val[i - 1]) {
                    // not need process if the last digital bits is 0
                    continue;
                } else {
                    // no unit for 0
                    sb.append(numArray[n]);
                }
            } else {
                sb.append(numArray[n]);
                sb.append(unit);
            }
        }
        return sb.toString();
    }

    public static String formatDecimal(double decimal) {
        String decimals = String.valueOf(decimal);
        int decIndex = decimals.indexOf(".");
        int integ = Integer.valueOf(decimals.substring(0, decIndex));
        int dec = Integer.valueOf(decimals.substring(decIndex + 1));
        String result = foematInteger(integ) + "." + formatFractionalPart(dec);
        return result;
    }

    public static String formatFractionalPart(int decimal) {
        char[] val = String.valueOf(decimal).toCharArray();
        int len = val.length;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < len; i++) {
            int n = Integer.valueOf(val[i] + "");
            sb.append(numArray[n]);
        }
        return sb.toString();
    }

    /**
     * 求double的整数部分
     */
    public static String doubleInteger(double num) {
        int integer = (int) (num / 1);
        return integer + "";
    }

    /**
     * 求后两位余数
     */
    public static String doubleRemainder(double num) {
        double num2 = num * 100;
        String result = "";
        for (int i = 0; i < 2; i++) {
            result += (int) (num2 % 10) + "";
            num2 /= 10;
        }
        return result;
    }
}
