package com.guiying.module.ui.activity.main;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.common.voicedna.Common.IdVerifier;
import com.common.voicedna.Common.VerifyListener;
import com.common.voicedna.Record.AudioRecorder;
import com.common.voicedna.Record.PcmToWav;
import com.common.voicedna.Record.RecorderListener;
import com.common.voicedna.VoiceRecognize.VoiceRegisterConfig;
import com.common.voicedna.bean.DnaErrorCode;
import com.common.voicedna.bean.VoiceoperateDetailBean;
import com.common.voicedna.bean.VoiceprintTaskDetailBean;
import com.common.voicedna.data.AutoRegisData;
import com.common.voicedna.utils.AppExecutors;
import com.common.voicedna.utils.DnaCallback;
import com.common.voicedna.utils.EmptyUtil;
import com.guiying.module.main.R;
import com.guiying.module.ui.activity.base.BaseActivity;
import com.guiying.module.ui.activity.base.MySPManager;
import com.guiying.module.ui.activity.callback.ITouchEventListener;
import com.guiying.module.ui.activity.dialog.NumberVerificationSuccessPopup;
import com.guiying.module.ui.activity.dialog.SuccessPopup;
import com.guiying.module.ui.activity.dialog.VerificationSuccessPopup;
import com.guiying.module.ui.activity.view.AudioRecordStepView;
import com.guiying.module.ui.activity.view.AudioRecordView;
import com.iva.VoiceAsvSpoofDetect.AsvSpoofDetectOutputData;
import com.iva.VoiceQualityCheck.QualityCheckInfo;
import com.voiceai.voicedna.bean.dto.DynamicNumber;
import com.voiceai.voicedna.bean.dto.DynamicNumerIdnTaskResult;
import com.voiceai.voicedna.bean.dto.DynamicNumerRegisTaskFileResult;
import com.voiceai.voicedna.bean.dto.DynamicNumerRegisTaskResult;
import com.voiceai.voicedna.constant.ErrorCode;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class NumberVoiceDetectionActivity extends BaseActivity implements VerifyListener {
    private AudioRecordView mAudioRecordView;
    private TextView mTextTitle;
    private TextView vmTextPrompt;
    private TextView vmTextDeos;
    private int mRecordMaxNum;
    private String mUserName;
    private AudioRecordStepView mAudioRecordStepView;
    int step = 0;
    int recordNumber = 3;
    List<DynamicNumber> number;
    List<String> stringList;
    String taskId;
    private TextView voice_record_time;
    private ProgressBar progressbar;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_number_voice_detection;
    }

    @Override
    protected void initialize() {
        stringList = new ArrayList<>();
        initView();
        initData();
    }

    private void initView() {
        findViewById(R.id.voice_toolbar).setOnClickListener(this);
        mAudioRecordView = findViewById(R.id.voice__record_view);
        mTextTitle = findViewById(R.id.voice_title);
        vmTextPrompt = findViewById(R.id.voice_record_prompt);
        vmTextDeos = findViewById(R.id.voice_record_deos);
        mAudioRecordStepView = findViewById(R.id.voice_record_step);
        IdVerifier.getInstance().setVerifyListener(this);
        mAudioRecordStepView.setRecordNumber(recordNumber, step);
        progressbar = findViewById(R.id.voice_progress_bar_time);
        voice_record_time = findViewById(R.id.voice_record_time);
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

    public void scheduledExecutor() {
        IdVerifier.getInstance().createDynamicNumberRegisTask(MySPManager.getUserGroupid(), mUserName, new DnaCallback<String>() {
            @Override
            public void onSuccess(@Nullable String data) {
                taskId = data;

            }

            @Override
            public void onError(int code, String msg) {
                Toast.makeText(NumberVoiceDetectionActivity.this, msg, Toast.LENGTH_LONG).show();
                finish();
            }
        });


    }


    private void initData() {
        mRecordMaxNum = getIntent().getIntExtra("mRecordMaxNum", 3);
        mUserName = getIntent().getStringExtra("UserName");
        if (mRecordMaxNum == 0) {
            mTextTitle.setText(R.string.voice_register);
            vmTextPrompt.setText(R.string.voice_record_prompt1);
            getDynamicNumber();
            scheduledExecutor();
        } else if (mRecordMaxNum == 1) {
            Resources resources = getResources();
            String[] stuName = resources.getStringArray(R.array.list_verification);
            int dom = (int) (Math.random() * 9);
            mTextTitle.setText(R.string.voice_verify);
            getDynamicNumber();
        } else if (mRecordMaxNum == 2) {
            Resources resources = getResources();
            String[] stuName = resources.getStringArray(R.array.list_verification);
            mTextTitle.setText(R.string.voice_verify_n);
            getDynamicNumber();
        }

        if (mAudioRecordView != null) {
            mAudioRecordView.setAudioViewTouchListener(mITouchEventListener);
        }
        AudioRecorder.getInstance().setRecorderListener(myRecorderListener);
    }

    public void getDynamicNumber() {
        int useFor = 0;
        if (mRecordMaxNum > 0) {
            useFor = 1;
            recordNumber = 1;
            mAudioRecordStepView.setVisibility(View.INVISIBLE);
        }
        IdVerifier.getInstance().getDynamicNumber(recordNumber, useFor, new DnaCallback<List<DynamicNumber>>() {
            @Override
            public void onSuccess(@Nullable List<DynamicNumber> data) {
                if (data != null && data.size() > 0) {
                    number = data;
                    vmTextDeos.setText(number.get(0).getNumber().substring(0, 4) + "  " + number.get(0).getNumber().substring(4, 8));
                }

            }

            @Override
            public void onError(int code, String msg) {
                Toast toast = Toast.makeText(NumberVoiceDetectionActivity.this, msg, Toast.LENGTH_LONG);
                toast.setGravity(Gravity.BOTTOM, 0, 550);
                toast.show();
                finish();
            }
        });

    }

    private ITouchEventListener mITouchEventListener = new ITouchEventListener() {
        @Override
        public void onLongTap() {
            if (mAudioRecordView != null) {
                mAudioRecordView.start();
                mAudioRecordView.setPromptMsg(R.string.voice_record_prompt_2);
                countDownTimer.start();
                AudioRecorder.getInstance().startRecorder(NumberVoiceDetectionActivity.this);
            }
        }

        @Override
        public void onMoveCancel() {
            if (mAudioRecordView != null) {
                mAudioRecordView.stop();
                countDownTimer.cancel();
                voice_record_time.setVisibility(View.INVISIBLE);
                progressbar.setProgress(30);
                AudioRecorder.getInstance().stopRecorder();
                mAudioRecordView.setPromptMsg(R.string.voice_record_prompt_1);
            }

        }

        @Override
        public void onPrepare() {
        }

        @Override
        public void onMoveUp() {
            if (mAudioRecordView != null) {
                mAudioRecordView.stop();
                countDownTimer.cancel();
                voice_record_time.setVisibility(View.INVISIBLE);
                progressbar.setProgress(30);
                AudioRecorder.getInstance().stopRecorder();
                mAudioRecordView.setPromptMsg(R.string.voice_record_prompt_1);
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
                case 2:
                    String throwable = (String) msg.obj;
//                    mTextResult.setText(throwable);
                    break;
                case 3:
//                    voice_record_time.setText(time + "秒");
                    break;
                case 4:
                    hideProgressDialog();
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
        public void onRecordStop(byte[] bytes) {
            //录音结束
            showProgressDialog();
            if (mRecordMaxNum == 0) {
                IdVerifier.getInstance().submitDynamicNumberRegisTask(taskId, bytes, number.get(0).getNumberId(), new DnaCallback<DynamicNumerRegisTaskFileResult>() {
                    @Override
                    public void onSuccess(@Nullable DynamicNumerRegisTaskFileResult data) {
                        step++;
                        mAudioRecordStepView.setRecordNumber(recordNumber, step);
                        if (step < recordNumber) {
                            vmTextDeos.setText(number.get(0).getNumber().substring(0, 4) + "  " + number.get(0).getNumber().substring(4, 8));

                        }
                        if (step == 3) {
                            register();
                        } else {
                            hideProgressDialog();
                            getDynamicNumber();
                        }
                    }

                    @Override
                    public void onError(int code, String msg) {
                        Toast toast = Toast.makeText(NumberVoiceDetectionActivity.this, msg, Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.BOTTOM, 0, 550);
                        toast.show();
//                        mTextResult.setText(  ErrorCode.getErrorCodeByCode(code).getMsg());
                        getDynamicNumber();
                        hideProgressDialog();
                        if (code == -5029) {
                            finish();
                        }

                    }
                });

            } else {
                Voiceoperate(bytes);
            }

        }

    };

    /**
     * 注册
     */
    public void register() {

        IdVerifier.getInstance().createDynamicNumberRegisTask(taskId, new DnaCallback<DynamicNumerRegisTaskResult>() {
            @Override
            public void onSuccess(@Nullable DynamicNumerRegisTaskResult data) {
                MySPManager.setUserName(mUserName);
                SuccessPopup choiceAreaPop = new SuccessPopup(NumberVoiceDetectionActivity.this);
                choiceAreaPop.showAtLocation(mAudioRecordView, Gravity.CENTER, 0, 0);
                choiceAreaPop.setOnConfirm(new SuccessPopup.OnConfirm() {
                    @Override
                    public void Confirm() {
                        finish();
                    }
                });
                hideProgressDialog();
            }

            @Override
            public void onError(int code, String msg) {
                Toast toast = Toast.makeText(NumberVoiceDetectionActivity.this, msg, Toast.LENGTH_LONG);
                toast.setGravity(Gravity.BOTTOM, 0, 550);
                toast.show();
                finish();
            }
        });
    }

    /**
     * 1:1 验证 or 1:N
     */
    public void Voiceoperate( byte[] bytes) {
        if (number.size() == 0)
            return;
//        float Asv = (float)VoiceRegisterConfig.getVerificationAsv();
//        float Asv1 =  (float)VoiceRegisterConfig.getVerificationAsv1N();
        IdVerifier.getInstance().dynamicNumberIdentity(number.get(0).getNumberId(), bytes, MySPManager.getUserGroupName(), mUserName, new DnaCallback<DynamicNumerIdnTaskResult>() {
            @Override
            public void onSuccess(@Nullable DynamicNumerIdnTaskResult data) {
                hideProgressDialog();
                getDynamicNumber();
                if (data.getExecResults() != null && data.getExecResults().size() > 0) {
                    NumberVerificationSuccessPopup choiceAreaPop = new NumberVerificationSuccessPopup(NumberVoiceDetectionActivity.this, 0, mUserName);
                    choiceAreaPop.showAtLocation(mAudioRecordView, Gravity.CENTER, 0, 0);
                    choiceAreaPop.setData(data.getExecResults());
                    choiceAreaPop.setOnConfirm(new NumberVerificationSuccessPopup.OnConfirm() {
                        @Override
                        public void Confirm() {
                            finish();
                        }
                    });
                } else {
                    NumberVerificationSuccessPopup choiceAreaPop = new NumberVerificationSuccessPopup(NumberVoiceDetectionActivity.this, 1, mUserName);
                    choiceAreaPop.showAtLocation(mAudioRecordView, Gravity.CENTER, 0, 0);
                    choiceAreaPop.setOnConfirm(new NumberVerificationSuccessPopup.OnConfirm() {
                        @Override
                        public void Confirm() {
                            finish();
                        }
                    });
                }
            }

            @Override
            public void onError(int code, String msg) {
                hideProgressDialog();
                Toast toast = Toast.makeText(NumberVoiceDetectionActivity.this, msg, Toast.LENGTH_LONG);
                toast.setGravity(Gravity.BOTTOM, 0, 550);
                toast.show();
                getDynamicNumber();
            }
        });
    }


    @Override
    public boolean onAudioDetection(int var1, QualityCheckInfo inio, AsvSpoofDetectOutputData asvSpoofDetectOutput, String msg) {
        if (var1 != 0) {
            hideProgressDialog();
            Toast toast = Toast.makeText(NumberVoiceDetectionActivity.this, msg, Toast.LENGTH_LONG);
            toast.setGravity(Gravity.BOTTOM, 0, 550);
            toast.show();
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