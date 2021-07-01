package com.guiying.module.ui.activity.main;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Resources;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.common.voicedna.Common.IdVerifier;
import com.common.voicedna.Common.VerifyListener;
import com.common.voicedna.Record.AudioRecorder;
import com.common.voicedna.Record.PCM2WAVListener;
import com.common.voicedna.Record.PcmToWav;
import com.common.voicedna.Record.RecorderListener;
import com.common.voicedna.VoiceRecognize.VoiceRegisterConfig;
import com.common.voicedna.api.DnaPresenter;
import com.common.voicedna.bean.DnaErrorCode;
import com.common.voicedna.bean.VoiceoperateDetailBean;
import com.common.voicedna.bean.VoiceprintTaskDetailBean;
import com.common.voicedna.data.AutoRegisData;
import com.common.voicedna.utils.AppExecutors;
import com.common.voicedna.utils.DnaCallback;
import com.common.voicedna.utils.EmptyUtil;
import com.common.voicedna.utils.UuidUtis;
import com.guiying.module.main.R;
import com.guiying.module.ui.activity.base.BaseActivity;
import com.guiying.module.ui.activity.base.MySPManager;
import com.guiying.module.ui.activity.callback.ITouchEventListener;
import com.guiying.module.ui.activity.dialog.SuccessPopup;
import com.guiying.module.ui.activity.dialog.VerificationSuccessPopup;
import com.guiying.module.ui.activity.view.AudioRecordView;
import com.iva.VoiceAsvSpoofDetect.AsvSpoofDetectOutputData;
import com.iva.VoiceQualityCheck.QualityCheckInfo;
import com.voiceai.voicedna.bean.dto.IdnTaskDetail;
import com.voiceai.voicedna.bean.dto.RegisTaskDetail;
import com.voiceai.voicedna.constant.ErrorCode;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.SplittableRandom;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class VoiceDetectionActivity extends BaseActivity implements VerifyListener {
    private AudioRecordView mAudioRecordView;
    private TextView mTextTitle;
    private TextView voice_record_time;
    private TextView vmTextPrompt;
    private TextView vmTextDeos;
    private int mRecordMaxNum;
    private String mUserName;
    private ProgressBar progressbar;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_voice_detection;
    }

    @Override
    protected void initialize() {
        initView();
        initData();
    }

    private void initView() {
        findViewById(R.id.voice_toolbar).setOnClickListener(this);
        mAudioRecordView = findViewById(R.id.voice__record_view);
        mTextTitle = findViewById(R.id.voice_title);
        voice_record_time = findViewById(R.id.voice_record_time);
        vmTextPrompt = findViewById(R.id.voice_record_prompt);
        vmTextDeos = findViewById(R.id.voice_record_deos);
        progressbar = findViewById(R.id.voice_progress_bar_time);
        IdVerifier.getInstance().setVerifyListener(this);

    }

    /**
     * CountDownTimer 实现倒计时
     */
    private CountDownTimer countDownTimer = new CountDownTimer(30 * 1000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            String value = String.valueOf((int) (millisUntilFinished / 1000));
            voice_record_time.setText(value + "S");
            progressbar.setProgress(Integer.valueOf(value));
            voice_record_time.setVisibility(View.VISIBLE);
        }

        @Override
        public void onFinish() {
            voice_record_time.setVisibility(View.INVISIBLE);
            progressbar.setProgress(30);
            if (mAudioRecordView != null) {
                mAudioRecordView.stop();
                AudioRecorder.getInstance().stopRecorder();
            }
        }
    };


    private void initData() {
        mRecordMaxNum = getIntent().getIntExtra("mRecordMaxNum", 3);
        mUserName = getIntent().getStringExtra("UserName");
        if (mRecordMaxNum == 0) {
            mTextTitle.setText(R.string.voice_register);
            vmTextPrompt.setText(R.string.voice_record_prompt1);
            Resources resources = getResources();
            String[] stuName = resources.getStringArray(R.array.list_register);
            int dom = (int) (Math.random() * 9);
            vmTextDeos.setText(stuName[dom]);
        } else if (mRecordMaxNum == 1) {
            Resources resources = getResources();
            String[] stuName = resources.getStringArray(R.array.list_verification);
            int dom = (int) (Math.random() * 9);
            vmTextDeos.setText(stuName[dom]);
            mTextTitle.setText(R.string.voice_verify);
        } else if (mRecordMaxNum == 2) {
            Resources resources = getResources();
            String[] stuName = resources.getStringArray(R.array.list_verification);
            int dom = (int) (Math.random() * 9);
            vmTextDeos.setText(stuName[dom]);
            mTextTitle.setText(R.string.voice_verify_n);
        }

        if (mAudioRecordView != null) {
            mAudioRecordView.setAudioViewTouchListener(mITouchEventListener);
        }
        AudioRecorder.getInstance().setRecorderListener(myRecorderListener);
    }


    private ITouchEventListener mITouchEventListener = new ITouchEventListener() {
        @Override
        public void onLongTap() {
            if (mAudioRecordView != null) {
                mAudioRecordView.start();
                mAudioRecordView.setPromptMsg(R.string.voice_record_prompt_2);
                AudioRecorder.getInstance().startRecorder(VoiceDetectionActivity.this);
                countDownTimer.start();
            }
        }

        @Override
        public void onMoveCancel() {
            if (mAudioRecordView != null) {
                mAudioRecordView.stop();
                AudioRecorder.getInstance().stopRecorder();
                countDownTimer.cancel();
                voice_record_time.setVisibility(View.INVISIBLE);
                progressbar.setProgress(30);
            }

        }

        @Override
        public void onPrepare() {
        }

        @Override
        public void onMoveUp() {
            if (mAudioRecordView != null) {
                mAudioRecordView.stop();
                AudioRecorder.getInstance().stopRecorder();
                mAudioRecordView.setPromptMsg(R.string.voice_record_prompt_1);
                countDownTimer.cancel();
                voice_record_time.setVisibility(View.INVISIBLE);
                progressbar.setProgress(30);

            }


        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        if (mAudioRecordView != null) {
            mAudioRecordView.setPromptMsg(R.string.voice_record_prompt_1);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mAudioRecordView != null) {
            mAudioRecordView.stop();
        }
    }

    @Override
    protected void onDestroy() {
        if (mAudioRecordView != null) {
            mAudioRecordView.releasedAudioRecordView();
        }
        super.onDestroy();
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    showProgressDialog();
                    break;
            }

        }
    };
    private RecorderListener myRecorderListener = new RecorderListener() {

        @Override
        public void recordOfByte(byte[] data, int bufferSizeInBytes, int readsize) {

        }

        @Override
        public void onRecordStart(int resultCode) {
            //录音开始
        }

        @Override
        public void onRecordingError(String error) {
            //录音错误
        }

        @Override
        public void onRecordStop( byte[] bytes) {
            //录音结束
            handler.sendEmptyMessage(1);
            if (mRecordMaxNum == 0) {
                register(bytes);
            } else if (mRecordMaxNum == 1) {
                Voiceoperate(bytes);
            } else if (mRecordMaxNum == 2) {
                Voiceoperate_N(bytes);
            }

        }

    };

    public void register( byte[] bytes) {
        IdVerifier.getInstance().register(MySPManager.getUserGroupName(), bytes,mUserName, new DnaCallback<RegisTaskDetail>() {
            @Override
            public void onSuccess(@Nullable RegisTaskDetail data) {
                MySPManager.setUserName(mUserName);
                hideProgressDialog();
                SuccessPopup choiceAreaPop = new SuccessPopup(VoiceDetectionActivity.this);
                choiceAreaPop.showAtLocation(mAudioRecordView, Gravity.CENTER, 0, 0);
                choiceAreaPop.setOnConfirm(new SuccessPopup.OnConfirm() {
                    @Override
                    public void Confirm() {
                        finish();
                    }
                });
            }

            @Override
            public void onError(int code,String msg) {
                Toast.makeText(VoiceDetectionActivity.this, msg, Toast.LENGTH_LONG).show();
                hideProgressDialog();
            }
        });
    }

    /**
     * 1:1 验证
     *
     * @param b
     */
    public void Voiceoperate(byte[] b) {
        if (TextUtils.isEmpty(mUserName)) {
            hideProgressDialog();
            return;
        }

        IdVerifier.getInstance().identify(MySPManager.getUserGroupName(), b, mUserName, new DnaCallback<IdnTaskDetail>() {
            @Override
            public void onSuccess(@Nullable IdnTaskDetail data) {
                hideProgressDialog();
                if (data.getTaskResults().get(0).getExecResults().size() > 0) {
                    VerificationSuccessPopup choiceAreaPop = new VerificationSuccessPopup(VoiceDetectionActivity.this);
                    choiceAreaPop.showAtLocation(mAudioRecordView, Gravity.CENTER, 0, 0);
                    choiceAreaPop.setData(data.getTaskResults().get(0).getExecResults());
                    choiceAreaPop.setOnConfirm(new VerificationSuccessPopup.OnConfirm() {
                        @Override
                        public void Confirm() {
                            finish();
                        }
                    });
                } else {

                    VerificationSuccessPopup choiceAreaPop = new VerificationSuccessPopup(VoiceDetectionActivity.this,1);
                    choiceAreaPop.showAtLocation(mAudioRecordView, Gravity.CENTER, 0, 0);
                    choiceAreaPop.setOnConfirm(new VerificationSuccessPopup.OnConfirm() {
                        @Override
                        public void Confirm() {
                            finish();
                        }
                    });

                }
            }

            @Override
            public void onError(int code,String msg) {
                Toast.makeText(VoiceDetectionActivity.this, msg, Toast.LENGTH_LONG).show();
                hideProgressDialog();
            }
        });
    }

    /**
     * 1:N 验证
     *
     * @param b
     */
    public void Voiceoperate_N(byte[] b) {

        IdVerifier.getInstance().identify(MySPManager.getUserGroupName(), b, "", new DnaCallback<IdnTaskDetail>() {
            @Override
            public void onSuccess(@Nullable IdnTaskDetail data) {
                hideProgressDialog();
                if (data.getTaskResults().get(0).getExecResults().size() > 0) {
                    VerificationSuccessPopup choiceAreaPop = new VerificationSuccessPopup(VoiceDetectionActivity.this,2);
                    choiceAreaPop.showAtLocation(mAudioRecordView, Gravity.CENTER, 0, 0);
                    choiceAreaPop.setData(data.getTaskResults().get(0).getExecResults());
                    choiceAreaPop.setOnConfirm(new VerificationSuccessPopup.OnConfirm() {
                        @Override
                        public void Confirm() {
                            finish();
                        }
                    });
                } else {
                    VerificationSuccessPopup choiceAreaPop = new VerificationSuccessPopup(VoiceDetectionActivity.this,3);
                    choiceAreaPop.showAtLocation(mAudioRecordView, Gravity.CENTER, 0, 0);
                    choiceAreaPop.setOnConfirm(new VerificationSuccessPopup.OnConfirm() {
                        @Override
                        public void Confirm() {
                            finish();
                        }
                    });
                }
            }

            @Override
            public void onError(int code,String msg) {
                hideProgressDialog();
                Toast.makeText(VoiceDetectionActivity.this, msg, Toast.LENGTH_LONG).show();

            }
        });
    }

    @Override
    public boolean onAudioDetection(int var1, QualityCheckInfo inio, AsvSpoofDetectOutputData asvSpoofDetectOutput, String mgs) {
        if (var1 != 0) {
            Toast.makeText(this, mgs, Toast.LENGTH_LONG).show();
            hideProgressDialog();
        }

        return false;
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        if (view.getId() == R.id.voice_toolbar) {
            finish();
        }
    }
}