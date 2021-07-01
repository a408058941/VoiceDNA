package com.guiying.module.ui.activity.main;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.common.voicedna.Common.IdVerifier;
import com.common.voicedna.Record.AudioRecorder;
import com.common.voicedna.VoiceRecognize.VoiceRegisterConfig;
import com.common.voicedna.api.DnaPresenter;
import com.common.voicedna.bean.GroupBean;
import com.common.voicedna.bean.TokenBean;
import com.common.voicedna.network.RxCallback;
import com.common.voicedna.utils.CallBack;
import com.common.voicedna.utils.DnaCallback;
import com.common.voicedna.utils.EmptyUtil;
import com.common.voicedna.utils.PermissionUtil;
import com.common.voicedna.utils.SPManager;
import com.common.voicedna.utils.SPUtil;
import com.guiying.module.main.R;
import com.guiying.module.ui.activity.base.BaseActivity;
import com.guiying.module.ui.activity.base.MySPManager;
import com.voiceai.voicedna.bean.dto.VoicePrintGroup;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static com.guiying.module.ui.activity.base.Constants.DNA_DIGIT_ANDROID;

public class SplashActivity extends BaseActivity {
    private EditText MEdAppid;
    private EditText MEdAppsecret;
    private RadioGroup radio,radio1,radio2;
    private RadioButton radioButton1;
    private RadioButton radioButton2;
    private ImageView voice_toolbar;
    private String appid;
    private String appsecret;
    private int type = 1;
    private int Jump;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initialize() {
        MEdAppid = findViewById(R.id.ed_appid);
        MEdAppsecret = findViewById(R.id.ed_appsecret);

        findViewById(R.id.test_1).setOnClickListener(this);
        radio = findViewById(R.id.radio);
        radioButton1 = findViewById(R.id.radioButton);
        radioButton2 = findViewById(R.id.radioButton1);
        voice_toolbar = findViewById(R.id.voice_toolbar);
        radio1= findViewById(R.id.radio1);
        radio2= findViewById(R.id.radio2);
        voice_toolbar.setOnClickListener(this);
        Jump = getIntent().getIntExtra("Jump", 0);
        if (EmptyUtil.isEmpty(MySPManager.getAppId())) {
            voice_toolbar.setVisibility(View.GONE);
        }

        if (!EmptyUtil.isEmpty(MySPManager.getAppId())) {
            appid = MySPManager.getAppId();
            appsecret = MySPManager.getAppSecret();
        } else {
            if (isDebugVersion(this)){
                MEdAppid.setText("qcJBCiBVKNPCK5zZ9speshM3FnjFFrtQ");
                MEdAppsecret.setText("dRuvIImmvQnvu5EvRlTNcsMqBJKfgnsI");
            }
        }

        radio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkId) {
                if (checkId == R.id.radioButton) {
                    //自由文本
                    type = 1;
                    if (isDebugVersion(SplashActivity.this)){
                        if (MySPManager.getType() == 1) {
                            MEdAppid.setText("aKis0rt0mZFeJzTXguEaivrY5eY2umAD");
                            MEdAppsecret.setText("KBCqHrI1bltUj50hwhFyCmCwwhRRrwUS");
                        } else if (MySPManager.getType() == 2) {
                            MEdAppid.setText("qcJBCiBVKNPCK5zZ9speshM3FnjFFrtQ");
                            MEdAppsecret.setText("dRuvIImmvQnvu5EvRlTNcsMqBJKfgnsI");
                        }
                    }
                } else if (checkId == R.id.radioButton1) {
                    //随机数字
                    type = 2;
                    if (isDebugVersion(SplashActivity.this)){
                        if (MySPManager.getType() == 1) {
                            MEdAppid.setText("Oxh7jVdYNA4k6BXdS33bzTOvdyM59A7i");
                            MEdAppsecret.setText("gdNAGoHZZVp4dhIHKgOrpnGF64fwQqNw");

                        } else if (MySPManager.getType() == 2) {

                            MEdAppid.setText("oXypEIjRbQoecpWS3h8jJS6JMz4sJDKN");
                            MEdAppsecret.setText("ry1caLXynY2NaGDHfevA2OnnkEzBQxD9");
                        }
                    }
                }
            }
        });
        if (EmptyUtil.isNotEmpty(MySPManager.getAppId())) {
            MEdAppid.setText(MySPManager.getAppId());
            MEdAppsecret.setText(MySPManager.getAppSecret());
            type = MySPManager.getAppType();
            if (type == 1) {
                radioButton1.setChecked(true);
            } else if (type == 2) {
                radioButton2.setChecked(true);
            }
        }
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        if (view.getId() == R.id.test_1) {
            if (TextUtils.isEmpty(MEdAppid.getText().toString())) {
                Toast.makeText(SplashActivity.this, "请输入appid", Toast.LENGTH_SHORT).show();
                return;
            } else if (TextUtils.isEmpty(MEdAppsecret.getText().toString())) {
                Toast.makeText(SplashActivity.this, "请输入Appsecret", Toast.LENGTH_SHORT).show();
                return;
            }
            Intent intent = new Intent();
            intent.putExtra("appid", MEdAppid.getText().toString());
            intent.putExtra("appsecret", MEdAppsecret.getText().toString());
            intent.putExtra("type", type);
            setResult(RESULT_OK, intent);
            finish();
        }else
        if (view.getId() == R.id.voice_toolbar) {
            finish();
        }
    }
    public static boolean isDebugVersion(Context context) {
        try {
            ApplicationInfo info = context.getApplicationInfo();
            return (info.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}