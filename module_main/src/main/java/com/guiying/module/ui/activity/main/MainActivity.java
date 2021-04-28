package com.guiying.module.ui.activity.main;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.KeyEvent;

import androidx.annotation.Nullable;

import com.common.voicedna.api.DnaPresenter;
import com.common.voicedna.bean.FileBean;
import com.common.voicedna.bean.TokenBean;
import com.common.voicedna.bean.VoiceoperateDetailBean;
import com.common.voicedna.bean.VoiceprintTaskDetailBean;
import com.common.voicedna.data.AutoRegisData;
import com.common.voicedna.network.RxCallback;
import com.common.voicedna.utils.DnaCallback;
import com.common.voicedna.utils.SPManager;
import com.common.voicedna.utils.UuidUtis;
import com.guiying.module.main.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>类说明</p>
 *
 * @version V1.2.0
 * @name MainActivity
 */
public class MainActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();
    }


    protected void initialize() {
        DnaPresenter.getInstance().init(this, "IFCvHTsoBnP16q5noSBOjSBrMncBAF7n", "0hcVkdzbmb6Yvp6HKaVkIRMI45xP8OmX", new DnaCallback<TokenBean>() {
            @Override
            public void onSuccess(@Nullable TokenBean data) {


            }

            @Override
            public void onError(String throwable) {

            }
        });
        DnaPresenter.getInstance().group_create("111").safeSubscribe(new RxCallback<String>() {
            @Override
            public void onSuccess(@Nullable String string) {
                Log.e("1111","11111");
            }
        });
        //获取Token
//        DnaPresenter.getInstance().getToken().safeSubscribe(new RxCallback<TokenBean>() {
//            @Override
//            public void onSuccess(@Nullable TokenBean data) {
//                SPManager.setUserToken(data.getToken());
//            }
//
//            @Override
//            public void onError(Throwable t) {
//                super.onError(t);
//
//            }
//        });
//        获取分组  1cdcb88a98d24de491028154eab57f15  111
//        DnaPresenter.getInstance().getGroup().safeSubscribe(new RxCallback<List<GroupBean>>() {
//            @Override
//            public void onSuccess(@Nullable List<GroupBean> data) {
//                Log.e("11111",data.get(0).getId()+"");
//            }
//        });
//        RxPermissionsUtil.check(this, RxPermissionsUtil.CAMERA_STORAGE, "获取录音，文件读取权限", new RxPermissionsUtil.OnPermissionRequestListener() {
//            @Override
//            public void onSucceed() {

//            }

//            @Override
//            public void onFailed() {
//
//            }
//        });


        //注册
//        String pate =Environment.getExternalStorageDirectory().getAbsolutePath()+ "/1111111.wav";
//        DnaPresenter.getInstance().Uploadbycode(new File(pate), 0, UuidUtis.randomUuid(), new DnaCallback<FileBean>() {
//
//            @Override
//            public void onSuccess(@Nullable FileBean data) {
//                DnaPresenter.getInstance().register("1cdcb88a98d24de491028154eab57f15", data.getFileId(), new DnaCallback<VoiceprintTaskDetailBean>() {
//                    @Override
//                    public void onSuccess(@Nullable VoiceprintTaskDetailBean data) {
// Log.e("111111","111111");
//                    }
//
//                    @Override
//                    public void onError(String throwable) {
//
//                    }
//                });
//            }
//
//            @Override
//            public void onError(String throwable) {
//
//            }
//
//
//        });

//        //验证
        String pate = Environment.getExternalStorageDirectory().getAbsolutePath() + "/1111111.wav";
        DnaPresenter.getInstance().Uploadbycode(new File(pate), 1, UuidUtis.randomUuid(), new DnaCallback<FileBean>() {

            @Override
            public void onSuccess(@Nullable FileBean data) {
                List<AutoRegisData> autoRegis = new ArrayList<>();
                DnaPresenter.getInstance().Voiceoperate("1cdcb88a98d24de491028154eab57f15", data.getFileId(), 0, "", autoRegis, new DnaCallback<VoiceoperateDetailBean>() {
                    @Override
                    public void onSuccess(@Nullable VoiceoperateDetailBean data) {
                        Log.e("111111", data.getTaskResults().get(0).getDescription());
                    }

                    @Override
                    public void onError(String throwable) {
                        Log.e("111111", throwable);
                    }
                });
            }

            @Override
            public void onError(String throwable) {

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
//                ViewManager.getInstance().exitApp(this);
            }
            return false;

        } else if (event.getKeyCode() == KeyEvent.KEYCODE_MENU) {
            return true;
        }
        return super.dispatchKeyEvent(event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        DnaPresenter.getInstance().release();
    }
}
