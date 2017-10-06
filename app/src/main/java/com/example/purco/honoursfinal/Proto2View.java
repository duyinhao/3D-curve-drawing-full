package com.example.purco.honoursfinal;
import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;


class Proto2View extends GLSurfaceView {
    long timeCounter;
    private final Proto2Renderer mRenderer;
    String mode;
    public Proto2View(Context context){
        super(context);
        timeCounter = 0;
        // Create an OpenGL ES 2.0 context
        setEGLContextClientVersion(2);

        mRenderer = new Proto2Renderer();
        mode = "draw";
        // Set the Renderer for drawing on the GLSurfaceView
        setRenderer(mRenderer);
        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
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
        if(y<100)
        {
            if(x<100)
                mode = "edit";
            else if(x<200)
                mode = "rotate";
            else
                mode = "draw";

        }
        else {
            if (mode.equals("draw")) {
                switch (e.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        if(mRenderer.pieceHermit.points.length == 2&&mRenderer.pieceHermit.points[0][0]==0&&mRenderer.pieceHermit.points[0][1]==0&&mRenderer.pieceHermit.points[0][2]==0)
                        {
                            float[] startPoint = mRenderer.screenToWorldCoordinates(x,y);

                            mRenderer.pieceHermit.moveJoint(0,startPoint);
                            mRenderer.pieceHermit.moveJoint(1,startPoint);
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
                        if (0.5f * (System.nanoTime() - timeCounter) > 2000000000) {
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
                mRenderer.setAngle(mRenderer.getAngle() + 1);
                requestRender();
            }

        }
        //System.out.println(x);
        return true;
    }
}