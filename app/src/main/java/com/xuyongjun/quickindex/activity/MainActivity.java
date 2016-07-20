package com.xuyongjun.quickindex.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.xuyongjun.quickindex.R;
import com.xuyongjun.quickindex.adapter.ContactAdapter;
import com.xuyongjun.quickindex.domain.Contact;
import com.xuyongjun.quickindex.utils.Cheeses;
import com.xuyongjun.quickindex.view.QuickIndexBar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private QuickIndexBar mQuickIndexBar;
    private ListView mListView;
    private TextView mTvLetter;
    private EditText mEdSearch;
    private Button mBtnClear;
    private TextView mTvHint;

    private List<Contact> mListData;
    private ContactAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initListView();
        initViewListener();
    }


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg)
        {
            mTvLetter.setVisibility(View.GONE);
        }
    };

    private void initView()
    {
        mQuickIndexBar = (QuickIndexBar) findViewById(R.id.bar);
        mListView = (ListView) findViewById(R.id.list_view);
        mTvLetter = (TextView) findViewById(R.id.tv_letter);
        mEdSearch = (EditText) findViewById(R.id.et_search);
        mBtnClear = (Button) findViewById(R.id.btn_clear);
        mTvHint = (TextView) findViewById(R.id.tv_hint);
    }

    /**
     * 初始化ListView
     */
    private void initListView()
    {
        fillDataAndSort();

        mAdapter = new ContactAdapter(this, mListData);
        mListView.setAdapter(mAdapter);
    }

    /**
     * 初始化View的监听事件
     */
    private void initViewListener()
    {
        //设置字母索引触摸监听
        mQuickIndexBar.setOnLetterUpdateListener(new QuickIndexBar.OnLetterUpdateListener() {
            @Override
            public void onLetterUpdate(String letter)
            {
                showCenterLetter(letter);
                /*
                通过滑动的字母索引跳转到指定的位置
                 */
                for (int i = 0; i < mListData.size(); i++) {
                    Contact contact = mListData.get(i);
                    String dataLetter = contact.getPinyin().charAt(0) + "";
                    if (dataLetter.equals(letter)) {
                        mListView.setSelection(i);
                        break;//只要一有相同的就结束循环
                    }
                }
            }
        });


        /*
        添加ListView点击事件
         */
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Toast.makeText(MainActivity.this, mListData.get(position).getName(), Toast.LENGTH_SHORT).show();
            }
        });


        /*
        添加EditText输入监听，实现搜索
         */
        mEdSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                mListData.clear();
                String text = mEdSearch.getText().toString();//转为大写

                for (int i = 0; i < Cheeses.sCheeseStrings.length; i++) {
                    String name = Cheeses.sCheeseStrings[i];
                    if (name.startsWith(text)) {
                        mListData.add(new Contact(name));
                    }
                }
                // 未搜索到数据是显示提示信息；可以根据集合长度来判断
                mTvHint.setVisibility(mListData.size() > 0 ? View.GONE : View.VISIBLE);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        /*
        添加清除点击事件
         */
        mBtnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                mEdSearch.setText("");
            }
        });
    }


    /**
     * 当滑动索引条的时候 显示屏幕中间滑动的字母提示
     *
     * @param letter
     */
    private void showCenterLetter(String letter)
    {
        //清空消息
        handler.removeCallbacksAndMessages(null);

        mTvLetter.setText(letter);
        mTvLetter.setVisibility(View.VISIBLE);
        //两秒后隐藏
        handler.sendEmptyMessageDelayed(0, 500);
    }

    /**
     * 填充数据并且进行排序
     */
    private void fillDataAndSort()
    {
        mListData = new ArrayList<>();
        for (int i = 0; i < Cheeses.sCheeseStrings.length; i++) {
            Contact contact = new Contact(Cheeses.sCheeseStrings[i]);
            mListData.add(contact);
        }
        // 对集合进行排序
        Collections.sort(mListData);
    }


}
