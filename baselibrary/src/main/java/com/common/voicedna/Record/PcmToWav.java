package com.common.voicedna.Record;

import android.content.Context;
import android.os.Environment;
import android.util.Log;


import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class PcmToWav {

    /**
     * 清除文件
     *
     * @param filePathList
     */
    private static void clearFiles(List<String> filePathList) {
        for (int i = 0; i < filePathList.size(); i++) {
            File file = new File(filePathList.get(i));
            if (file.exists()) {
                file.delete();
            }
        }
    }
    public static void saveAudioDataToFile(Context context, byte[] audioData, String outFilename, PCM2WAVListener listener) {
        int channels = 1;
        FileOutputStream fos = null;
        InputStream is = null;
        File filePath = context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
        String currentFileName = filePath.getAbsolutePath();
        String wavRootDir =currentFileName + "/VoiceKWS";
        File wavFile = new File(wavRootDir);
        if (!wavFile.exists()){
            wavFile.mkdir();
        }

        File file = new File(wavRootDir+"/"+outFilename+".wav");
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            fos = new FileOutputStream(file);
            long byteRate = 16 * 16000 * channels / 8;
            byte[] wavHead = getWavHeader(audioData.length, audioData.length+36,16000, channels, byteRate);
            fos.write(wavHead);
            is = new ByteArrayInputStream(audioData);
            byte[] buff = new byte[1024];
            int len = 0;
            while((len=is.read(buff))!=-1){
                fos.write(buff, 0, len);
            }

            listener.finalResult(ErrorCode.SUCCESS,file);
        } catch (Exception e) {
            listener.finalResult(ErrorCode.FAIL,null);
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
                if(is != null){
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
                listener.finalResult(ErrorCode.FAIL,null);
            }
        }

    }


    public static byte[] getWavHeader(long totalAudioLen,
                                      long totalDataLen, long longSampleRate, int channels, long byteRate) {
        byte[] header = new byte[44];
        header[0] = 'R'; // RIFF/WAVE header
        header[1] = 'I';
        header[2] = 'F';
        header[3] = 'F';
        header[4] = (byte) (totalDataLen & 0xff);
        header[5] = (byte) ((totalDataLen >> 8) & 0xff);
        header[6] = (byte) ((totalDataLen >> 16) & 0xff);
        header[7] = (byte) ((totalDataLen >> 24) & 0xff);
        header[8] = 'W';
        header[9] = 'A';
        header[10] = 'V';
        header[11] = 'E';
        header[12] = 'f'; // 'fmt ' chunk
        header[13] = 'm';
        header[14] = 't';
        header[15] = ' ';
        header[16] = 16; // 4 bytes: size of 'fmt ' chunk
        header[17] = 0;
        header[18] = 0;
        header[19] = 0;
        header[20] = 1; // format = 1
        header[21] = 0;
        header[22] = (byte) channels;
        header[23] = 0;
        header[24] = (byte) (longSampleRate & 0xff);
        header[25] = (byte) ((longSampleRate >> 8) & 0xff);
        header[26] = (byte) ((longSampleRate >> 16) & 0xff);
        header[27] = (byte) ((longSampleRate >> 24) & 0xff);
        header[28] = (byte) (byteRate & 0xff);
        header[29] = (byte) ((byteRate >> 8) & 0xff);
        header[30] = (byte) ((byteRate >> 16) & 0xff);
        header[31] = (byte) ((byteRate >> 24) & 0xff);
        header[32] = (byte) (1 * 16 / 8); // block align
        header[33] = 0;
        header[34] = 16; // bits per sample
        header[35] = 0;
        header[36] = 'd';
        header[37] = 'a';
        header[38] = 't';
        header[39] = 'a';
        header[40] = (byte) (totalAudioLen & 0xff);
        header[41] = (byte) ((totalAudioLen >> 8) & 0xff);
        header[42] = (byte) ((totalAudioLen >> 16) & 0xff);
        header[43] = (byte) ((totalAudioLen >> 24) & 0xff);
        char a = '\0';
        return header;
    }

    /**
     * the traditional io way
     *
     * @return
     * @throws IOException
     */
    public static byte[] toByteArray(File f) throws IOException {

        if (!f.exists()) {
            throw new FileNotFoundException(f.getAbsolutePath());
        }
        ByteArrayOutputStream bos = new ByteArrayOutputStream((int) f.length());
        BufferedInputStream in = null;
        try {
            in = new BufferedInputStream(new FileInputStream(f));
            int buf_size = 1024;
            byte[] buffer = new byte[buf_size];
            int len = 0;
            while (-1 != (len = in.read(buffer, 0, buf_size))) {
                bos.write(buffer, 0, len);
            }
            return bos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            bos.close();
        }
    }
    //删除文件夹及文件夹下所有文件
    public static void deleteFile(File file) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                File f = files[i];
                deleteFile(f);
            }
            file.delete();
        } else if (file.exists()) {
            file.delete();
        }
    }
}
