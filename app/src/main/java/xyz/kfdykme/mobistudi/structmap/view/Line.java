package xyz.kfdykme.mobistudi.structmap.view;

import android.graphics.PointF;

/**
 * Project Name: Mobistudi2
 * Class Description:
 * Created by kf on 2017/8/27 23:20.
 * Last Edit on 2017/8/27 23:20
 * 修改备注：
 */

public class Line {

    PointF startP;
    PointF endP;
    PointF midP;
    float k ;

    float dis;

    public static final float NULL = 9999;

    public Line(PointF startP, PointF endP) {
        this.startP = startP;
        this.endP = endP;
        midP = new PointF((startP.x+endP.x)/2,(startP.y+endP.y)/2);
        dis = (float) (Math.sqrt((startP.x-endP.x)*(startP.x-endP.x))+((startP.y-endP.y)*(startP.y-endP.y)));
        if(startP.x == endP.x){
            k = NULL;
        }else{
            k = (endP.y - startP.y)/(endP.x-startP.x);
        }
    }

    public Line(float k) {
        this.k = k;
    }

    public PointF getStartP() {
        return startP;
    }

    public void setStartP(PointF startP) {
        this.startP = startP;
    }

    public PointF getEndP() {
        return endP;
    }

    public void setEndP(PointF endP) {
        this.endP = endP;
    }

    public float getK() {
        return k;
    }

    public void setK(float k) {
        this.k = k;
    }

    public PointF getMidP() {
        return midP;
    }

    public void setMidP(PointF midP) {
        this.midP = midP;
    }

    public float getDis() {
        return dis;
    }

    public void setDis(float dis) {
        this.dis = dis;
    }
}
