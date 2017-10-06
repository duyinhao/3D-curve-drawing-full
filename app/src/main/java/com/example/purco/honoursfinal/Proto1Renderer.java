package com.example.purco.honoursfinal;

/**
 * Created by Purco on 9/21/2016.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.GLUtils;
import android.opengl.Matrix;

import java.util.ArrayList;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;



import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.os.SystemClock;

import com.example.purco.honoursfinal.RenderClasses.Cube;
import com.example.purco.honoursfinal.RenderClasses.Line;
import com.example.purco.honoursfinal.RenderClasses.LinePreview;
import com.example.purco.honoursfinal.RenderClasses.RecTexture;
import com.example.purco.honoursfinal.RenderClasses.Square;
import com.example.purco.honoursfinal.RenderClasses.UniformColLine;

import java.util.ArrayList;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Purco on 6/12/2016.
 */
public class Proto1Renderer implements GLSurfaceView.Renderer {
    //private Triangle mTriangle;
    //private Square   mSquare;
    private Cube mCube;
    private int width = 0;
    private int height = 0;
    ArrayList<Cube> joints = new ArrayList<Cube>();
    private UniformColLine mLine;
    private LinePreview mLinePreview;
    private final float[] mMVPMatrix = new float[16];
    private final float[] mProjectionMatrix = new float[16];
    private final float[] mViewMatrix = new float[16];
    private float[] mRotationMatrix = new float[16];
    float[] scratch = new float[16];
    PHCurve2 pieceHermit ;
    String mode;
    RecTexture mSquare;
    RecTexture mSquare1;
    RecTexture mSquare2;
    RecTexture mSquare3;
    RecTexture mSquare4;

    RecTexture mSquareU;
    RecTexture mSquareU1;
    RecTexture mSquareU2;
    RecTexture mSquareU3;
    Context context;
    public volatile float activeIconNum;
    public Proto1Renderer(Context context)
    {
        this.context = context;
        activeIconNum=0;

    }
    public void setActiveIconNum(int num)
    {
        this.activeIconNum = num;
    }
    public void onSurfaceCreated(GL10 unused, EGLConfig config) {
        float lineCoords[] = {   // in counterclockwise order:

        };
        mode = "draw";
        // Set the background frame color

        // initialize a triangle
        mAngle=0;

        float[] es = {0.0f, 0.1f,  0.2f};
       // mTriangle = new Triangle(triangleCoords);
        // initialize a square
       // mSquare = new Square();
        // initialize a line
        mLine = new UniformColLine(lineCoords,  loadTexture(context,  R.drawable.colmap1));
        mLinePreview = new LinePreview(lineCoords);

        float[] temp1 = {0.f,0.f,0.0f};
        mCube = new Cube(temp1);
        //hcurve[] hlist = new hcurve[2];
        for(int i=0; i < 100; i++)
        {
            joints.add(new Cube());
        }


        float[][] temp = new float[0][3];

        //float[] point1 = {0.0f,0.0f,0.0f};
        //   float[] point2 = {0.3f, 0.1f, 0.3f};
        //  temp[0] = point1;
        //  temp[1] = point2;
        float[] point1 = {0.0f,0.0f,0.0f};
        float[] point2 = {0.0f, 0.0f, 0.0f};
        float[] point3 = {0.1f, 0.1f, 0.1f};

        float[] point4 = {0.2f,0.1f,0.2f};
        float[] point5 = {0.3f, -0.1f, 0.3f};
        float[] point6 = {-0.1f, 0.1f, 0.1f};

        float[][] temp2 = new float[2][3];
        temp2[0] = point1;
        temp2[1] = point2;
        //temp2[3] = point4;
        //temp2[2] = point3;
        //temp2[4] = point5;
        //temp2[5] = point6;
        // temp2[6] = point1;

        pieceHermit = new PHCurve2(temp2);
        //pieceHermit = new PHCurve2();


        // pieceHermit = new pHcurve(temp);
    }

    public void onDrawFrame(GL10 unused) {

        //float[] scratch = new float[16];
        // Create a rotation transformation for the triangle
        // long time = SystemClock.uptimeMillis() % 4000L;
        // float angle = 0.090f * ((int) time);
       // Matrix.setRotateM(mRotationMatrix, 0, mAngle, 0, 1.0f, 0);


        // Redraw background color
        GLES20.glClearColor(1,1,1,1);

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
       // for(int i = 0 ; i<joints.size(); i++)
       // {
      //      joints.get(i).draw(scratch);
       // }

        mLine.draw(scratch, mRotationMatrix);


        float[] mRotationMatrixChange = new float[16];
        Matrix.setRotateM(mRotationMatrixChange, 0, 90, 0, 1, 0);

        float[] mRotationMatrixPreview = new float[mRotationMatrix.length];
         //mRotationMatrixPreview = mRotationMatrix.clone();
        //System.out.println("rot"+mRotationMatrix[0]);
      //  if(magnitutde > 0.01f) {
        //    System.out.println("change" + mRotationMatrixChange[0]);
        Matrix.multiplyMM(mRotationMatrixPreview, 0,mRotationMatrixChange , 0,mRotationMatrix , 0);
        float[] scratch1 = new float[16];
        Matrix.multiplyMM(scratch1, 0, mMVPMatrix, 0, mRotationMatrixPreview, 0);

        mLinePreview.draw(scratch1, mRotationMatrixPreview);
        float[] color2 = {0.949f, 0.027f, 0.133f};
        float[] color3 = {0.035f, 0.949f, 0.027f};



         mSquareU1.draw(mMVPMatrix, color2);
        mSquareU2.draw(mMVPMatrix, color2);

        if(activeIconNum==0)
            mSquare.draw(mMVPMatrix, color3);
        else
            mSquareU.draw(mMVPMatrix, color2);

        if(activeIconNum==1)
            mSquare1.draw(mMVPMatrix, color3);
        else
            mSquareU1.draw(mMVPMatrix, color2);

        if(activeIconNum==2)
            mSquare2.draw(mMVPMatrix, color3);
        else
            mSquareU2.draw(mMVPMatrix, color2);
        System.out.println(activeIconNum);

        if(activeIconNum==3)
            mSquare3.draw(mMVPMatrix, color3);
        else
            mSquareU3.draw(mMVPMatrix, color2);
        System.out.println(activeIconNum);

        //mSquare.draw(scratch);
        mSquare4.draw(mMVPMatrix, color3);

    }


    public void onSurfaceChanged(GL10 unused, int width, int height) {
        GLES20.glViewport(0, 0, width, height);
        this.width = width;
        this.height = height;
        GLES20.glLineWidth(50);

        float ratio = (float) width / height;

        // this projection matrix is applied to object coordinates
        // in the onDrawFrame() method
        //Matrix.frustumM(mProjectionMatrix, 0, -ratio, ratio, -1, 1, 3, 7);
        Matrix.orthoM(mProjectionMatrix, 0, -ratio, ratio, -1, 1, 3, 7);

        Matrix.setRotateM(mRotationMatrix, 0, mAngle, 0, 1.0f, 0);


        // Redraw background color
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        Matrix.setLookAtM(mViewMatrix, 0, 0, 0, 4, 0f, 0f, 0f, 0f, 1.0f, 0.0f);

        // Calculate the projection and view transformation
        Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0);

        // Combine the rotation matrix with the projection and camera view
        // Note that the mMVPMatrix factor *must be first* in order
        // for the matrix multiplication product to be correct.
        Matrix.multiplyMM(scratch, 0, mMVPMatrix, 0, mRotationMatrix, 0);

        float[] topLeft = this.screenToWorldCoordinates(0,0);
        float[] bottomRight = this.screenToWorldCoordinates(200,200);

        float[] topLeft1 = this.screenToWorldCoordinates(200,0);
        float[] bottomRight1 = this.screenToWorldCoordinates(400,200);

        float[] topLeft2 = this.screenToWorldCoordinates(400,0);
        float[] bottomRight2 = this.screenToWorldCoordinates(600,200);

        float[] topLeft3 = this.screenToWorldCoordinates(600,0);
        float[] bottomRight3 = this.screenToWorldCoordinates(800,200);

        float[] topLeft4 = this.screenToWorldCoordinates(800,0);
        float[] bottomRight4 = this.screenToWorldCoordinates(1000,200);
        float squareCoords[] = {
                topLeft[0],  topLeft[1], 0.5f,   // top left
                topLeft[0], bottomRight[1], 0.5f,   // bottom left
                bottomRight[0], bottomRight[1], 0.5f,   // bottom right
                bottomRight[0],  topLeft[1], 0.5f }; // top right
        float squareCoords1[] = {
                topLeft1[0],  topLeft1[1], 0.5f,   // top left
                topLeft1[0], bottomRight1[1], 0.5f,   // bottom left
                bottomRight1[0], bottomRight1[1], 0.5f,   // bottom right
                bottomRight1[0],  topLeft1[1], 0.5f }; // top right
        float squareCoords2[] = {
                topLeft2[0],  topLeft2[1], 0.5f,   // top left
                topLeft2[0], bottomRight2[1], 0.5f,   // bottom left
                bottomRight2[0], bottomRight2[1], 0.5f,   // bottom right
                bottomRight2[0],  topLeft2[1], 0.5f }; // top right
        float squareCoords3[] = {
                topLeft3[0],  topLeft3[1], 0.5f,   // top left
                topLeft3[0], bottomRight3[1], 0.5f,   // bottom left
                bottomRight3[0], bottomRight3[1], 0.5f,   // bottom right
                bottomRight3[0],  topLeft3[1], 0.5f }; // top right
        float squareCoords4[] = {
                topLeft4[0],  topLeft4[1], 0.5f,   // top left
                topLeft4[0], bottomRight4[1], 0.5f,   // bottom left
                bottomRight4[0], bottomRight4[1], 0.5f,   // bottom right
                bottomRight4[0],  topLeft4[1], 0.5f }; // top right

        float[] color2 = {0.949f, 0.027f, 0.133f};
        float[] color3 = {0.035f, 0.949f, 0.027f};
        int mTextureDataHandle = loadTexture(context,  R.drawable.edit);
        int mTextureDataHandle1 = loadTexture(context, R.drawable.rotate);
        int mTextureDataHandle2 = loadTexture(context,  R.drawable.pushs);
        int mTextureDataHandle3 = loadTexture(context,  R.drawable.pulls);
        int mTextureDataHandle4 = loadTexture(context,  R.drawable.garbage);

        int mTextureDataHandleu = loadTexture(context,  R.drawable.edit1);
        int mTextureDataHandleu1 = loadTexture(context, R.drawable.rotate1);
        int mTextureDataHandleu2 = loadTexture(context,  R.drawable.push);
        int mTextureDataHandleu3 = loadTexture(context,  R.drawable.pull);
        System.out.println(mTextureDataHandle+" ppap "+mTextureDataHandle1+" "+mTextureDataHandle2);
        mSquare = new RecTexture(squareCoords,mTextureDataHandle);

        mSquare1 = new RecTexture(squareCoords1,mTextureDataHandle1);
        mSquare2 = new RecTexture(squareCoords2,mTextureDataHandle2);
        mSquare3 = new RecTexture(squareCoords3,mTextureDataHandle3);
        mSquare4 = new RecTexture(squareCoords4,mTextureDataHandle4);

        mSquareU = new RecTexture(squareCoords,mTextureDataHandleu);
        mSquareU1 = new RecTexture(squareCoords1,mTextureDataHandleu1);
        mSquareU2 = new RecTexture(squareCoords2,mTextureDataHandleu2);
        mSquareU3 = new RecTexture(squareCoords3,mTextureDataHandleu3);
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
    public void rotate(float dx, float dy)
    {
        float magnitutde = (float)Math.sqrt(Math.pow(dx,2)+Math.pow(dy,2))/5;
        float[] mRotationMatrixChange = new float[16];
        Matrix.setRotateM(mRotationMatrixChange, 0, magnitutde, dy, dx, 0);

        //System.out.println("rot"+mRotationMatrix[0]);
        if(magnitutde > 0.01f) {
            System.out.println("change" + mRotationMatrixChange[0]);
            Matrix.multiplyMM(mRotationMatrix, 0,mRotationMatrixChange , 0,mRotationMatrix , 0);

            System.out.println(mRotationMatrix[0]);
        }


    }

    public void rotate(float angle, float x, float y, float z)
    {
        float[] mRotationMatrixChange = new float[16];
        Matrix.setRotateM(mRotationMatrixChange, 0, angle*100, -1*x, -1*y, -1*z);
        System.out.print("angle"+angle);
        //System.out.println("rot"+mRotationMatrix[0]);
        if(angle > 0.01f) {
        Matrix.multiplyMM(mRotationMatrix, 0,mRotationMatrixChange , 0,mRotationMatrix , 0);

        }



    }
    //correct between -1 and 1
     float[] normalizedScreenToWorld(float x, float y)
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
    private float[] cameraToWorldCoordinates(float x, float y, float z)
    {
        float [] input = new float[4];
        input[0] = x;
        input[1] = y;
        input[2] = z;
        input[3]=0;


        float[] inverseMatrix = new float[16];
        // float[] temp = new float[16];

        //Matrix.multiplyMM(temp,0,mProjectionMatrix,0 , scratch ,0);

        Matrix.invertM(inverseMatrix,0,scratch,0 );
        //inverseMatrix = scratch;
        //float []input = normalizedScreenToWorld(x, y);
        float[] output = new float[4];

        Matrix.multiplyMV(output, 0, inverseMatrix , 0 ,input,0 );
        return output;
    }
    private float[] screenToWorldCoordinates(float x, float y, float zWorldCoordinate)
    {

        float[] inverseMatrix = new float[16];
        // float[] temp = new float[16];

        //Matrix.multiplyMM(temp,0,mProjectionMatrix,0 , scratch ,0);

        Matrix.invertM(inverseMatrix,0,scratch,0 );
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
        float[] worldInput = screenToWorldCoordinates(screenX, screenY);
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
            Matrix.multiplyMV(transformedPoint, 0, scratch , 0 ,currentPoint,0 );
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



            transformedPoint[2] = transformedPoint[2] + 0.01f/(float)Math.pow(newDistance*10+1, 2);

            float[] output = cameraToWorldCoordinates(transformedPoint[0], transformedPoint[1], transformedPoint[2]);

            pieceHermit.moveJoint(i , output );

        }


       // System.out.println(closestIndex+" distance "+shortestDistance+" "+output[2]);

        mLine.setPoints(pieceHermit.getPoints(180*pieceHermit.points.length));
        mLinePreview.setPoints(previewTransformation(pieceHermit.getPoints(180*pieceHermit.points.length)));

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
        //System.out.println("uhuihui"+output[1]);
        mLine.setPoints(pieceHermit.getPoints(180*pieceHermit.points.length));
        mLinePreview.setPoints(previewTransformation(pieceHermit.getPoints(180*pieceHermit.points.length)));

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


        //  System.out.println(output[0]);
    }
    private float[] previewTransformation(float[] input)
    {
        float[] output = new float[input.length];
        for(int i = 0 ; i < input.length; i++)
        {


                output[i] = input[i]*0.25f;



        }
        return output;
    }
    public void edit2(float screenX,float screenY)
    {

        // System.out.println(output[2]);
        int closestIndex = 0;
        float[][] points =  pieceHermit.points;
        float[] worldInput = screenToWorldCoordinates(screenX, screenY);
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
            Matrix.multiplyMV(transformedPoint, 0, scratch , 0 ,currentPoint,0 );
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



            transformedPoint[2] = transformedPoint[2] - 0.01f/(float)Math.pow(newDistance*10+1, 2);

            float[] output = cameraToWorldCoordinates(transformedPoint[0], transformedPoint[1], transformedPoint[2]);
            pieceHermit.moveJoint(i , output );

        }


        // System.out.println(closestIndex+" distance "+shortestDistance+" "+output[2]);

        mLine.setPoints(pieceHermit.getPoints(180*pieceHermit.points.length));
        mLinePreview.setPoints(previewTransformation(pieceHermit.getPoints(180*pieceHermit.points.length)));



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
    }

    public void setCurrentPoint(float x,float y)
    {
        float[] output = screenToWorldCoordinates(x, y);
        //  pieceHermit.setLastPoint(output[0], -1*output[1], output[2]);
        pieceHermit.moveJoint(pieceHermit.points.length -1 , output);
        mLine.setPoints(pieceHermit.getPoints(180*pieceHermit.points.length));
        mLinePreview.setPoints(previewTransformation(pieceHermit.getPoints(180*pieceHermit.points.length)));


    }
    public void clear()
    {
        float[] point1 = {0.0f,0.0f,0.0f};
        float[] point2 = {0.0f, 0.0f, 0.0f};


        float[][] temp2 = new float[2][3];
        temp2[0] = point1;
        temp2[1] = point2;

        //temp2[3] = point4;
        //temp2[2] = point3;
        //temp2[4] = point5;
        //temp2[5] = point6;
        // temp2[6] = point1;

        this.pieceHermit.setPoints(temp2);

        System.out.println("dsfsdf");
        mLine.setPoints(pieceHermit.getPoints(180*pieceHermit.points.length));
        mLinePreview.setPoints(previewTransformation(pieceHermit.getPoints(180*pieceHermit.points.length)));


    }
    public float getLastLength()
    {
        if(pieceHermit.points.length>=2) {
            float lastPoint[] = pieceHermit.points[pieceHermit.points.length - 1];
            float secondLastPoint[] = pieceHermit.points[pieceHermit.points.length - 2];

            double distance = Math.sqrt(Math.pow(lastPoint[0] - secondLastPoint[0], 2) + Math.pow(lastPoint[1] - secondLastPoint[1], 2) + Math.pow(lastPoint[2] - secondLastPoint[2], 2));
            return (float) distance;
        }
        return 0;
    }
    public static int loadTexture(final Context context, final int resourceId)
    {
        final int[] textureHandle = new int[1];

        GLES20.glGenTextures(1, textureHandle, 0);

        if (textureHandle[0] != 0)
        {
            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inScaled = false;   // No pre-scaling

            // Read in the resource
            final Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resourceId, options);

            // Bind to the texture in OpenGL
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureHandle[0]);

            // Set filtering
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_NEAREST);
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_NEAREST);

            // Load the bitmap into the bound texture.
            GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0);

            // Recycle the bitmap, since its data has been loaded into OpenGL.
            bitmap.recycle();
        }

        if (textureHandle[0] == 0)
        {
            throw new RuntimeException("Error loading texture.");
        }

        return textureHandle[0];
    }



}