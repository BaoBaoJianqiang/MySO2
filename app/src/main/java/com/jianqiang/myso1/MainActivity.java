package com.jianqiang.myso1;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.jianqiang.jnihelloworld.JniUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends Activity {
    private String soFileName = "libhello.so";

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);

        Utils.extractAssets(newBase, soFileName);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        File dir = this.getDir("jniLibs", Activity.MODE_PRIVATE);

        File tmpFile = new File(dir.getAbsolutePath() + File.separator + "armeabi-v7a");
        if (!tmpFile.exists()) {
            tmpFile.mkdirs();
        }

        File distFile = new File(tmpFile.getAbsolutePath() + File.separator + "libhello.so");

        System.loadLibrary("goodbye");

        if (Utils.copyFileFromAssets(this, "libhello.so", distFile.getAbsolutePath())){
            System.load(distFile.getAbsolutePath());
        }

        final Button btnShowMessage = (Button) findViewById(R.id.btnShowMessage);
        btnShowMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnShowMessage.setText(new JniUtils().getString());
            }
        });
    }
}
