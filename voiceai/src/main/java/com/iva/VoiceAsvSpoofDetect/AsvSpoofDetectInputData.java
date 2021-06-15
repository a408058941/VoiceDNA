package com.iva.VoiceAsvSpoofDetect;

public class AsvSpoofDetectInputData {
   public byte[] data_buffer_b;
   public short[] data_buffer_s;
   public float[] data_buffer_f;
    public int data_type = 1;
    // 1. data_type = 1, means input data is byte[];
    // 2. data_type = 2, means input data is short[];
    // 3. data_type = 3, means input data is float[];
    public int data_len;
    public int num_channels = 1;
    public int sample_rate = 16000;
    public boolean interlace = false;
    public boolean end_of_data = false;
}
