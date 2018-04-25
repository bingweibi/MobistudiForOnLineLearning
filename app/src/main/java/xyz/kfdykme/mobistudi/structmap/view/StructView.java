package xyz.kfdykme.mobistudi.structmap.view;

import android.annotation.SuppressLint;
import android.content.*;
import android.graphics.*;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.*;
import android.view.animation.*;
import android.widget.*;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.*;

import xyz.kfdykme.mobistudi.common.MobiActivity;
import xyz.kfdykme.mobistudi.structmap.StructMapConstant;
import xyz.kfdykme.mobistudi.structmap.model.KfMapData;
import xyz.kfdykme.mobistudi.structmap.presenter.StructMapEventListener;
import xyz.kfdykme.mobistudi.tool.FileHelper;
import xyz.kfdykme.mobistudi.tool.PointHelper;

/**
 * @author cxf
 */
public class StructView extends ViewGroup {

	private final String TAG = getClass().toString();
	private final String position = "[{\"x\":899,\"y\":1628},{\"x\":291,\"y\":1627},{\"x\":669,\"y\":1300},{\"x\":948,\"y\":59},{\"x\":650,\"y\":802},{\"x\":697,\"y\":349},{\"x\":235,\"y\":323},{\"x\":179,\"y\":778},{\"x\":217,\"y\":1193}]";

	int colorBackGrpund,colorLine,colorNode,colorText,colorStartNode,colorArc;

	List<KfMapData<String>> data;
	List<KfMapNodeView> dispatchedViews = new ArrayList<KfMapNodeView>();
	boolean isLoaded = false;
	boolean show = false;
	Paint mPaint;
	
	int baseX = 0,baseY = 0;

	enum DrawStatus{empty,draw}
	private DrawStatus drawStatus = DrawStatus.empty;
	OnItemViewClickListener mOnItemViewClickListener;
	String subjectName;
	
	public StructView(Context context){
		this(context,null);
	}

	public StructView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public StructView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	private boolean checkDistance(KfMapNodeView view, boolean min, boolean max, int minDistance, int maxDistance){
		int dis2,dX,dY;

		for(KfMapNodeView targetView:dispatchedViews){
			dX = (int) Math.abs(targetView.getRCenter().x - view.getRCenter().x);
			dY = (int) Math.abs(targetView.getRCenter().y - view.getRCenter().y);
			dis2 = (dX * dX) + (dY * dY);
			dis2 = (int) Math.sqrt(dis2);

			if((dis2 <minDistance && min ) && (targetView != view)) {
				int minF = minDistance/20;
				float x = targetView.getRCenter().x+(targetView.getRCenter().x - view.getRCenter().x)/minF;
				float y = targetView.getRCenter().y+(targetView.getRCenter().y - view.getRCenter().y)/minF;

				targetView.setRCenter(new PointF(x,y));
				layout(targetView);
				checkDistance(targetView,min,max,minDistance,maxDistance);
				return true;
			}
			if(dis2 >= maxDistance && max) {
				int maxF = maxDistance / 10;

				float x = targetView.getRCenter().x - (targetView.getRCenter().x - view.getRCenter().x) / maxF;
				float y = targetView.getRCenter().y - (targetView.getRCenter().y - view.getRCenter().y) / maxF;
				targetView.setRCenter(new PointF(x, y));
				layout(targetView);
				targetView.setState(KfMapNodeView.TOUCH_STATUS_ON_TOUCH_MOVING);
				checkDistance(targetView, min, max, 450, 2000);
			}
		}
		return false;
	}

	@Override
	protected void dispatchDraw(Canvas canvas) {
		switch(drawStatus){
			case draw:
				mPaint = new Paint();
				mPaint.setColor(colorLine);
				mPaint.setAntiAlias(true);
				mPaint.setStrokeWidth(20);
				mPaint.setAlpha(140);

				//spread vies on load
				if(!isLoaded && getChildCount()!=0 ) {
					startFirstView(data.get(0).getView(),canvas);
				}

				//link views by nex
				for(int i = 0; i < getChildCount();i++){
					if(getChildAt(i).getVisibility() == View.VISIBLE){
						KfMapNodeView view = (StructView.KfMapNodeView) getChildAt(i);
						int pos = data.size()-1;
						for(;pos>-1;pos--){
							if(data.get(pos).getData().equals(view.getText().toString()))
								break;
						}
						if(pos == -1){
							return ;
						}
						KfMapData<String> d = data.get(pos);
						for(int j = 0; j < d.getNex().size();j++){
							KfMapNodeView view2 = findNodeViewByString(d.getNex().get(j).getData());
							if(view2.isOnCurrectPosition) {
								drawLine(canvas,view,view2,mPaint);
							}
						}
					}
				}
				break;
				
			case empty:
				break;
				default:
		}

		super.dispatchDraw(canvas);
	}

	public void drawLine(Canvas canvas,KfMapNodeView v1,KfMapNodeView v2,Paint paint){

		PointF p1 = v1.getRCenter();
		PointF p2 = v2.getRCenter();
		float k2 = (float)v2.getMeasuredHeight()/v2.getMeasuredWidth();
		Line l = new Line(p2,p1);
		float dis = (float) (Math.abs(l.getK()) > k2 ?v2.getMeasuredHeight()*0.6:v2.getMeasuredWidth()*0.6);

		PointF lineEndP = PointHelper.getPointBet(p1,p2,dis);
		PointF arcEndP = PointHelper.getPointBet(p1,p2, (float) (dis*0.7));
		canvas.drawLine(p1.x+baseX
						,p1.y+baseY
						,lineEndP.x+baseX
						,lineEndP.y+baseY,paint);

		float h = 80,w = 50;

		PointF sP = PointHelper.getPointBet(p1,lineEndP,h);
		List<PointF> ps = PointHelper.getTwoPoints(sP,p2,w);

		Paint arcPaint = new Paint();
		arcPaint.setAntiAlias(true);
		arcPaint.setColor(colorArc);
		arcPaint.setStrokeWidth(40);
		Path arc = new Path();
		arc.moveTo(arcEndP.x+baseX,arcEndP.y+baseY);
		arc.lineTo(ps.get(0).x+baseX,ps.get(0).y+baseY);
		arc.lineTo(ps.get(1).x+baseX,ps.get(1).y+baseY);
		arc.close();

		canvas.drawPath(arc,arcPaint);
	}

	public KfMapNodeView findNodeViewByString(String s){

		for(int i = 0;i < getChildCount();i++){
			KfMapNodeView view = data.get(i).getView();
			if(s.equals(view.getText())){
				return view;
			}
		}
		return null;
	}

	private void initColor(){
		String colorString ="";
		try {
			colorString= FileHelper.readFile(StructMapConstant.RDIR_MAP,StructMapConstant.COLOR_FILE_NAME,StructMapConstant.COLOR_FILE_NAME);
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(colorString.equals(FileHelper.NOT_FIND)){
			colorBackGrpund = Color.parseColor("#00343f");
			colorText = Color.parseColor("#1db0b8");
			colorLine = Color.parseColor("#37c6c0");
			colorNode = Color.parseColor("#d0e9ff");
			colorStartNode = Color.parseColor("#33aabb");
			colorArc = Color.parseColor("#eeeeee");
		}else{
			Map<String,Integer> maps = new Gson().fromJson(colorString,new TypeToken<Map<String,Integer>>(){}.getType());
			colorBackGrpund = maps.get(StructMapConstant.KEY_BACKGROUND_COLOR);
			colorText = maps.get(StructMapConstant.KEY_TEXT_COLOR);
			colorLine = maps.get(StructMapConstant.KEY_LINE_COLOR);
			colorNode = maps.get(StructMapConstant.KEY_NODE_COLOR);
			colorStartNode = maps.get(StructMapConstant.KEY_START_COLOR);
			colorArc = maps.get(StructMapConstant.KEY_ARC_COLOR);
		}
	}

	@SuppressLint("ClickableViewAccessibility")
	public void initData(){
		for(int i = 0; i < data.size();i++){
			
			//add view in MapData
			final KfMapNodeView child = new KfMapNodeView(getContext(),colorNode,colorStartNode,colorText);
			child.setText(data.get(i).getData());
			child.setVisibility(View.INVISIBLE);
			child.setPadding(30,30,30,30);
			data.get(i).setView(child);

			//set pre
			for(KfMapData d:data.get(i).getNex()){
				d.getPre().add(data.get(i));
			}
			
			//note as first node
			if(i == 0){
				child.setIsFirstView(true);
			}
		
			addView(child,LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
			
			//set child listener
			child.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View view) {
					if(mOnItemViewClickListener !=null)mOnItemViewClickListener.onClick((KfMapNodeView) view);
				}
			});

			child.setOnLongClickListener(new OnLongClickListener(){

					@Override
					public boolean onLongClick(View view)
					{
						child.touchStatus = KfMapNodeView.TOUCH_STATUS_ON_TOUCH_DRAGING;
						if(mOnItemViewClickListener !=null)mOnItemViewClickListener.onLongClick((KfMapNodeView) view);
						return true;
					}
				});
				
			child.setOnTouchListener(new OnTouchListener(){
				float x,y;
					@Override
					public boolean onTouch(View p1, MotionEvent p2) {
						switch(p2.getAction()){
							case  MotionEvent.ACTION_UP:
								child.touchStatus = KfMapNodeView.TOUCH_STATUS_DEFAULT;
								goBackNodeVIew(child);
								break;
							case MotionEvent.ACTION_MOVE:
								if(child.touchStatus == KfMapNodeView.TOUCH_STATUS_ON_TOUCH_DRAGING) {
									if(x != p2.getRawX()){
										child.setRCenter(new PointF(child.getRCenter().x +p2.getRawX()-x,child.getRCenter().y));
									}
									if(y != p2.getRawY()){
										child.setRCenter(new PointF(child.getRCenter().x,child.getRCenter().y+p2.getRawY()-y));
									}
									checkDistance(child,true,false,300,1000);
									layout(child);
									x = p2.getRawX();
									y = p2.getRawY();
								}
								break;
							case MotionEvent.ACTION_DOWN:
								x = p2.getRawX();
								y = p2.getRawY();
								break;
							default:
						}
						return false;
					}
				});
			
		}
	}

	private void goBackNodeVIew(final KfMapNodeView child) {
		int i = 0;
		for(; i< getChildCount();i++){
			if(data.get(i).getData().equals(child.getText().toString())){
				break;
			}
		}
		if(i == getChildCount()){
			return;
		}

		List<Map<String, Integer>> pos = new Gson().fromJson(position, new TypeToken<List<Map<String, Integer>>>() {
		}.getType());

		final int bx = pos.get(i).get("x");
		final int by = pos.get(i).get("y");

		int dx = (int) Math.abs(child.getRCenter().x-bx);
		int dy = (int) Math.abs(child.getRCenter().y-by);

		if(dx>10 || dy >10) {
			final Timer t = new Timer();
			t.schedule(new TimerTask() {
				@Override
				public void run() {
					int tx;
					int ty;
					int dx = (int) Math.abs(child.getRCenter().x-bx);
					int dy = (int) Math.abs(child.getRCenter().y-by);
					if(child.getRCenter().x -bx >10){
						tx = (int) (child.getRCenter().x -10);
					} else{
						tx = (int) (child.getRCenter().x +10);
					}
					if(child.getRCenter().y -by >10){
						ty = (int) (child.getRCenter().y -10);
					} else{
						ty = (int) (child.getRCenter().y +10);
					}
					if(dx < 10 && dy <10){
						cancel();
					}
					child.setRCenter(new PointF(tx,ty));
					((MobiActivity)getContext()).runOnUiThread(new Runnable() {
						@Override
						public void run() {
							layout(child);
						}
					});
					t.cancel();
				}
			},50);
		}
	}

	private void layout(KfMapNodeView child) {
		int x = (int) (child.getRCenter().x+baseX);
		int y = (int)(child.getRCenter().y+baseY);
		int r = (int)(child.getRadius());
		int h = child.getMeasuredHeight()/2;
		int w = child.getMeasuredWidth()/2;
		child.layout(x - w, y - h, x + w, y + h);
	}

	@Override
	protected void onLayout(boolean p1, int p2, int p3, int p4, int p5) {
		Log.d(TAG,"onLayout");

		if(show) {
			List<Map<String, Integer>> pos = new Gson().fromJson(position, new TypeToken<List<Map<String, Integer>>>() {
			}.getType());

			for (int i = 0; i < data.size(); i++) {
				KfMapNodeView view = data.get(i).getView();
				view.setRCenter(new PointF(pos.get(i).get("x"),pos.get(i).get("y")));
				dispatchedViews.add(view);
				layout(view);
			}
		}
	}
	
	private void onLoadData(){
		initData();
		//set status;
		drawStatus = DrawStatus.draw;
		Log.d(TAG,"onLoadData");
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		measureChildren(widthMeasureSpec,heightMeasureSpec);
	}

	public void startFirstView(final KfMapNodeView view,final Canvas canvas){
		AnimationSet aS = new AnimationSet(true);
		
		Animation tA = new TranslateAnimation(
			-view.getRCenter().x,
			0,
			-view.getRCenter().y,
			0);
		
		tA.setFillAfter(true);
		tA.setDuration(100);
		tA.setAnimationListener(new Animation.AnimationListener(){

				@Override
				public void onAnimationStart(Animation p1) {
					isLoaded = true;
				}

				@Override
				public void onAnimationEnd(Animation p1)
				{
					spreadKfMapNodeView(view,canvas);
				}

				@Override
				public void onAnimationRepeat(Animation p1) {
					// TODO: Implement this method
				}
			});
		aS.addAnimation(tA);
		view.startAnimation(aS);
	}
	
	public void startView(int duration,final KfMapNodeView startView,final KfMapNodeView targetView,final Canvas canvas){

		startView.setVisibility(View.VISIBLE);
		targetView.setVisibility(View.VISIBLE);
		AnimationSet aS = new AnimationSet(true);
			
		int dx;
		int dy;
		dx = (int) (startView.getRCenter().x- targetView.getRCenter().x);
		dy = (int) (startView.getRCenter().y- targetView.getRCenter().y);
		Animation tA = new TranslateAnimation(dx,0,dy,0);
			
		tA.setFillAfter(true);
		tA.setDuration(duration);

		tA.setAnimationListener(new Animation.AnimationListener(){

		@Override
		public void onAnimationStart(Animation p1) {
			// TODO: Implem
		}

		@Override
		public void onAnimationEnd(Animation p1) {
		//	drawLine(canvas,startView,targetView,mPaint);
			spreadKfMapNodeView(targetView,canvas);
			targetView.isOnCurrectPosition = true;
		}

		@Override
		public void onAnimationRepeat(Animation p1) {
			// TODO: Implement this method
		}
	});
		RotateAnimation rotateAnim = new RotateAnimation(
			0
			,720
			,Animation.RELATIVE_TO_SELF
			,0.5f
			,Animation.RELATIVE_TO_SELF
			,0.5f);

		rotateAnim.setDuration(duration);
		rotateAnim.setFillAfter(true);

		aS.addAnimation(rotateAnim);


		aS.addAnimation(tA);
		targetView.startAnimation(aS);
	}

	private void setChildRCenter(KfMapNodeView child) {
		do{
			float x = (float) (Math.random() *(getMeasuredWidth()*2-getMeasuredWidth()));
			float y = (float) (Math.random() * (getMeasuredHeight()*2-getMeasuredHeight()));
			child.setRCenter(new PointF(x,y));
		}while(checkDistance(child,true,false,300,1000));
		dispatchedViews.add(child);
	}

	public void spreadKfMapNodeView(KfMapNodeView view,Canvas canvas){
		isLoaded = true;
		//get kfmapdata from view;
		int pos = data.size()-1;
		for(;pos>-1;pos--){
			if(data.get(pos).getData().equals(view.getText().toString())) {
				break;
			}
		}
		if(pos == -1){
			return ;
		}
		KfMapData<String> d = data.get(pos);
		
		view.hasSpreaded = true;
		
		//spread
		for(int j = 0; j < d.getNex().size();j++){
			KfMapNodeView view2 = findNodeViewByString(d.getNex().get(j).getData());
			
			if(!view2.hasSpreaded) {
				startView(300,view,view2,canvas);
			}
		}
	}
	
	public void setData(List<KfMapData<String>> data){
		this.data = data;
	}

	public OnItemViewClickListener getmOnItemViewClickListener() {
		return mOnItemViewClickListener;
	}

	public void setOnItemViewClickListener(OnItemViewClickListener mOnItemViewClickListener) {
		this.mOnItemViewClickListener = mOnItemViewClickListener;
	}

	private OnTouchListener mScrollTouchListener = new OnTouchListener(){

		int lastRawX = 0;
		int lastRawY = 0;
		@Override
		public boolean onTouch(View p1, MotionEvent p2) {

			for(int i =0 ; i < getChildCount();i++){
				KfMapNodeView view = (StructView.KfMapNodeView) getChildAt(i);
				layout(view);
			}
			int rawX = (int) p2.getRawX();
			int rawY = (int) p2.getRawY();

			int dX = rawX-lastRawX;
			int dY = rawY - lastRawY;

			if(Math.abs(dX)<100) {
				baseX+=dX;
			}
			if(Math.abs(dY)<100) {
				baseY+=dY;
			}
			lastRawX = rawX;
			lastRawY = rawY;

			return true;
		}
	};

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public void show(){
		show = true;
		initColor();
		onLoadData();
	}

	public List<KfMapData<String>> getData() {
		return data;
	}

	public interface OnItemViewClickListener{
		void onClick(KfMapNodeView view);
		void onLongClick(KfMapNodeView view);
	}

	//TODO: 节点view
	public class KfMapNodeView extends android.support.v7.widget.AppCompatTextView {

		Paint mPaint;
		Paint firPaint;
		boolean shouldMove = false;
		boolean hasSpreaded = false;
		boolean isOnCurrectPosition = false;
		PointF sCenter;
		PointF rCenter;
		
		static final int TOUCH_STATUS_DEFAULT = 0;
		static final int TOUCH_STATUS_ON_TOUCH_MOVING = 1;
		static final int TOUCH_STATUS_ON_TOUCH_DRAGING = 2;
		
		int touchStatus = TOUCH_STATUS_DEFAULT;
		int backGroundColor;
		int startBackGroundColor;
		static final int DRAW_STATUS_RECT = 0;
		static final int DRAW_STATUS_DEFAULT = DRAW_STATUS_RECT;
		static final int DRAW_STATUS_CIRCLE = 1;
		int drawStatus = DRAW_STATUS_DEFAULT;		
		boolean isFirstView = false;

		public KfMapNodeView(Context context,int bgc,int sbgc,int tc){
			super(context,null);

			setGravity(Gravity.CENTER);
			setTextColor(tc);
			backGroundColor = bgc;
			startBackGroundColor = sbgc;
		}

		@Override
		protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
			super.onMeasure(widthMeasureSpec, heightMeasureSpec);

			mPaint = new Paint();
			mPaint.setColor(backGroundColor);
			mPaint.setStrokeWidth(33);
			mPaint.setAntiAlias(true);
			firPaint = new Paint();
			firPaint.setColor(startBackGroundColor);
			firPaint.setStrokeWidth(33);
			firPaint.setAntiAlias(true);
			mPaint.setAlpha(160);
		}

		@Override
		protected void onDraw(Canvas canvas) {
			
			// TODO: Implement this method
			canvas.save();
			if(drawStatus == DRAW_STATUS_CIRCLE){
				if(isFirstView) {
					canvas.drawCircle(getSCenter().x,getSCenter().y,getRadius(),firPaint);
				}
				canvas.drawCircle(getSCenter().x,getSCenter().y,getRadius()*0.9f,mPaint);
			} else if (drawStatus == DRAW_STATUS_RECT){
				float l = 0;
				float t = 0;
				float r = l + getMeasuredWidth();
				float b = t + getMeasuredHeight();
				RectF rect = new RectF(l,t,r,b);
				if(isFirstView){

					float dx = getMeasuredWidth()*0.1f;
					float dy = getMeasuredHeight()*0.1f;
					canvas.drawRoundRect(rect,30,30,firPaint);
					if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
						canvas.drawRoundRect(l+dx,t+dy,r-dx,b-dy,25,25,mPaint);
					}
				}else{
					canvas.drawRoundRect(rect,30,30,mPaint);
				}
			}	
			canvas.restore();
			super.onDraw(canvas);
		}

		public void setIsFirstView(boolean isFirstView)
		{
			this.isFirstView = isFirstView;
		}
		public boolean isFirstView()
		{
			return isFirstView;
		}
		public void setDrawStatus(int drawStatus)
		{
			this.drawStatus = drawStatus;
		}
		public int getDrawStatus()
		{
			return drawStatus;
		}
		public PointF getSCenter()
		{
			return new PointF(getMeasuredWidth()/2,getMeasuredHeight()/2);
		}
		public PointF getRCenter()
		{
			return rCenter;
		}
		public void setRCenter(PointF p){
			this.rCenter = p;
		}
		public void setState(int state)
		{
			this.touchStatus = state;
		}
		public int getState()
		{
			return touchStatus;
		}
		public float getRadius(){
			return Math.max(getMeasuredWidth(),getMeasuredHeight())/2;
		}
	}
}
