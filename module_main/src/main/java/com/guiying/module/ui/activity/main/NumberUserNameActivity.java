package com.guiying.module.ui.activity.main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.guiying.module.main.R;
import com.guiying.module.ui.activity.base.BaseActivity;

public class NumberUserNameActivity extends BaseActivity {

    private int mRecordMaxNum;
    private TextView mTextTitle;
    private TextView mEdName;
    String groupid;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_number_user_name;
    }

    @Override
    protected void initialize() {
        mRecordMaxNum = getIntent().getIntExtra("mRecordMaxNum", 3);
        findViewById(R.id.voice_toolbar).setOnClickListener(this);
        findViewById(R.id.tv_next).setOnClickListener(this);
        mTextTitle = findViewById(R.id.voice_title);
        groupid=getIntent().getStringExtra("groupid");
        mEdName = findViewById(R.id.ed_name);
        if (mRecordMaxNum==1){
            mEdName.setHint("请输入用户名(10个字符以内)");
            mTextTitle.setText("请输入用户名");
        }else {
            mTextTitle.setText("设置用户名");
        }
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        if (view.getId() == R.id.voice_toolbar) {
            finish();
        } else if (view.getId() == R.id.tv_next) {
            if (TextUtils.isEmpty(mEdName.getText().toString())){
                Toast.makeText(this, "请输入用户名", Toast.LENGTH_SHORT).show();
                return;
            }
            startActivity(new Intent(this, NumberVoiceDetectionActivity.class).putExtra("mRecordMaxNum", mRecordMaxNum).putExtra("UserName",mEdName.getText().toString()).putExtra("groupid",groupid));
            finish();
        }
    }
}