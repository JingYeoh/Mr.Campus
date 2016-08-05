package com.jkb.model.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.util.Log;

/**
 * 判断格式的工具类
 *
 * @author Administrator
 */
public class FormatUtils {

    private static final String TAG = "FormatUtils";

    /**
     * 判断手机号码是否邮箱
     */
    public static boolean judgePhoneFormat(String str) {
        if (StringUtils.isEmpty(str)) {
            return false;
        }
        String patternMobile = "^(13[0-9]|15[01]|153|15[6-9]|180|18[23]|18[5-9])\\d{8}$";
        Pattern pMobile = Pattern.compile(patternMobile);
        Matcher matcherMobile = pMobile.matcher(str);
        if (matcherMobile.matches()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断邮箱是否邮箱
     */
    public static boolean judgeEmailFormat(String str) {
        if (StringUtils.isEmpty(str)) {
            return false;
        }
        String patternEmail = "^\\s*\\w+(?:\\.{0,1}[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$";
        Pattern pEmail = Pattern.compile(patternEmail);
        Matcher matcherEmail = pEmail.matcher(str);
        if (matcherEmail.matches()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断网址是否邮箱
     */
    public static boolean judgeUrlFormat(String str) {
        if (StringUtils.isEmpty(str)) {
            return false;
        }
        Pattern patternUrl = Pattern
                .compile(
                        "^http://[\\w-\\.]+(?:/|(?:/[\\w\\.\\-]+)*(?:/[\\w\\.\\-]+\\.do))?$",
                        Pattern.CASE_INSENSITIVE);
        Matcher matcherUrl = patternUrl.matcher(str);
        Log.d(TAG,
                "---judgeuRLFormat...." + str + "\n的结果是" + matcherUrl.matches());
        if (matcherUrl.matches()) {
            return true;
        } else {
            return false;
        }
    }
}
