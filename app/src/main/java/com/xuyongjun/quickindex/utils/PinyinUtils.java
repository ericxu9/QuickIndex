package com.xuyongjun.quickindex.utils;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * ============================================================
 * 作 者 : XYJ
 * 版 本 ： 1.0
 * 创建日期 ： 2016/7/19 21:45
 * 描 述 ：获取汉字拼音核心类 ，使用第一方库完成
 * 修订历史 ：
 * ============================================================
 **/
public class PinyinUtils {
    /**
     * 根据传入的字符串(包含汉字),得到拼音
     * @return
     */
    public static String getPinyin(String str) {
        // 格式
        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        //大写
        format.setCaseType(HanyuPinyinCaseType.UPPERCASE);
        //没有音调
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);

        StringBuilder sb = new StringBuilder();

        char[] charArray = str.toCharArray();
        for (int i = 0; i < charArray.length; i++) {
            char c = charArray[i];
            // 如果是空格, 跳过
            if(Character.isWhitespace(c)){
                continue;
            }
            if(c >= -127 && c < 128){
                // 肯定不是汉字
                sb.append(c);
            }else {
                String s = "";
                try {
                    // 通过char得到拼音集合. 单 -> dan, shan
                    s = PinyinHelper.toHanyuPinyinStringArray(c, format)[0];
                    sb.append(s);
                } catch (BadHanyuPinyinOutputFormatCombination e) {
                    e.printStackTrace();
                    sb.append(s);
                }
            }
        }

        return sb.toString();
    }
}
