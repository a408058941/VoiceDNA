package com.iva.constant;

import android.content.Context;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FileUtil {

    public static int copyFileToSD(Context context, String strInFileName, String strOutFilePath) throws IOException {
        File floder = new File(strOutFilePath);
        if (!floder.exists()) {
            floder.mkdirs();
        }
        File file = new File(strOutFilePath, strInFileName);
//        if(!file.exists()){
//            file.mkdirs();
//        }
        OutputStream myOutput = new FileOutputStream(file);
        InputStream myInput = context.getAssets().open(strInFileName);

        byte[] buffer = new byte[1024];
        int length = myInput.read(buffer);
        while (length > 0) {
            myOutput.write(buffer, 0, length);
            length = myInput.read(buffer);
        }
        myOutput.flush();
        myInput.close();
        myOutput.close();
        return ErrorCode.SUCCESS;
    }
}
