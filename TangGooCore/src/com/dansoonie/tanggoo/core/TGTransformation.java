package com.dansoonie.tanggoo.core;

public class TGTransformation {
	float[] mTranslate = { 0.0f, 0.0f, 0.0f };
	TGQuaternion mOrientation;
	float[] mScale = {1.0f, 1.0f, 1.0f};
	
	public TGTransformation() {
		mOrientation = new TGQuaternion();
	}
	
	public void translate(float x, float y, float z) {
		mTranslate[0] += x;
		mTranslate[1] += y;
		mTranslate[2] += z;
	}
	
	public void setTranslate(float x, float y, float z) {
		mTranslate[0] = x;
		mTranslate[1] = y;
		mTranslate[2] = z;
	}
	
	public float getTranslateX() {
		return mTranslate[0];
	}
	
	public float getTranslateY() {
		return mTranslate[1];
	}
	
	public float getTranslateZ() {
		return mTranslate[2];
	}
	
	public void getTranslate(float[] t) {
		getTranslate(t, 0);
	}
	
	public void getTranslate(float[] t, int idx) {
		System.arraycopy(mTranslate, 0, t, idx, 3);
	}
	
	public void scale(float s) {
		mScale[0] *= s;
		mScale[1] *= s;
		mScale[2] *= s;
	}
	
	public void setScale(float s) {
		mScale[0] = mScale[1] = mScale[2] = s;
	}
	
	public float getScaleX() {
		return mScale[0];
	}
	
	public float getScaleY() {
		return mScale[1];
	}
	
	public float getScaleZ() {
		return mScale[2];
	}
	
	public void getScale(float[] s) {
		getScale(s, 0);
	}
	
	public void getScale(float[] s, int idx) {
		System.arraycopy(mScale, 0, s, idx, 3);
	}
}
