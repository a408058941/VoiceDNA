package com.common.voicedna.Record;

import android.content.Context;
import android.media.AudioRecord;
import android.os.Environment;
import android.util.Log;


import com.common.voicedna.utils.AppExecutors;
import com.common.voicedna.utils.EmptyUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AudioRecorder {
    private static AudioRecorder audioRecorder;
    private AudioRecord audioRecord;
    // 缓冲区字节大小
    private int bufferSizeInBytes = 0;
    //录音状态
    private volatile Status status = Status.STATUS_NO_READY;
    RecorderListener listener;

    //单例模式
    public static AudioRecorder getInstance() {
        if (audioRecorder == null) {
            audioRecorder = new AudioRecorder();
        }
        return audioRecorder;

    }

    /**
     * 创建录音对象
     */
    public int initRecorder() {//String fileName, int audioSource, int sampleRateInHz, int channelConfig, int audioFormat
        // 获得缓冲区字节大小
        bufferSizeInBytes = AudioRecord.getMinBufferSize(Constants.SAMPLE_RATE_IN_HZ,
                Constants.CHANNEL_IN, Constants.ENCODING_PCM);
        audioRecord = new AudioRecord(Constants.AUDIOROUCE, Constants.SAMPLE_RATE_IN_HZ, Constants.CHANNEL_IN, Constants.ENCODING_PCM, bufferSizeInBytes);
        return ErrorCode.SUCCESS;
    }

    /**
     * 开启录音
     *
     * @return
     */
    public int startRecorder(Context context,String mUserName) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (status != Status.STATUS_START) {
                        try {
                            if (audioRecord==null){
                                initRecorder();
                            }
                            audioRecord.startRecording();
                            if (listener != null)
                                listener.onRecordStart(0);
                        } catch (Exception e) {
                            listener.onRecordingError(e.toString());
                            return;
                        }
                        status = Status.STATUS_START;

                        writeDataTOFile(context,mUserName);
                    }

                } catch (Exception ex) {

                }

            }
        }).start();
        return ErrorCode.SUCCESS;
    }

    File file;
    private String date;
    private String currentFileName;
    private String fileName;

    private void writeDataTOFile(Context context,String mUserName) {
        int offset = 0;
        FileOutputStream fos = null;
        try {
//            String currentFileName = Constants.ROOT_PATH;
            File filePath = context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
            currentFileName = filePath.getAbsolutePath();
            file = new File(currentFileName + "/VoiceKWS");
            if (!file.exists()) {
                file.mkdirs();
            }
            SimpleDateFormat sTimeFormat = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
            String date = sTimeFormat.format(new Date());
            if (!new File(currentFileName + "/voiceai").exists()) {
                new File(currentFileName + "/voiceai").mkdirs();
            }
            String filename = "voiceai/" + date + ".pcm";
            file = new File(currentFileName, filename);
            String fieReact = file.getAbsolutePath();
            fileName = fieReact;
            fos = new FileOutputStream(file);// 建立一个可存取字节的文件
        } catch (IllegalStateException e) {
            listener.onRecordingError(e.toString());
            return;
        } catch (FileNotFoundException e) {
            listener.onRecordingError(e.toString());
            return;

        } catch (Exception e) {
            listener.onRecordingError(e.toString());
            return;

        }
        //将录音状态设置成正在录音状态
        status = Status.STATUS_START;
        while (status == Status.STATUS_START && fos != null) {
            // new一个byte数组用来存一些字节数据，大小为缓冲区大小
            byte[] audiodata = new byte[bufferSizeInBytes];
            int readsize = 0;
            if (audioRecord == null) {
                return;
            }
            readsize = audioRecord.read(audiodata, 0, bufferSizeInBytes);

            if (AudioRecord.ERROR_INVALID_OPERATION != readsize && fos != null) {
                //用于拓展业务
                byte[] volAudioData1 = new byte[bufferSizeInBytes];
                System.arraycopy(audiodata, 0, volAudioData1, 0, bufferSizeInBytes);
                if (listener != null)
                    listener.recordOfByte(volAudioData1, bufferSizeInBytes, readsize);
                try {
                    fos.write(volAudioData1);
                    fos.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        try {
            if (fos != null) {
                fos.flush();
                fos.close();// 关闭写入流
            }
            if (file != null) {
                if (listener != null) {
                    byte[] b = PcmToWav.toByteArray(file);
                   if ( EmptyUtil.isEmpty(mUserName)){
                       mUserName=System.currentTimeMillis()+"";
                   }
                    PcmToWav. saveAudioDataToFile(context, b,mUserName , new PCM2WAVListener() {
                        @Override
                        public void finalResult(int resultCode,File f) {
                            if (status == Status.STATUS_STOP) {
                                if (resultCode==ErrorCode.SUCCESS){
                                    AppExecutors.getInstance().mainThread().execute(new Runnable() {
                                        @Override
                                        public void run() {
                                            listener.onRecordStop(f);
                                        }
                                    });

                                }
                            }
                            PcmToWav.deleteFile(file);
                        }
                    });
                }
            }
        } catch (
                IOException e) {
            Log.e("AudioRecorder", e.getMessage());
        }
    }

    public void setRecorderListener(RecorderListener listener) {
        this.listener = listener;
    }

    /**
     * 录音对象的状态
     */
    public enum Status {
        //未开始
        STATUS_NO_READY,
        //预备
        STATUS_READY,
        //录音
        STATUS_START,
        //暂停
        STATUS_PAUSE,
        //停止
        STATUS_STOP

    }

    /**
     * 取消录音
     */
    public void canel() {
//        filesName.clear();
        fileName = null;
        if (audioRecord != null) {
            audioRecord.release();
            audioRecord = null;
        }

        status = Status.STATUS_NO_READY;
    }

    /**
     * 释放资源
     */
    public void release() {
        Log.d("AudioRecorder", "===release===");
        //假如有暂停录音
        if (audioRecord != null) {
            audioRecord.release();
            audioRecord = null;
        }
        status = Status.STATUS_NO_READY;
    }

    /**
     * 停止录音
     */
    public int stopRecorder() {
        Log.d("AudioRecorder", "===stopRecord===");
        if (status == Status.STATUS_NO_READY || status == Status.STATUS_READY) {
            Log.d("AudioRecorder", "===stopRecord=== fail without init");
            return ErrorCode.RECORDER_MOT_BEGIN;
        } else {
            try {
                status = Status.STATUS_STOP;
                audioRecord.stop();
            } catch (Exception e) {
                Log.d("AudioRecorder", "===stopRecord=== error");
                return ErrorCode.CLOSE_RECORD_FAIL;
            }

            return ErrorCode.SUCCESS;
        }

    }

    /**
     * 暂停录音
     */
    public void pauseRecorder() {
        Log.d("AudioRecorder", "===pauseRecord===");
        if (status != Status.STATUS_START) {
            if (listener != null) listener.onRecordingError("录音暂停失败");
        } else {
            audioRecord.stop();
            status = Status.STATUS_PAUSE;
            if (listener != null) listener.onRecordingError("录音已暂停");
        }
    }

}
