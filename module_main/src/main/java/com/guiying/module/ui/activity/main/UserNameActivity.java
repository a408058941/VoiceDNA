package com.guiying.module.ui.activity.main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.common.voicedna.Common.IdVerifier;
import com.guiying.module.main.R;
import com.guiying.module.ui.activity.base.BaseActivity;
import com.guiying.module.ui.activity.base.MySPManager;

public class UserNameActivity extends BaseActivity {
    private int mRecordMaxNum;
    private TextView mTextTitle;
    private TextView mEdName;
    private TextView tv_doc;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_user_name;
    }

    @Override
    protected void initialize() {
        mRecordMaxNum = getIntent().getIntExtra("mRecordMaxNum", 3);
        findViewById(R.id.voice_toolbar).setOnClickListener(this);
        findViewById(R.id.tv_next).setOnClickListener(this);
        mTextTitle = findViewById(R.id.voice_title);
        tv_doc = findViewById(R.id.tv_doc);
        mEdName = findViewById(R.id.ed_name);
        if (mRecordMaxNum==1){
            tv_doc.setText("请先输入验证的用户名，再录入比对声纹");
            mTextTitle.setText("1:1验证");
        }else {
            tv_doc.setText("请先输入注册人员的用户名，再录入声纹");
            mEdName.setHint("请输入用户名");
            mTextTitle.setText("声纹注册");
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
            if (MySPManager.getAppType() == 1) {
                startActivity(new Intent(this, VoiceDetectionActivity.class).putExtra("mRecordMaxNum", mRecordMaxNum).putExtra("UserName",mEdName.getText().toString()));
            }else if (MySPManager.getAppType() == 2) {
                startActivity(new Intent(this, NumberVoiceDetectionActivity.class).putExtra("mRecordMaxNum", mRecordMaxNum).putExtra("UserName",mEdName.getText().toString()));
            }
            finish();
        }
    }
}