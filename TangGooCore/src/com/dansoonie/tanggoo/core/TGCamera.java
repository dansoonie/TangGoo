package com.dansoonie.tanggoo.core;

import javax.microedition.khronos.opengles.GL10;

import android.util.Log;

public class TGCamera extends TGObject {
	private static final String TAG="TGCamera";
	
	private float mNear;	
	private float mFar;
	private float mFocalPosition;
	private float mFocalViewWidth;
	private float mFocalDistance;
	
	private float[] mV_Direction;	// Direction x, y, z vector
	private float[] mV_Up;
	
	private boolean mDirtyProjection;
	private boolean mDirtyLookAt;	
	
	public TGCamera() {
		super();
		mV_Direction = new float[3];
		mV_Direction[0] = 0;
		mV_Direction[1] = 0;
		mV_Direction[2] = -1;
		
		mV_Up = new float[3];
		mV_Up[0] = 0;
		mV_Up[1] = 1;
		mV_Up[2] = 0;
	}
	
	public float getNear() {
		return mNear;
	}
	
	public float getFar() {
		return mFar;
	}
	
	public float getFocalPosition() {
		return mFocalPosition;
	}
	
	public float getFocalViewWidth() {
		return mFocalViewWidth;
	}
	
	public float getFocalDisatance() {
		return mFocalDistance;
	}
	
	public void setCameraView(float near, float far, float focalPosition, float focalViewWidth) {
		Log.d(TAG, "setCameraView");
		mNear = near;
		mFar = far;
		mFocalPosition = focalPosition;
		mFocalViewWidth = focalViewWidth;
		mFocalDistance = mNear + (mFar-mNear)*mFocalPosition;
		mDirtyProjection = true;
	}
	
	public void setUpVector(float vx, float vy, float vz) {
		mV_Up[0] = vx;
		mV_Up[1] = vy;
		mV_Up[2] = vz;
		mDirtyLookAt = true;
	}
	
	@Override
	public void translate(float tx, float ty, float tz) {
		Log.d(TAG, "translate");
		super.translate(tx, ty, tz);
		mDirtyLookAt = true;
	}
	
	@Override
	public void setTranslate(float x, float y, float z) {
		super.setTranslate(x, y, z);
		mDirtyLookAt = true;
	}
	
	public void getUpVector(float[] up) {
		if (up.length<3) {
			// TODO: Error handling.
			return;
		}		
		System.arraycopy(mV_Up, 0, up, 0, 3);
	}
	
	public void setDirection(float vx, float vy, float vz) {
		mV_Direction[0] = vx;
		mV_Direction[1] = vy;
		mV_Direction[2] = vz;
		mDirtyLookAt = true;
	}
	
	public void getCenter(float[] center) {
		getCenter(center, 0);
	}
	
	public void getCenter(float[] center, int idx) {
		if (center.length<idx+3) {
			// TODO: Error handling.
			return;
		}		
		for (int i=0; i<3; i++) {
			center[idx+i] = mTransformation.mTranslate[i]+mV_Direction[i]; 
		}
	}	
	
	public boolean updateProjection() {
		return mDirtyProjection;
	}	
	
	public boolean updateLookAt() {
		return mDirtyLookAt;		
	}
	
	void draw(GL10 gl) {
		// Do nothing
		Log.e(TAG, "TGCamera should not call draw!!!");
	}
}
