package com.visionin.core;

import android.app.Activity;
import android.content.Context;

import com.rex.load.NativeLoad;
import com.rex.utils.DeviceUtil;
import com.rex.utils.FileUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

/**
 * Created by Visionin on 16/9/10.
 */
public class VSFacer {
    static {
        long so = NativeLoad.loadSo("libvisionin.so");
        NativeLoad.registJNIMethod(so, "com/visionin/core/VSFacer", "loadFacerBuffer", "(Ljava/lang/String;[B)Z");
        NativeLoad.registJNIMethod(so, "com/visionin/core/VSFacer", "loadFacerPath", "(Ljava/lang/String;Ljava/lang/String;)Z");
        NativeLoad.registJNIMethod(so, "com/visionin/core/VSFacer", "destroyFacer", "()V");

        NativeLoad.registJNIMethod(so, "com/visionin/core/VSFacer", "startFacerTracking", "()V");
        NativeLoad.registJNIMethod(so, "com/visionin/core/VSFacer", "stopFacerTracking", "()V");
        NativeLoad.registJNIMethod(so, "com/visionin/core/VSFacer", "isFacerTracking", "()Z");
        NativeLoad.registJNIMethod(so, "com/visionin/core/VSFacer", "getFacerMarks", "(I)[F");

        NativeLoad.registJNIMethod(so, "com/visionin/core/VSFacer", "startFacerShaper", "()V");
        NativeLoad.registJNIMethod(so, "com/visionin/core/VSFacer", "stopFacerShaper", "()V");
//        NativeLoad.registJNIMethod(so, "com/visionin/core/VSFacer", "setShaping", "(IF)V");
//
//        NativeLoad.registJNIMethod(so, "com/visionin/core/VSFacer", "setMarker", "(Z)V");
//        NativeLoad.registJNIMethod(so, "com/visionin/core/VSFacer", "getFacerMarks", "()[F");
    }

    public static boolean initialize(Context context){
        InputStream is = null;
        try {
            is = context.getResources().getAssets().open("facer.model");
            OutputStream os = FileUtil.fileOutputStream("/data/data/"+ DeviceUtil.getPackageName(context) +"/__resource/facer.model");
            FileUtil.write(os, is);

            return loadFacerPath(null, null);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean initialize(String model, String lisence){
        return loadFacerPath(model, lisence);
    }

    protected static native boolean loadFacerBuffer(String path, byte[] lisence);

    /**
     * 加载人脸资源
     * @param path
     * @param lisence
     * @return
     */
    protected static native boolean loadFacerPath(String path, String lisence);

    /**
     * 销毁人脸资源
     */
    public static native void destroyFacer();

    /**
     * 开始人脸追踪
     */
    public static native void startFacerTracking();

    /**
     * 结束人脸追踪
     */
    public static native void stopFacerTracking();

    /**
     * 是否正在运行人脸追踪
     * @return
     */
    public static native boolean isFacerTracking();

    /**
     * 获取人脸的68个点坐标
     * @param face 第几个人脸
     * @return
     */
    public static native float[] getFacerMarks(int face);

    /**
     * 开始整形
     */
    public static native void startFacerShaper();

    /**
     * 结束整形
     */
    public static native void stopFacerShaper();
//    public native void setShaping(int cmd, float strength);
//
//    public native void setMarker(boolean flag);
//    public native float[] getFacerMarks();
}
