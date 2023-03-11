package com.leeson.image_pickers.utils;

import android.content.Context;
import android.net.Uri;

import com.luck.picture.lib.engine.CompressFileEngine;
import com.luck.picture.lib.interfaces.OnKeyValueResultCallbackListener;
import com.luck.picture.lib.utils.DateUtils;

import java.io.File;
import java.util.ArrayList;

import top.zibin.luban.Luban;
import top.zibin.luban.OnNewCompressListener;
import top.zibin.luban.OnRenameListener;

/**
 * Created by robot on 2022/3/30 13:43.
 *
 * @author robot < robot >
 */

public class ImageCompressEngine implements CompressFileEngine {

    int compressSize;

    public ImageCompressEngine(int compressSize) {
        this.compressSize = compressSize;
    }

    @Override
    public void onStartCompress(Context context, ArrayList<Uri> source, OnKeyValueResultCallbackListener call) {

        Luban.with(context).load(source).ignoreBy(compressSize).setRenameListener(new OnRenameListener() {
            @Override
            public String rename(String filePath) {
                int indexOf = filePath.lastIndexOf(".");
                String postfix = indexOf != -1 ? filePath.substring(indexOf) : ".jpg";
                return DateUtils.getCreateFileName("CMP_") + postfix;
            }
        }).setCompressListener(new OnNewCompressListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(String source, File compressFile) {
                if (call != null) {
                    call.onCallback(source, compressFile.getAbsolutePath());
                }
            }

            @Override
            public void onError(String source, Throwable e) {
                if (call != null) {
                    call.onCallback(source, null);
                }
            }
        }).launch();
    }
}
