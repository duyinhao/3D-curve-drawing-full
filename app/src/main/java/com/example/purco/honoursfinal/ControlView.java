package com.example.purco.honoursfinal;

/**
 * Created by Purco on 9/21/2016.
 */

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.GLUtils;
import android.os.Environment;
import android.view.MotionEvent;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;


class ControlView extends GLSurfaceView {
    long timeCounter;
    private final ControlRenderer mRenderer;
    String mode;
  //  File file;

  private float[] previousArcBallCoord;
    private float[] currentArcBallCoord;
    public ControlView(Context context){
        super(context);
        previousArcBallCoord = new float[3];
         currentArcBallCoord = new float[3];
        timeCounter = 0;
        // Create an OpenGL ES 2.0 context

        setEGLContextClientVersion(2);

        mRenderer = new ControlRenderer(context);
        mode = "draw";
        // Set the Renderer for drawing on the GLSurfaceView
        setRenderer(mRenderer);
        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);





            File file = new File(Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_PICTURES), "test");
            if (!file.mkdirs()) {
                System.out.println("failed");
            }


//            Uri path = Uri.fromFile(file);
//            Intent emailIntent = new Intent(Intent.ACTION_SEND);
//// set the type to 'email'
//            emailIntent.setType("vnd.android.cursor.dir/email");
//            String to[] = {"asd@gmail.com"};
//            emailIntent.putExtra(Intent.EXTRA_EMAIL, to);
//// the attachment
//            emailIntent.putExtra(Intent.EXTRA_STREAM, path);
//// the mail subject
//            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject");
//            context.startActivity(Intent.createChooser(emailIntent, "Send email..."));


//        file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
    }
    private final float TOUCH_SCALE_FACTOR = 180.0f / 320;
    private float mPreviousX;
    private float mPreviousY;

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        // MotionEvent reports input details from the touch screen
        // and other input controls. In this case, you are only
        // interested in events where the touch position changed.


        float x = e.getX();
        float y = e.getY();
        //mRenderer.setA ngle(mRenderer.getAngle()+1);
        double width =  getWidth()*0.8;
        if(y<200)
        {
            if(x<200) {
                mode = "draw";
                mRenderer.setActiveIconNum(0);
                requestRender();
            }else if(x<400) {
                mode = "rotate";
                mRenderer.setActiveIconNum(1);
                requestRender();
            }
                else    if(x<600) {
                mode = "edit";
                mRenderer.setActiveIconNum(2);
                requestRender();
            }
            else if(x<800)
            {

                mode = "draw";

                mRenderer.setActiveIconNum(0);
                mRenderer.clear();

                requestRender();


            }
            else if(x<1000)
            {


                emailData();

            }
        }
        else {
            if (mode.equals("draw")) {
                switch (e.getAction()) {
                    case MotionEvent.ACTION_DOWN:

                        if(mRenderer.pieceHermit.points.length == 2&&mRenderer.pieceHermit.points[0][0]==0&&mRenderer.pieceHermit.points[0][1]==0&&mRenderer.pieceHermit.points[0][2]==0) {
                            float[] startPoint = mRenderer.screenToWorldCoordinates(x, y);

                            mRenderer.pieceHermit.moveJoint(0, startPoint);
                            mRenderer.pieceHermit.moveJoint(1, startPoint);
                        }
                    case MotionEvent.ACTION_MOVE:

                        float dx = x - mPreviousX;
                        float dy = y - mPreviousY;

                        // reverse direction of rotation above the mid-line
                        if (y > getHeight() / 2) {
                            dx = dx * -1;
                        }

                        // reverse direction of rotation to left of the mid-line
                        if (x < getWidth() / 2) {
                            dy = dy * -1;
                        }

                        //mRenderer.setAngle(
                        //        mRenderer.getAngle() +
                        //                ((dx + dy) * TOUCH_SCALE_FACTOR));

                        // mRenderer.setAngle(mRenderer.getAngle()+1);
                        //uncomment the line below for some trippy sheeeeee

                        requestRender();
                        // if(dx*dy >50000) {
                        if ( mRenderer.getLastLength()> 0.5)  {
                            timeCounter = System.nanoTime();
                            mPreviousX = x;
                            mPreviousY = y;

                            mRenderer.addPoint(x, y);
                        } else {

                            mRenderer.setCurrentPoint(x, y);
                        }
                }
            }
            //System.out.println(y);

            if (mode.equals("edit")) {
                mRenderer.edit(x, y);
                requestRender();
            }
            if (mode.equals("rotate")) {

                float[] temp = this.screenToArcBallCoord(x,y);
                switch (e.getAction()) {
                    case MotionEvent.ACTION_DOWN:




                        //previousArcBallCoord = this.screenToArcBallCoord(x,y);
                        previousArcBallCoord[0] = temp[0];
                        previousArcBallCoord[1] = temp[1];
                        previousArcBallCoord[2] = temp[2];


                        //System.out.println("asdf"+Math.sqrt(tempx*tempx + tempy*tempy + temp[2]*temp[2] ));



                    case MotionEvent.ACTION_MOVE:

                        currentArcBallCoord[0] = temp[0];
                        currentArcBallCoord[1] = temp[1];
                        currentArcBallCoord[2] = temp[2];
                        // System.out.println(currentArcBallCoord[0] - previousArcBallCoord[0]);
                        float dotProduct = currentArcBallCoord[0]*previousArcBallCoord[0] + currentArcBallCoord[1]*previousArcBallCoord[1]+ currentArcBallCoord[2]*previousArcBallCoord[2];
                        double angle = Math.acos(dotProduct);
                        float[] rotationAxis = new float[3];
                        // System.out.println(rotationAxis.length+" "+currentArcBallCoord.length+" "+previousArcBallCoord.length);
                        rotationAxis[0] = currentArcBallCoord[1] * previousArcBallCoord[2] - previousArcBallCoord[1] * currentArcBallCoord[2];
                        rotationAxis[1] = currentArcBallCoord[2] * previousArcBallCoord[0] - previousArcBallCoord[2] * currentArcBallCoord[0];
                        rotationAxis[2] = currentArcBallCoord[0] * previousArcBallCoord[1] - previousArcBallCoord[0] * currentArcBallCoord[1];
                        System.out.println(rotationAxis[0]+ " "+rotationAxis[1]+ " "+rotationAxis[2]+ " " );
                        if(angle > 0.01) {
                            mRenderer.rotate((float) angle, rotationAxis[0], rotationAxis[1], rotationAxis[2]);

                            previousArcBallCoord =  currentArcBallCoord.clone();
                        }
                        requestRender();

                }
                //mRenderer.setAngle(mRenderer.getAngle() + 1);
                ;
            }

        }
        //System.out.println(x);
        return true;
    }
    private float[] screenToArcBallCoord(float x, float y )
    {
        float[] temp =  mRenderer.normalizedScreenToWorld(x,y);

        float length = (float)Math.sqrt( Math.pow(temp[0], 2) + Math.pow(temp[1], 2));

        //adjust coordinates to within the arcball
        if(length > 1f)
        {
            temp[0] = temp[0]/length ;
            temp[1] = temp[1]/length;
        }
        float tempx = temp[0];
        float tempy = temp[1];
        float zcoord = (float)Math.sqrt(1 - tempx*tempx  - tempy*tempy) ;
        temp[2] = zcoord;

        return temp;
    }
    public void emailData()
    {
        //verifyStoragePermissions(getContext());

        try {
            String fileName = "testFileName.txt";
            File root = new File(Environment.getExternalStorageDirectory(), "testDir");
            if (!root.exists()) {
                root.mkdirs();
            }
            File gpxfile = new File(root, fileName);
            FileWriter writer = new FileWriter(gpxfile);

            String message = "";
            for(int i = 0 ; i < mRenderer.pieceHermit.points.length;i++)
            {
             message=message  +  mRenderer.pieceHermit.points[i][0]+", "+mRenderer.pieceHermit.points[i][1]+", "+mRenderer.pieceHermit.points[i][2]+"#" ;
            }
            writer.append(message);
            writer.flush();
            writer.close();
            sendEmail(gpxfile);
        } catch (IOException p) {
            p.printStackTrace();
        }
    }

    protected void sendEmail(File file){
        Uri path = Uri.fromFile(file); // This guy gets the job done!

        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("message/rfc822");

        i.putExtra(Intent.EXTRA_SUBJECT, "Test subject");
        i.putExtra(Intent.EXTRA_TEXT, "This is the body of the email");
        i.putExtra(Intent.EXTRA_STREAM, path); // Include the path
        try {
            getContext().startActivity(Intent.createChooser(i, "Send mail..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(getContext(), "There are no email clients installed.", Toast.LENGTH_SHORT).show();
        }
    }


}