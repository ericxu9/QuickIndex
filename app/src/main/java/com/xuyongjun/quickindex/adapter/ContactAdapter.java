package com.xuyongjun.quickindex.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xuyongjun.quickindex.R;
import com.xuyongjun.quickindex.domain.Contact;

import java.util.List;

/**
 * ============================================================
 * 作 者 : XYJ
 * 版 本 ： 1.0
 * 创建日期 ： 2016/7/19 22:01
 * 描 述 ：
 * 修订历史 ：
 * ============================================================
 **/
public class ContactAdapter extends BaseAdapter {

    private List<Contact> mListData;
    private LayoutInflater mInflater;

    public ContactAdapter(Context context, List<Contact> list)
    {
        this.mListData = list;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount()
    {
        return mListData.size();
    }

    @Override
    public Object getItem(int position)
    {
        return mListData.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_item, null);
        }
        ViewHolder holder = ViewHolder.getHolder(convertView);
        Contact contact = mListData.get(position);

        String currentLetter = contact.getPinyin().charAt(0)+"";
        /*
        判断前后两个字母是否相同，相同就不显示，不相同的显示
         */
        String temp = null;
        if (position == 0 ) {
            temp = currentLetter;
        } else {
            // 获取之前的一个字母
            String preLetter = mListData.get(position - 1).getPinyin().charAt(0) + "";
            if (!TextUtils.equals(preLetter,currentLetter)) {
                temp = currentLetter;
            }
        }
        holder.mTvIndex.setVisibility(temp == null?View.GONE:View.VISIBLE);
        holder.mTvName.setText(contact.getName());
        holder.mTvIndex.setText(currentLetter);
        return convertView;
    }

    private static class ViewHolder {

        private TextView mTvIndex;
        private TextView mTvName;

        public static ViewHolder getHolder(View view)
        {
            Object tag = view.getTag();
            if (tag == null) {
                ViewHolder holder = new ViewHolder();
                holder.mTvName = (TextView) view.findViewById(R.id.tv_name);
                holder.mTvIndex = (TextView) view.findViewById(R.id.tv_index);
                view.setTag(holder);
                return holder;
            } else {
                return (ViewHolder) tag;
            }
        }
    }
}
