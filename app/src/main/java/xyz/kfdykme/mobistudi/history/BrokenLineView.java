package xyz.kfdykme.mobistudi.history;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import chinaykc.mobistudi.R;

/**
 * Project Name: Mobistudi2
 * Class Description:
 * @author kf
 * @date 2017/8/23 14:06
 * Last Edit on 2017/8/23 14:06
 * 修改备注：
 */

public class BrokenLineView extends ViewGroup {


    float x;

    float y;

    float startX;

    float startY;

    float deWidth;

    int baseColor;

    TextView mXtv;

    List<List<PointF>> data = new ArrayList<List<PointF>>();

    List<Integer> colors = new ArrayList<Integer>();

    public BrokenLineView(Context context) {
        this(context,null);
    }

    public BrokenLineView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView(){

        setWillNotDraw(false);
        baseColor = getContext().getResources().getColor(R.color.colorPrimary);

        mXtv = new TextView(getContext());

        addView(mXtv);

        deWidth = 50;

        startX = deWidth;

        setOnTouchListener(new OnTouchListener() {

            float x;


            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        x = event.getRawX();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        mXtv.setText(getDataFromFloat(startX-deWidth));
                        startX +=(event.getRawX()-x);
                        startX = startX >30 ? 30:startX;
                        x = event.getRawX();
                        break;
                        default:
                }

                return true;
            }
        });
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getMeasuredWidth();
        int height = getMeasuredHeight()-mXtv.getHeight();

        //test data
        if(data.size() == 0)
        for(int i =0;i< 20; i+=5){
            List<PointF> ps = new ArrayList<PointF>();
            int color = Color.rgb((int)(Math.random()*250),(int)(Math.random()*250),(int)(Math.random()*250));
            for(int j = i; j < i+ 7 ;j++){
                PointF p = new PointF((float)(j*200+Math.random()*200),(float)(Math.random()*height)+100);
                ps.add(p);
            }
            data.add(ps);
            colors.add(color);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int width = getMeasuredWidth();
        int height = getMeasuredHeight()-mXtv.getHeight();

        Paint paint = new Paint();
        paint.setColor(baseColor);
        paint.setStrokeWidth(3);
        paint.setAntiAlias(true);
        paint.setAlpha(70);

        canvas.save();

        Paint tPaint = new Paint();
        tPaint.setColor(paint.getColor());
        tPaint.setAntiAlias(true);
        tPaint.setTextSize(60);

        canvas.drawText("学习时间（小时）",0,110,tPaint);
        canvas.drawLine(deWidth,0,deWidth,height,paint);
        canvas.drawLine(0,height-deWidth,width,height-deWidth,paint);

        int v = 0;

        for(List<PointF> ps:data)
        {
            int mColor = colors.get(v++);
            paint.setColor(mColor);

            for(PointF p:ps){
                canvas.drawCircle(p.x+startX,height-deWidth-p.y,10,paint);
                canvas.drawLine(p.x+startX,height-deWidth,p.x+startX,height,paint);
                tPaint.setColor(mColor);
                tPaint.setAntiAlias(true);
                tPaint.setTextSize(50);
                if(p.x+startX < 10 * deWidth && p.x+startX>0){
                    canvas.drawText("  "+(int)(((p.y)/600) * 14),0,height-deWidth-p.y,tPaint);
                    Paint pa = new Paint(paint);
                    pa.setAlpha(10);
                    canvas.drawLine(p.x+startX,height-deWidth-p.y,0,height-deWidth-p.y,pa);
                }

            }

            for(int i =0 ;i < (ps.size()-1);i++){
                canvas.drawLine(ps.get(i).x+startX
                        ,height-deWidth-ps.get(i).y
                        ,ps.get(i+1).x +startX
                        ,height-deWidth-ps.get(i+1).y
                        ,paint);
            }
        }

        canvas.restore();


        super.onDraw(canvas);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        mXtv.layout(0,getMeasuredHeight()-60,400,getMeasuredHeight());
    }

    private String getDataFromFloat(Float time){
        int d = (int) (time /10);
        d *=-1;
        return "第"+d+"天";
    }

}
