package com.example.purco.honoursfinal;

/**
 * Created by Purco on 9/21/2016.
 */


        import java.util.ArrayList;

/**
 * Created by Purco on 8/17/2016.
 */
public class PHCurve2 {
    float[][] points;
    float[][] tangents;
    public PHCurve2()
    {
        this.points = new float[0][0];
        this.tangents= new float[points.length][3];

    }

    public PHCurve2( float[][] points )
    {
        this.points = points;

        this.tangents= new float[points.length][3];

        float[] defaultTangent = {0.0f, 0.0f,0.0f};

        if(points.length != 0 )
        {


            //since tangents are calculated using the point after and before them, the last and first points need default tangents
            tangents[0] = defaultTangent;
            tangents[tangents.length-1] = defaultTangent;

            for(int i = 1 ; i < points.length -1 ; i++)
            {
                tangents[i] = calcTangent(points[i-1],points[i],points[i+1]);

            }
        }
    }
    public void setPoints(float[][] points)
    {
        this.points = points;

        this.tangents= new float[points.length][3];

        float[] defaultTangent = {0.0f, 0.0f,0.0f};

        if(points.length != 0 )
        {


            //since tangents are calculated using the point after and before them, the last and first points need default tangents
            tangents[0] = defaultTangent;
            tangents[tangents.length-1] = defaultTangent;

            for(int i = 1 ; i < points.length -1 ; i++)
            {
                tangents[i] = calcTangent(points[i-1],points[i],points[i+1]);

            }
        }
    }
    private void updateTangents()
    {
        float[][] tempTan = new float[points.length+1][3];
        this.tangents = tempTan;


        float[] defaultTangent = {0.0f, 0.0f,0.0f};
        tangents[0] = defaultTangent;
        tangents[tangents.length-1] = defaultTangent;

        for(int i = 1 ; i < points.length -1 ; i++)
        {
            tangents[i] = calcTangent(points[i-1],points[i],points[i+1]);

        }

    }
    public void addPoint(float[] newPoint)
    {



        float[][] temp = new float[points.length+1][3];
        float[][] tempTan = new float[points.length+1][3];

        for(int i = 0 ; i < points.length; i++)
        {
            temp[i] = points[i];

        }
        temp[temp.length-1] = newPoint;
        //y is negative for some reason, must // FIXME: 8/31/2016
        // temp[temp.length-1][1]=newPoint[1]*-1;


        this.points = temp;
        //update and calculate tangents for the new points
        this.tangents = tempTan;


        float[] defaultTangent = {0.0f, 0.0f,0.0f};
        tangents[0] = defaultTangent;
        tangents[tangents.length-1] = defaultTangent;

        for(int i = 1 ; i < points.length -1 ; i++)
        {
            tangents[i] = calcTangent(points[i-1],points[i],points[i+1]);

        }
    }

    private float[] calcTangent(float[] previousPoint, float[] currentPoint, float[] nextPoint)
    {
        double xDis = currentPoint[0] - nextPoint[0];
        double yDis = currentPoint[1] - nextPoint[1];
        double zDis = currentPoint[2] - nextPoint[2];

        double xDis1 = currentPoint[0] - nextPoint[0];
        double yDis1 = currentPoint[1] - nextPoint[1];
        double zDis1 = currentPoint[2] - nextPoint[2];

       double distance2 = (3.0)*Math.max(Math.sqrt(xDis*xDis + yDis*yDis + zDis*zDis ), Math.sqrt(xDis1*xDis1 + yDis1*yDis1 + zDis1*zDis1 ));

        double distance = (3.0)*Math.sqrt(xDis*xDis + yDis*yDis + zDis*zDis );
        //distance = 1;
        float[] tangent = {0.0f,0.0f,0.0f};

        for(int j = 0; j < 3; j++)
        {
            tangent[j] =  (nextPoint[j]-previousPoint[j] )/(float)distance;
        }
        for(int j = 0; j < 3; j++)
        {
            tangent[j] =   tangent[j] *(float)distance2*0.5f;
        }
        return tangent;

    }
    public float[] getPoints(int segmentNum )
    {
        float[][] points = new float[segmentNum+1][3];
        for(int i = 0; i < segmentNum+1 ; i++ )
        {
            float t = (float)i/(segmentNum);
            // System.out.println(t);
            points[i] = getPointAtT(t);
            //System.out.println();
        }


        float[][] temp =   points;
        float[] set = new float[temp.length*3];
        for(int i = 0; i < temp.length*3; i++)
        {
            set[i] = temp[i/3][i%3];
            //   System.out.println(set[i]);
        }
        return set;
    }
    public void setPoint(int index , float[] output)
    {
        points[index] = output;


    }

    public float[] getPointAtT(float t)
    {

        int numOfBezier = points.length-1;
        int bezierIndex = (int)((numOfBezier)*t);

        //this is to say that if t = 1 then the segment index is the last segment not the one after last
        if(bezierIndex == numOfBezier)
        {
            bezierIndex =  numOfBezier - 1;

        }
        float pointInBezier = t*numOfBezier - bezierIndex ;
        //System.out.println("length: "+points.length);
        //System.out.println(bezierIndex);
        //return piecewiseHCurve[bezierIndex].getPointAtT(pointInBezier);
        return getPointAtTBezier(pointInBezier, points[bezierIndex], tangents[bezierIndex], points[bezierIndex+1], tangents[bezierIndex+1] );
    }
    public void moveJoint(int jointIndex , float[] coord)
    {
        points[jointIndex] = coord;
        updateTangents();
    }


    public float[] getPointAtTBezier(float t, float[] startPoint, float[] startTangent, float[] endPoint, float[] endTangent) {
        float t2 = t * t;
        float t3 = t * t * t;

        float[] outputVec3 = new float[3];
        for(int i = 0; i < 3; i++)
        {

            outputVec3[i] = (2*t3 - 3*t2 + 1)*startPoint[i] + (t3 -2*t2 + t)*startTangent[i] + (-2*t3 + 3*t2)*endPoint[i] + (t3 - t2)*endTangent[i];

            //  System.out.println(outputVec3[i] +" "+t);



        }
        return outputVec3;
    }





}
