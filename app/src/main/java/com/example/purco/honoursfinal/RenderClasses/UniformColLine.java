package com.example.purco.honoursfinal.RenderClasses;

/**
 * Created by Purco on 9/21/2016.
 */

import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;



/**
 * Created by Purco on 6/18/2016.
 */


import com.example.purco.honoursfinal.Proto1Renderer;

/**
 * Created by Purco on 6/12/2016.
 */
public class UniformColLine {
    private final int mProgram;
    private int mTextureUniformHandle;
    private int mTextureDataHandle;
    private final String vertexShaderCode =
            "uniform mat4 uMVPMatrix;" +
                    "uniform mat4 uRotationMatrix;" +
                    "attribute vec4 vPosition;" +
                    "varying vec4 v_Position;"+

                    "void main() {" +
                    "  gl_Position =  uMVPMatrix * vPosition;" +
                    "  v_Position =  uRotationMatrix * vPosition;" +
                    "}";
    private int mMVPMatrixHandle;

    private final String fragmentShaderCode =
            "precision mediump float;" +
                    "uniform vec4 vColor;" +
                    "varying vec4 v_Position;"+
                    "uniform sampler2D u_Texture;"+

                    "void main() {" +
                    //  "vColor.xyzw = vec4(1.0, 2.0, 3.0, 4.0);"+
                    //     "vColor.x = vColor.x;"+
                    "vec4 temp =  vColor*(v_Position.z+1.0);"+
                    "vec4 colChange = vec4(v_Position[3] ,0.0 ,0.0 ,0.0);"+
                   // "  gl_FragColor = temp + colChange ;" +
                    "vec2 v_TexCoordinate = vec2( v_Position[2]*0.5 +0.5 ,0.0 );"+
                    "  gl_FragColor = texture2D(u_Texture, v_TexCoordinate);"+
                    "}";


    private FloatBuffer vertexBuffer;

    // number of coordinates per vertex in this array
    static final int COORDS_PER_VERTEX = 3;
    float lineCoords[] ;
    // Set color with red, green, blue and alpha (opacity) values
    float color[] = { 0.63671875f, 0.76953125f, 0.22265625f, 1.0f };

    public UniformColLine(float lineCoordsInput[],int mTextureDataHandle) {
        this.lineCoords= lineCoordsInput;

        loadVertexBuffer();
        this.mTextureDataHandle = mTextureDataHandle;
        int vertexShader = Proto1Renderer.loadShader(GLES20.GL_VERTEX_SHADER,
                vertexShaderCode);
        int fragmentShader = Proto1Renderer.loadShader(GLES20.GL_FRAGMENT_SHADER,
                fragmentShaderCode);

        // create empty OpenGL ES Program
        mProgram = GLES20.glCreateProgram();

        // add the vertex shader to program
        GLES20.glAttachShader(mProgram, vertexShader);

        // add the fragment shader to program
        GLES20.glAttachShader(mProgram, fragmentShader);

        // creates OpenGL ES program executables
        GLES20.glLinkProgram(mProgram);
    }
    private int mPositionHandle;
    private int mColorHandle;
    private int mRotationMatrixHandle;


    private int vertexCount ;
    private final int vertexStride = COORDS_PER_VERTEX * 4; // 4 bytes per vertex
    private void loadVertexBuffer()
    {
        // initialize vertex byte buffer for shape coordinates
        ByteBuffer bb = ByteBuffer.allocateDirect(
                // (number of coordinate values * 4 bytes per float)
                lineCoords.length * 4);
        // use the device hardware's native byte order
        bb.order(ByteOrder.nativeOrder());
        vertexCount = lineCoords.length / COORDS_PER_VERTEX;
        // create a floating point buffer from the ByteBuffer
        vertexBuffer = bb.asFloatBuffer();
        // add the coordinates to the FloatBuffer
        vertexBuffer.put(lineCoords);
        // set the buffer to read the first coordinate
        vertexBuffer.position(0);
    }
    public void draw(float[] mvpMatrix, float[] rotationMatrix) {
        // Add program to OpenGL ES environment



        GLES20.glUseProgram(mProgram);



        // get handle to vertex shader's vPosition member
        mPositionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition");

        // get handle to shape's transformation matrix
        mMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");

        mRotationMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uRotationMatrix");

        mTextureUniformHandle = GLES20.glGetUniformLocation(mProgram, "u_Texture");

        // Set the active texture unit to texture unit 0.
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);

        // Bind the texture to this unit.
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, mTextureDataHandle);

        // Tell the texture uniform sampler to use this texture in the shader by binding to texture unit 0.
        GLES20.glUniform1i(mTextureUniformHandle, 0);


        GLES20.glUniformMatrix4fv(mRotationMatrixHandle, 1, false, rotationMatrix, 0);
        //System.out.println(rotationMatrix[0]);
        // Pass the projection and view transformation to the shader
        GLES20.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, mvpMatrix, 0);

        // Enable a handle to the triangle vertices
        GLES20.glEnableVertexAttribArray(mPositionHandle);

        // Prepare the triangle coordinate data
        GLES20.glVertexAttribPointer(mPositionHandle, COORDS_PER_VERTEX,
                GLES20.GL_FLOAT, false,
                vertexStride, vertexBuffer);

        // get handle to fragment shader's vColor member
        mColorHandle = GLES20.glGetUniformLocation(mProgram, "vColor");

        // Set color for drawing the triangle
        GLES20.glUniform4fv(mColorHandle, 1, color, 0);

        // Draw the triangle
        GLES20.glDrawArrays(GLES20.GL_LINE_STRIP, 0, vertexCount);

        // Disable vertex array
        GLES20.glDisableVertexAttribArray(mPositionHandle);

        GLES20.glActiveTexture( GLES20.GL_TEXTURE0);
        GLES20.glBindTexture( GLES20.GL_TEXTURE_2D, 0);


    }

    public void addPoint(float x, float y, float z)
    {
        float[] newLineCoords = new float[lineCoords.length+3]  ;
        newLineCoords[lineCoords.length] = x;
        newLineCoords[lineCoords.length+1] = y;
        newLineCoords[lineCoords.length+2] = z;
        for(int i = 0; i < lineCoords.length  ; i++ )
        {
            newLineCoords[i] = lineCoords[i];
        }

        this.lineCoords = newLineCoords;
        loadVertexBuffer();
    }
    public void setPoints(float[] lineCoords)
    {


        this.lineCoords = lineCoords;


        loadVertexBuffer();
    }
}