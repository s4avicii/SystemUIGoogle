package com.google.android.systemui.gesture;

import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.util.Log;
import androidx.appcompat.view.SupportMenuInflater$$ExternalSyntheticOutline0;
import androidx.coordinatorlayout.R$styleable;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.nio.channels.FileChannel;
import java.util.HashMap;
import org.tensorflow.lite.Interpreter;

public final class BackGestureTfClassifierProviderGoogle extends R$styleable {
    public Interpreter mInterpreter = null;
    public AssetFileDescriptor mModelFileDescriptor = null;
    public float[][] mOutput = ((float[][]) Array.newInstance(float.class, new int[]{1, 1}));
    public HashMap mOutputMap = new HashMap();
    public final String mVocabFile;

    public final HashMap loadVocab(AssetManager assetManager) {
        BufferedReader bufferedReader;
        HashMap hashMap = new HashMap();
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(assetManager.open(this.mVocabFile)));
            int i = 0;
            while (true) {
                String readLine = bufferedReader.readLine();
                if (readLine == null) {
                    break;
                }
                hashMap.put(readLine, Integer.valueOf(i));
                i++;
            }
            bufferedReader.close();
        } catch (Exception e) {
            Log.e("BackGestureTfClassifier", "Load vocab file error: ", e);
        } catch (Throwable th) {
            th.addSuppressed(th);
        }
        return hashMap;
        throw th;
    }

    public final float predict(Object[] objArr) {
        Interpreter interpreter = this.mInterpreter;
        if (interpreter == null) {
            return -1.0f;
        }
        interpreter.runForMultipleInputsOutputs(objArr, this.mOutputMap);
        return this.mOutput[0][0];
    }

    public final void release() {
        Interpreter interpreter = this.mInterpreter;
        if (interpreter != null) {
            interpreter.close();
        }
        AssetFileDescriptor assetFileDescriptor = this.mModelFileDescriptor;
        if (assetFileDescriptor != null) {
            try {
                assetFileDescriptor.close();
            } catch (Exception e) {
                Log.e("BackGestureTfClassifier", "Failed to close model file descriptor: ", e);
            }
        }
    }

    public BackGestureTfClassifierProviderGoogle(AssetManager assetManager, String str) {
        this.mVocabFile = SupportMenuInflater$$ExternalSyntheticOutline0.m4m(str, ".vocab");
        this.mOutputMap.put(0, this.mOutput);
        try {
            AssetFileDescriptor openFd = assetManager.openFd(str + ".tflite");
            this.mModelFileDescriptor = openFd;
            this.mInterpreter = new Interpreter(openFd.createInputStream().getChannel().map(FileChannel.MapMode.READ_ONLY, this.mModelFileDescriptor.getStartOffset(), this.mModelFileDescriptor.getDeclaredLength()));
        } catch (Exception e) {
            Log.e("BackGestureTfClassifier", "Load TFLite file error:", e);
        }
    }
}
