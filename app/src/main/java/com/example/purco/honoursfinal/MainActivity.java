package com.example.purco.honoursfinal;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

//import com.example.purco.honoursfinal.view.MyGLSurfaceView;

public class MainActivity extends AppCompatActivity {
    private Proto1View proto1View;
    private ControlView controlView;
    private Proto2View proto2View;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        controlView = new ControlView(this);



        proto1View = new Proto1View(this);
        proto2View = new Proto2View(this);
        verifyStoragePermissions(this);

//        try {
//            String fileName = "testFileName.txt";
//            File root = new File(Environment.getExternalStorageDirectory(), "testDir");
//            if (!root.exists()) {
//                root.mkdirs();
//            }
//            File gpxfile = new File(root, fileName);
//            FileWriter writer = new FileWriter(gpxfile);
//            writer.append("Testing email txt attachment.");
//            writer.flush();
//            writer.close();
//            sendEmail(gpxfile);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }



    }

    // Storage Permissions
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };



    /**
     * Checks if the app has permission to write to device storage
     *
     * If the app does not has permission then the user will be prompted to grant permissions
     *
     * @param activity
     */
    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }
    public void switchControl(View view)
    {

        setContentView(controlView);
    }

    protected void sendEmail(File file){
        Uri path = Uri.fromFile(file); // This guy gets the job done!

        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("message/rfc822");
        i.putExtra(Intent.EXTRA_SUBJECT, "Test subject");
        i.putExtra(Intent.EXTRA_TEXT, "This is the body of the email");
        i.putExtra(Intent.EXTRA_STREAM, path); // Include the path
        try {
            startActivity(Intent.createChooser(i, "Send mail..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
        }
    }
    public void switchProto1(View view)
    {

        setContentView(proto1View);
    }
    public void switchProto2(View view)
    {

        setContentView(proto2View);

    }
}
