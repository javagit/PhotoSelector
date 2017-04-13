package com.fire.photoselector.utils;

import java.text.DecimalFormat;

/**
 * Created by Fire on 2017/4/12.
 */

public class GetFileSize {
    public static String getSize(long size) {
        float f = (float) size / (1024 * 1024);
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(f) + "MB";
    }
}
