package com.xuyongjun.quickindex.domain;

import com.xuyongjun.quickindex.utils.PinyinUtils;

/**
 * ============================================================
 * 作 者 : XYJ
 * 版 本 ： 1.0
 * 创建日期 ： 2016/7/19 21:57
 * 描 述 ：
 * 修订历史 ：
 * ============================================================
 **/
public class Contact implements Comparable<Contact>{
    private String name;
    private String pinyin;

    public Contact(String name)
    {
        this.name = name;
        this.pinyin = PinyinUtils.getPinyin(name);
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getPinyin()
    {
        return pinyin;
    }

    public void setPinyin(String pinyin)
    {
        this.pinyin = pinyin;
    }

    @Override
    public int compareTo(Contact another)
    {

        return this.pinyin.compareTo(another.pinyin);
    }
}
