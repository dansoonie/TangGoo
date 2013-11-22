package com.dansoonie.tanggoo.core;


public class TGObject {
	protected TGTransformation mTransformation;
	
	public TGObject() {
		mTransformation = new TGTransformation();
	}
	
	public void translate(float tx, float ty, float tz) {
		mTransformation.translate(tx, ty, tz);
	}
	
	public void setTranslate(float tx, float ty, float tz) {
		mTransformation.setTranslate(tx, ty, tz);
	}
	
	public void getTranslate(float[] t) {
		mTransformation.getTranslate(t);
	}
	
	public void getTranslate(float[] t, int idx) {
		mTransformation.getTranslate(t, idx);
	}
	
	public float getTranslate(int axis) {
		return mTransformation.mTranslate[axis];
	}
	
}
