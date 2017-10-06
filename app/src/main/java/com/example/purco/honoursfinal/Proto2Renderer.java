package com.example.purco.honoursfinal;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;

import com.example.purco.honoursfinal.RenderClasses.Cube;
import com.example.purco.honoursfinal.RenderClasses.Line;

import java.util.ArrayList;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Purco on 9/21/2016.
 package com.example.purco.drawingapp;


 import android.opengl.GLES20;
 import android.opengl.GLSurfaceView;
 import android.opengl.Matrix;
 import android.os.SystemClock;

 import java.util.ArrayList;

 import javax.microedition.khronos.egl.EGLConfig;
 import javax.microedition.khronos.opengles.GL10;

 /**
 * Created by Purco on 6/12/2016.
 */
public class Proto2Renderer implements GLSurfaceView.Renderer {

    private Cube mCube;
    private int width = 0;
    private int height = 0;
    ArrayList<Cube> joints = new ArrayList<Cube>();
    ArrayList<Cube> joints2 = new ArrayList<Cube>();
    private Line mLine;
    private final float[] mMVPMatrix = new float[16];
    private final float[] mProjectionMatrix = new float[16];
    private final float[] mViewMatrix = new float[16];
    private float[] mRotationMatrix = new float[16];
    private float[] mRotationMatrix1 = new float[16];
    private float[] mRotationMatrix2 = new float[16];
    float[] scratch = new float[16];
    float[] scratch2 = new float[16];
    float[] scratch3 = new float[16];
    PHCurve2 pieceHermit ;
    String mode;
    public void onSurfaceCreated(GL10 unused, EGLConfig config) {
        float lineCoords[] = {   // in counterclockwise order:

        };
        mode = "draw";
        // Set the background frame color
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);

        // initialize a triangle
        float triangleCoords[] = {   // in counterclockwise order:
                0.0f,  0.622008459f, 0.0f, // top
                0.0f, -0.311004243f, 0.0f, // bottom left
                0.5f, -0.311004243f, 0.0f  // bottom right
        };

        float[] es = {0.0f, 0.1f,  0.2f};
       // mTriangle = new Triangle(triangleCoords);
        // initialize a square
        //mSquare = new Square();
        // initialize a line
        mLine = new Line(lineCoords);
        float[] temp1 = {0.f,0.f,0.0f};
        mCube = new Cube(temp1);
       // hcurve[] hlist = new hcurve[2];
        for(int i=0; i < 100; i++)
        {
            joints.add(new Cube());
        }
        for(int i=0; i < 100; i++)
        {
            joints2.add(new Cube());
        }


        float[][] temp = new float[0][3];

        //float[] point1 = {0.0f,0.0f,0.0f};
        //   float[] point2 = {0.3f, 0.1f, 0.3f};
        //  temp[0] = point1;
        //  temp[1] = point2;
        float[] point1 = {0.0f,0.0f,0.0f};
        float[] point2 = {0.3f, 0.1f, 0.0f};
        float[] point3 = {0.1f, 0.1f, 0.1f};

        float[] point4 = {0.2f,0.1f,0.2f};
        float[] point5 = {0.3f, -0.1f, 0.3f};
        float[] point6 = {-0.1f, 0.1f, 0.1f};

        float[][] temp2 = new float[2][3];
        temp2[0] = point1;
        temp2[1] = point2;
        //temp2[2] = point3;
        //temp2[3] = point4;
        //temp2[4] = point5;
        //temp2[5] = point6;
        // temp2[6] = point1;

        pieceHermit = new PHCurve2(temp2);



        // pieceHermit = new pHcurve(temp);
    }

    public void onDrawFrame(GL10 unused) {

        //float[] scratch = new float[16];
        // Create a rotation transformation for the triangle
        // long time = SystemClock.uptimeMillis() % 4000L;
        // float angle = 0.090f * ((int) time);
       // Matrix.setRotateM(mRotationMatrix, 0, mAngle, 0, 1.0f, 0);


        // Redraw background color
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        Matrix.setLookAtM(mViewMatrix, 0, 0, 0, 4, 0f, 0f, 0f, 0f, 1.0f, 0.0f);

        // Calculate the projection and view transformation
        Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0);

        // Combine the rotation matrix with the projection and camera view
        // Note that the mMVPMatrix factor *must be first* in order
        // for the matrix multiplication product to be correct.
        Matrix.multiplyMM(scratch, 0, mMVPMatrix, 0, mRotationMatrix, 0);

        // mTriangle.draw(scratch);
        //mCube.draw(scratch);
        for(int i = 0 ; i<joints.size(); i++)
        {
            joints.get(i).draw(scratch);
        }

        for(int i = 0 ; i<joints2.size(); i++)
        {
            joints2.get(i).draw(scratch2);
        }

        mLine.draw(scratch, mRotationMatrix);
        Matrix.setRotateM(mRotationMatrix1, 0, 90, 1.0f, 0, 0);

        //Matrix.multiplyMM(scratch2,0,mRotationMatrix1,0,scratch,0);
        Matrix.multiplyMM(scratch2, 0, mMVPMatrix, 0, mRotationMatrix1, 0);
        //Matrix.translateM(scratch2, 0 , 0.0f,0.0f,0.3f);

        mLine.draw(scratch2, mRotationMatrix);

        //mSquare.draw(scratch);


    }

    public void onSurfaceChanged(GL10 unused, int width, int height) {
        GLES20.glViewport(0, 0, width, height);
        this.width = width;
        this.height = height;


        float ratio = (float) width / height;

        // this projection matrix is applied to object coordinates
        // in the onDrawFrame() method
        //Matrix.frustumM(mProjectionMatrix, 0, -ratio, ratio, -1, 1, 3, 7);
        Matrix.orthoM(mProjectionMatrix, 0, -ratio, ratio, -1, 1, 3, 7);

    }
    public static int loadShader(int type, String shaderCode){

        // create a vertex shader type (GLES20.GL_VERTEX_SHADER)
        // or a fragment shader type (GLES20.GL_FRAGMENT_SHADER)
        int shader = GLES20.glCreateShader(type);

        // add the source code to the shader and compile it
        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);

        return shader;
    }
    public volatile float mAngle;

    public float getAngle() {
        return mAngle;
    }

    public void setAngle(float angle) {
        mAngle = angle;
    }

    public float[] normalizedScreenToWorld(float x, float y)
    {
        float normalizedX = (x/width*width/height - width/(1.0f*height)*0.5f)*2*height/width;
        float normalizedY = (y/height-0.5f)*2;
        // System.out.println(normalizedX);

        float [] input = new float[4];
        input[0] = normalizedX;
        input[1] = -1*normalizedY;
        input[2] = 0;
        input[3] = 0;
        return input;
    }

    public float[] screenToWorldCoordinates(float x, float y)
    {

        float[] inverseMatrix = new float[16];
        // float[] temp = new float[16];

        //Matrix.multiplyMM(temp,0,mProjectionMatrix,0 , scratch ,0);

        Matrix.invertM(inverseMatrix,0,scratch,0 );
        //inverseMatrix = scratch;
        float []input = normalizedScreenToWorld(x, y);
        float[] output = new float[4];

        Matrix.multiplyMV(output, 0, inverseMatrix , 0 ,input,0 );
        return output;
    }
    private float[] screenToWorldCoordinates(float x, float y, float zWorldCoordinate)
    {

        float[] inverseMatrix = new float[16];
        // float[] temp = new float[16];

        //Matrix.multiplyMM(temp,0,mProjectionMatrix,0 , scratch ,0);

        Matrix.invertM(inverseMatrix,0,scratch2,0 );
        //inverseMatrix = scratch;
        float normalizedX = (x/width*width/height - width/(1.0f*height)*0.5f)*2*height/width;
        float normalizedY = (y/height-0.5f)*2;
        // System.out.println(normalizedX);
        float[] output = new float[4];
        float [] input = new float[4];
        input[0] = normalizedX;
        input[1] = -1*normalizedY;
        input[2] = zWorldCoordinate;
        input[3] = 0;

        Matrix.multiplyMV(output, 0, inverseMatrix , 0 ,input,0 );
        return output;
    }
    public void edit(float screenX,float screenY)
    {

        // System.out.println(output[2]);
        int closestIndex = 0;
        float[][] points =  pieceHermit.points;
        //float[] worldInput = screenToWorldCoordinates(screenX, screenY);
        float shortestDistance = 100;

        for(int i = 0 ; i < points.length ; i++ )
        {

            float[] currentPoint = new float[4];
            // worldInput = screenToWorldCoordinates(screenX, screenY,  );
            //float[] currentPoint =  points[i];
            currentPoint[0] = points[i][0];
            currentPoint[1] = points[i][1];
            currentPoint[2] = points[i][2];
            currentPoint[3] = 0;
            float transformedPoint[] = new float[4] ;
            Matrix.multiplyMV(transformedPoint, 0, scratch2 , 0 ,currentPoint,0 );
            // float[] oldClosestOutput = screenToWorldCoordinates(screenX, screenY, points[closestIndex][2]);
            //output[2] =  currentPoint[2];

            float[] normScreen = normalizedScreenToWorld(screenX,screenY);
            float x = normScreen[0];
            float y = normScreen[1];
            // float z = output[2];







            //float oldShortestDistance = (float)Math.sqrt(Math.pow((x-points[closestIndex][0]),2) + Math.pow((y-points[closestIndex][1]),2));
            float newDistance = (float)Math.sqrt(Math.pow((x-transformedPoint[0]),2) + Math.pow((y-transformedPoint[1]),2));
            // System.out.println(points[closestIndex][0]);
            //  System.out.println(oldShortestDistance);
            if(newDistance < shortestDistance)
            {
                closestIndex = i;
                shortestDistance = newDistance;
            }

            // output = screenToWorldCoordinates(screenX, screenY, points[closestIndex][2]);

        }
        float transformedPoint[] = new float[4] ;
        float inputPoint[] = new float[4];
        inputPoint[0] =  points[closestIndex][0];
        inputPoint[1] =  points[closestIndex][1];

        inputPoint[2] =  points[closestIndex][2];
        inputPoint[3] =  0;

        Matrix.multiplyMV(transformedPoint, 0, scratch2 , 0 ,inputPoint,0 );

        float[] output = screenToWorldCoordinates(screenX, screenY,transformedPoint[2]);

        pieceHermit.moveJoint(closestIndex , output );

        System.out.println(closestIndex+" distance "+shortestDistance+" "+output[2]);

        //points[closestIndex][0] = 100f;
        //points[closestIndex][1] = 100f;
        //points[closestIndex][2] = 100f;
        //System.out.println(closestIndex);
        mLine.setPoints(pieceHermit.getPoints(180));

        for(int i=0; i<joints.size();i++)
        {
            if(i<pieceHermit.points.length)
            {
                joints.get(i).setPosition(pieceHermit.points[i]);
            }
            else
            {
                float[] temppos = {1.5f,1.5f,1.5f};
                joints.get(i).setPosition(temppos);
            }
        }

        for(int i=0; i<joints2.size();i++)
        {
            if(i<pieceHermit.points.length)
            {
                joints2.get(i).setPosition(pieceHermit.points[i]);
            }
            else
            {
                float[] temppos = {1.5f,1.5f,1.5f};
                joints2.get(i).setPosition(temppos);
            }
        }
    }
    public void addPoint(float x, float y)
    {
        //float[] temp1 = {0.f,0.f,0.0f};
        float[] output = screenToWorldCoordinates(x, y);

        //mCube.setPosition(output);
        //      mCube = new Cube();
//        for(int i = 0 ; i < 16 ; ++i)
//        {
//            int row = i/4;
//            int collumn = i%4;
//            if(collumn == 0)
//            {
//                objectX = objectX + normalizedX*inverseMatrix[i];
//            }
//            if(collumn ==1 )
//            {
//                objectY = objectY + normalizedY*inverseMatrix[i];
//
//            }
//        }
        // mLine.addPoint(output[0] , -1*output[1] , output[2]);
//        float[] startPoint = {-0.1f,-0.1f,-0.1f};
//        float[] controlPoint = {0.0f, 0.2f, 0.2f};
//        float[] endPoint = {0.1f, 0.1f, 0.1f};
//
//        bezier2 testCurve = new bezier2(startPoint,controlPoint,endPoint);
//
//        float[] startPoint2 = {0.1f, 0.1f, 0.1f};
//        float[] controlPoint2 = {0.0f, 0.2f, 0.2f};
//        float[] endPoint2 = {0.1f, 0.1f, 0.1f};
//
//        bezier2 testCurve2 = new bezier2(startPoint2,controlPoint2,endPoint2);
//
//        float[] set =   testCurve.getPoints(40);
//
//        bezier2[] inputP = new bezier2[2];
//        inputP[0] = testCurve;
//        inputP[1] = testCurve2;
//        pBezier2 test = new pBezier2(inputP);
//
//
//
//
//
//        float[] tangent1 = {-1.0f, -2.0f, -1.0f};
//        float[] endPoint3 = {0.3f, 0.3f, 0.0f};
//        float[] tangent2 =  {1.0f, 1.0f, 1.0f};
//
//        float[] endPoint4 = {-0.3f, -0.1f, -0.3f};
//
//

//
//        double xDis = point1[0] - point3[0];
//        double yDis = point1[1] - point3[1];
//        double zDis = point1[2] - point3[2];
//
//        double distance = Math.sqrt(xDis*xDis + yDis*yDis + zDis*zDis );
//        //distance = 1;
//        float[] tangent = {0.0f,0.0f,0.0f};
//
//        for(int i = 0; i < 3; i++)
//        {
//            tangent[i] =  (point1[i] - point3[i])/(float)distance;
//        }
//
//        hcurve hermiteCurve = new hcurve(point1, tangent1, point2, tangent);
//        hcurve hermiteCurve2 = new hcurve( point2 , tangent,point3, tangent1 );

//        hcurve[] hlist = new hcurve[2];
//        hlist[0] = hermiteCurve;
//        hlist[1] = hermiteCurve2;
//        pHcurve pieceHermit = new pHcurve(hlist);
//

        pieceHermit.addPoint(output);
        System.out.println("uhuihui"+output[1]);
        mLine.setPoints(pieceHermit.getPoints(180));
        for(int i=0; i<joints.size();i++)
        {
            if(i<pieceHermit.points.length)
            {
                joints.get(i).setPosition(pieceHermit.points[i]);
            }
            else
            {
                float[] temppos = {1.5f,1.5f,1.5f};
                joints.get(i).setPosition(temppos);
            }
        }

        for(int i=0; i<joints2.size();i++)
        {
            if(i<pieceHermit.points.length)
            {
                joints2.get(i).setPosition(pieceHermit.points[i]);
            }
            else
            {
                float[] temppos = {1.5f,1.5f,1.5f};
                joints2.get(i).setPosition(temppos);
            }
        }


        //  System.out.println(output[0]);
    }
    public void setCurrentPoint(float x,float y)
    {
        float[] output = screenToWorldCoordinates(x, y);
        //  pieceHermit.setLastPoint(output[0], -1*output[1], output[2]);
        pieceHermit.moveJoint(pieceHermit.points.length -1 , output);
        mLine.setPoints(pieceHermit.getPoints(180));

    }


}