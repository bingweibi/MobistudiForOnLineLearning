package xyz.kfdykme.mobistudi.tool;

import android.graphics.PointF;

import java.util.ArrayList;
import java.util.List;

import xyz.kfdykme.mobistudi.structmap.view.Line;

/**
 * Project Name: Mobistudi2
 * Class Description:
 * @author kf
 * @date 2017/8/27 23:16
 * Last Edit on 2017/8/27 23:16
 * 修改备注：
 */

public class PointHelper {

    /**
     * 获取垂直于两点连线且过其中一点的直线上距离该点某距离的两个点
     */
    public static List<PointF> getTwoPoints(PointF fP,PointF sP,float dis){
        List<PointF> ps = new ArrayList<PointF>();
        Line l1 = getLine(fP,sP);
        Line l2 = getSLine(l1,fP);

        PointF mP = l2.getMidP();

        float k = l2.getK();

        float dx = getXByKND(dis,k);
        float dx2 = -1*dx;
        float dy = dx * k;
        float dy2 = -1*dy;
        ps.add(new PointF(fP.x+dx,fP.y+dy));
        ps.add(new PointF(fP.x+dx2,fP.y+dy2));
        return  ps;
    }

    public static Line getLine(PointF sP,PointF eP){
        return new Line(sP,eP);
    }

    public static Line getSLine(Line l,PointF mP){
        Line rl = new Line(l.getK() == Line.NULL?0:-1/l.getK());
        rl.setMidP(mP);
        return rl;
    }

    public static PointF getDPoint(PointF P1,PointF P2){
        return new PointF(P1.x-P2.x,P1.y-P2.y);
    }

    public static PointF getPointBet(PointF p1,PointF p2,float dis){
        float x ;
        float y;
        Line l = getLine(p1,p2);
        if(l.getK() == Line.NULL){
            x = p1.x;
            y = p2.y >p1.y?p2.y-dis:p2.y+dis;
        }else{
            float dx = getXByKND(dis,l.getK());
            float dy = dx * l.getK();

            if(p1.x < p2.x){
                x= p2.x-dx;
                y = p2.y-dy;
            }else{

                x= p2.x+dx;
                y = p2.y+dy;
            }
        }
        return new PointF(x,y);

    }

    public static float getXByKND(float d,float k){

            return (float) Math.sqrt(d *d/(1+(k*k)));
    }
}
