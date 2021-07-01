package com.iva;


import com.iva.FlacFormatTransformation.AudioDataInfo;

public class FormatConversionNative {
    static {
        System.loadLibrary("social_format-lib");
    }

    public static  native int formatConversionToFlac(byte[] pcm_data, AudioDataInfo audioDataInfo);
}