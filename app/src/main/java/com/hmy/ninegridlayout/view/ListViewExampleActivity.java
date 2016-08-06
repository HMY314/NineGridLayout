package com.hmy.ninegridlayout.view;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.hmy.ninegridlayout.R;
import com.hmy.ninegridlayout.adapter.NineGridTestAdapter;
import com.hmy.ninegridlayout.model.NineGridTestModel;

import java.io.Serializable;
import java.util.List;

public class ListViewExampleActivity extends AppCompatActivity {

    private static final String ARG_LIST = "list";

    private ListView mListView;
    private NineGridTestAdapter mAdapter;
    private List<NineGridTestModel> mList;

    public static void startActivity(Context context, List<NineGridTestModel> list) {
        Intent intent = new Intent(context, ListViewExampleActivity.class);
        intent.putExtra(ARG_LIST, (Serializable) list);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view_example);
        getIntentData();
        initView();
    }

    private void getIntentData() {
        mList = (List<NineGridTestModel>) getIntent().getSerializableExtra(ARG_LIST);
    }

    private void initView() {
        mListView = (ListView) findViewById(R.id.lv_bbs);

        mAdapter = new NineGridTestAdapter(this);
        mAdapter.setList(mList);
        mListView.setAdapter(mAdapter);
    }
}
