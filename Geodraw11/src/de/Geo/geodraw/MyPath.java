package de.Geo.geodraw;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;

public class MyPath extends Path{
	public Paint   mPaint;
	public static int myColor = Color.WHITE;
	public static int myStrokeWidth = 6;
	public MyPath() {
		super(); // calls the standard constructor of the parent class "Path"
	    mPaint = new Paint();
	    mPaint.setAntiAlias(true);
	    mPaint.setDither(true);
	    mPaint.setColor(myColor);
	    mPaint.setStyle(Paint.Style.STROKE);
	    mPaint.setStrokeJoin(Paint.Join.ROUND);
	    mPaint.setStrokeCap(Paint.Cap.ROUND);
	    mPaint.setStrokeWidth(myStrokeWidth);
	}	

	public void ChangeColor(int mColor)
	{
		myColor = mColor;
	}
	public void ChangeWidth(int mWidth)
	{
		myStrokeWidth = mWidth;
	}
	
}
