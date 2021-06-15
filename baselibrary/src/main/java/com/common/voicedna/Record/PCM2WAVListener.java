package com.common.voicedna.Record;

import java.io.File;

public interface PCM2WAVListener {
    void finalResult(int resultCode, File file);
}
