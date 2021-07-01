package com.guiying.module.ui.activity.main;

import android.Manifest;
import android.content.Intent;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.common.voicedna.Common.IdVerifier;
import com.common.voicedna.VoiceRecognize.VoiceRegisterConfig;
import com.common.voicedna.bean.ServiceType;
import com.common.voicedna.utils.CallBack;
import com.common.voicedna.utils.DnaCallback;
import com.common.voicedna.utils.EmptyUtil;
import com.common.voicedna.utils.PermissionUtil;
import com.common.voicedna.utils.SPUtil;
import com.guiying.module.main.R;
import com.guiying.module.ui.activity.base.BaseActivity;
import com.guiying.module.ui.activity.base.MySPManager;
import com.voiceai.voicedna.bean.dto.VoicePrintGroup;

import java.util.List;

/**
 * <p>类说明</p>
 *
 * @version V1.2.0
 * @name MainActivity
 */
public class MainActivity extends BaseActivity {
    private String appid;
    private String appsecret;
    private TextView tv_type;
    private int type;
    private long lastClickTime = 0L;
    private static final int FAST_CLICK_DELAY_TIME = 500; // 快速点击间隔
    private int count = 0;
    public String HOSTTextURL = "https://test.cloudv3.voiceaitech.com";
    public String fileTextServer = "https://test.dna.upload.voiceaitech.com";

    public String HOSTFormalURL = "https://console.voiceaitech.com";
    public String fileFormaServer = "https://dna.upload.voiceaitech.com";
    String dnaServer;
    String fileServer;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initialize() {
        SPUtil.getSP(this);
        VoiceRegisterConfig.setVerifySpeechDurationStandard(3);
        VoiceRegisterConfig.setRegisterSpeechDurationStandard(8);
        tv_type = findViewById(R.id.tv_type);
        findViewById(R.id.test_1).setOnClickListener(this);
        findViewById(R.id.test_2).setOnClickListener(this);
        findViewById(R.id.test_3).setOnClickListener(this);
        findViewById(R.id.tv_set).setOnClickListener(this);
        findViewById(R.id.image_log).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (System.currentTimeMillis() - lastClickTime < FAST_CLICK_DELAY_TIME) {
                    if (count >= 4) {
                        if (MySPManager.getType() == 1) {
                            tv_type.setText("VoiceDNA-test");
                            MySPManager.setType(2);
                            dnaServer = HOSTTextURL;
                            fileServer = fileTextServer;
                        } else if (MySPManager.getType() == 2) {
                            tv_type.setText("VoiceDNA");
                            MySPManager.setType(1);
                            dnaServer = HOSTFormalURL;
                            fileServer = fileFormaServer;
                        }
                        startActivityForResult(new Intent(MainActivity.this, SplashActivity.class), 101);
                        count = 0;
                    } else {
                        count++;
                    }
                } else {
                    count = 1;
                }
                lastClickTime = System.currentTimeMillis();
            }
        });
        if (MySPManager.getType() == 2) {
            tv_type.setText("VoiceDNA-test");
            dnaServer = HOSTTextURL;
            fileServer = fileTextServer;
        } else if (MySPManager.getType() == 1) {
            tv_type.setText("VoiceDNA");
            dnaServer = HOSTFormalURL;
            fileServer = fileFormaServer;
        }

        if (EmptyUtil.isEmpty(MySPManager.getAppId())) {
            startActivityForResult(new Intent(this, SplashActivity.class), 101);
        } else {
            appid = MySPManager.getAppId();
            appsecret = MySPManager.getAppSecret();
            type = MySPManager.getAppType();
            initPermission();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 101) {
//            if (EmptyUtil.isNotEmpty(MySPManager.getAppId())) {
            if (data == null)
                return;
            appid = data.getStringExtra("appid");
            appsecret = data.getStringExtra("appsecret");
            type = data.getIntExtra("type", 0);
            initPermission();
//            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        IdVerifier.getInstance().release();
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        if (isFastClick()) {
            return;
        }
        if (EmptyUtil.isEmpty(appid)) {
            startActivityForResult(new Intent(this, SplashActivity.class), 101);
            return;
        } else if (EmptyUtil.isEmpty(MySPManager.getUserGroupid())) {
            startActivityForResult(new Intent(this, SplashActivity.class), 101);
            return;
        } else if (view.getId() == R.id.tv_set) {
            startActivityForResult(new Intent(this, SplashActivity.class), 101);
            return;
        } else if (view.getId() == R.id.test_1) {
            //注册
            startActivity(new Intent(MainActivity.this, UserNameActivity.class).putExtra("mRecordMaxNum", 0));
        } else if (view.getId() == R.id.test_2) {
            //1:1
            startActivity(new Intent(MainActivity.this, UserNameActivity.class).putExtra("mRecordMaxNum", 1));
        } else if (view.getId() == R.id.test_3) {
            if (MySPManager.getAppType() == 1) {
                startActivity(new Intent(MainActivity.this, VoiceDetectionActivity.class).putExtra("mRecordMaxNum", 2));
            } else if (MySPManager.getAppType() == 2) {
                //1:N
                startActivity(new Intent(MainActivity.this, NumberVoiceDetectionActivity.class).putExtra("mRecordMaxNum", 2));
            }
        } else if (view.getId() == R.id.image_log) {

        }
    }


    /**
     * 初始化权限
     */
    public void initPermission() {
        new PermissionUtil(this).permission(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO, Manifest.permission.READ_PHONE_STATE).request(new CallBack() {
            @Override
            public void onSuccess() {
                init();
            }

            @Override
            public void onFail() {
                Toast.makeText(MainActivity.this, "权限获取失败", Toast.LENGTH_SHORT).show();
            }
        });
    }


    /**
     * 初始化SDK
     */
    public void init() {
        showProgressDialog("正在初始化");
        ServiceType serviceId = type == 1 ? ServiceType.TEXT : ServiceType.NUMBER;
        IdVerifier.getInstance().init(this, appid, appsecret, serviceId, dnaServer, fileServer, new DnaCallback<Integer>() {
            @Override
            public void onSuccess(@Nullable Integer data) {
                //初始化成功
                if (data == 0) {
                    getGroup();
                }
            }

            @Override
            public void onError(int code, String msg) {
                Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                appid = "";
                hideProgressDialog();
            }
        });
    }

    /**
     * 获取分组
     */
    public void getGroup() {
        IdVerifier.getInstance().getGroup(new DnaCallback<List<VoicePrintGroup>>() {
            @Override
            public void onSuccess(@Nullable List<VoicePrintGroup> data) {
                if (data != null && data.size() > 0) {
                    hideProgressDialog();
                    MySPManager.setUserGroupid(data.get(0).getId());
                    MySPManager.setUserGroupName(data.get(0).getGroupName());
                    MySPManager.setAppId(appid);
                    MySPManager.setAppSecret(appsecret);
                    MySPManager.setAppType(type);
                    Toast.makeText(MainActivity.this, "初始化成功", Toast.LENGTH_SHORT).show();
                } else {
                    GreateGroup();
                }
            }

            @Override
            public void onError(int code, String msg) {
                Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                hideProgressDialog();
            }
        });
    }

    public void GreateGroup() {
        IdVerifier.getInstance().GreateGroup("android_group", new DnaCallback<String>() {
            @Override
            public void onSuccess(@Nullable String data) {
                getGroup();
            }

            @Override
            public void onError(int code, String msg) {
                Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                hideProgressDialog();
            }
        });

    }


    /**
     * 再按一次退出程序
     */
    private long currentBackPressedTime = 0;
    private static int BACK_PRESSED_INTERVAL = 5000;

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN
                && event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            if (System.currentTimeMillis() - currentBackPressedTime > BACK_PRESSED_INTERVAL) {
                currentBackPressedTime = System.currentTimeMillis();
                return true;
            } else {
                finish();

            }
            return false;

        } else if (event.getKeyCode() == KeyEvent.KEYCODE_MENU) {
            return true;
        }
        return super.dispatchKeyEvent(event);
    }
}
