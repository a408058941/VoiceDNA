package com.common.voicedna.Common;

import android.text.TextUtils;

import androidx.annotation.Nullable;

import com.common.voicedna.VoiceRecognize.VoiceRegisterConfig;
import com.common.voicedna.api.DnaPresenter;
import com.common.voicedna.bean.VoiceoperateDetailBean;
import com.common.voicedna.bean.VoiceoperateTaskBean;
import com.common.voicedna.bean.VoiceprintTaskBean;
import com.common.voicedna.bean.VoiceprintTaskDetailBean;
import com.common.voicedna.data.AutoRegisData;
import com.common.voicedna.network.RxCallback;
import com.common.voicedna.utils.AppExecutors;
import com.common.voicedna.utils.DnaCallback;
import com.common.voicedna.utils.InstanceUtil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class Dispatcher {
    private static Dispatcher instance;
    private static Object object = new Object();

    public static Dispatcher getInstance() {
        if (instance == null) {
            synchronized (object) {
                if (instance == null) {
                    instance = InstanceUtil.getInstance(Dispatcher.class);
                }
            }
        }
        return instance;
    }





}
