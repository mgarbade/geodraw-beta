package de.Geo.geodraw;

import java.util.ArrayList;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnTouchListener;

public class DrawView extends SurfaceView implements OnTouchListener {
	int width, height;
	private MyPath    mMyPath;
	private ArrayList<MyPath> MyPaths = new ArrayList<MyPath>();
	private Paint   mBitmapPaint;
	boolean changeCol = false;
	boolean changeWid = false;
	int newColor;
	int newWidth;
	
	public DrawView(Context context) {
		super(context);
	    setFocusable(true);
	    setFocusableInTouchMode(true);

	    this.setOnTouchListener(this);
	    mMyPath = new MyPath();
	    MyPaths.add(mMyPath);

		new Paint();
	    mBitmapPaint = new Paint(Paint.DITHER_FLAG); // Dithering affects how colors that are higher precision than the device are down-sampled
	}
	@Override
	protected void onDraw(Canvas canvas) {            
        if (GetterSetter.mBitmap != null) { 
        	width  = canvas.getWidth();
        	height = canvas.getHeight();
        	canvas.drawBitmap(GetterSetter.mBitmap, null, new RectF(0, 0, width, height), mBitmapPaint); //this method is slow, probably 
        }
        //HERE THE PATHS ARE FINALLY DRAWN
        for (MyPath p : MyPaths){
        		canvas.drawPath(p, p.mPaint);
        }
    }
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }
	@Override
	public boolean onTouch(View arg0, MotionEvent event) {
	      float x = event.getX();
	      float y = event.getY();
	
	      switch (event.getAction()) {
	          case MotionEvent.ACTION_DOWN:
	              touch_start(x, y);
	              invalidate();
	              break;
	          case MotionEvent.ACTION_MOVE:
	              touch_move(x, y);
	              invalidate();
	              break;
	          case MotionEvent.ACTION_UP:
	              touch_up();
	              invalidate();
	              break;
	      }

	      return true;
	}
	private float mX, mY;
    private static final float TOUCH_TOLERANCE = 4;

    private void touch_start(float x, float y) {
    	//first create a new linepath "MyPath" with certain properties
        mMyPath = new MyPath();
        MyPaths.add(mMyPath);
        mMyPath.reset();
        mMyPath.moveTo(x, y);
        mX = x;
        mY = y;
    }
    private void touch_move(float x, float y) {
        float dx = Math.abs(x - mX);
        float dy = Math.abs(y - mY);
        if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
            mMyPath.quadTo(mX, mY, (x + mX)/2, (y + mY)/2);
            mX = x;
            mY = y;
        }
    }
    private void touch_up() {
        mMyPath.lineTo(mX, mY);
        // commit the path to our offscreen
    }
	// used to clear the screen
	public void clearPoints () {
		MyPaths.clear();
		invalidate();
	}
	public void undoLastLine () {
		if(MyPaths.size()>0)
		{
			MyPaths.remove(MyPaths.size()-1);
			invalidate();
		}
	}
	// used to set drawing color
	public void changeColour (int col_in) {
		changeWid = true;
		switch (col_in) {
			case 0 : {
				newColor = Color.WHITE;
				break;
			}
			case 1 : {
				newColor = Color.BLUE;
				break;
			}
			case 2 : {
				newColor = Color.CYAN;
				break;
			}
			case 3 : {
				newColor = Color.GREEN;
				break;
			}
			case 4 : {
				newColor = Color.MAGENTA;
				break;
			}
			case 5 : {
				newColor = Color.RED;
				break;
			}
			case 6 : {
				newColor = Color.YELLOW;
				break;
			}
			case 7 : {
				newColor = Color.BLACK;
				break;
			}
		}
		mMyPath.ChangeColor(newColor); // here the new PaintObject is set to the new color
	}
	public void changeWidth (int width_in){
		changeWid = true;
		switch (width_in) {
			case 0 : {
				newWidth = 1;
				break;
			}
			case 1 : {
				newWidth = 3;
				break;
			}
			case 2 : {
				newWidth = 6;
				break;
			}
			case 3 : {
				newWidth = 10;
				break;
			}
			case 4 : {
				newWidth = 14;
				break;
			}
		}
		mMyPath.ChangeWidth(newWidth); // here the new PaintObject is set to the new width
		
	}


}
