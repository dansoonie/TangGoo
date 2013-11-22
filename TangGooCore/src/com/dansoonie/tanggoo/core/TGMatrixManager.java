package com.dansoonie.tanggoo.core;

import java.util.Stack;

import android.opengl.Matrix;

public class TGMatrixManager {
	private float[] mM_Projection;
	private float[] mM_View;
	private float[] mM_PVM;
	
	private Stack<float[]> mMatrixStack;
	
	public TGMatrixManager() {
		mM_Projection = new float[16];
		mM_View = new float[16];
		mM_PVM = new float[16];
		Matrix.setIdentityM(mM_Projection, 0);
		Matrix.setIdentityM(mM_View, 0);
		Matrix.setIdentityM(mM_PVM, 0);
		
		mMatrixStack = new Stack<float[]>();
	}
	
	public void init() {
		if (mM_Projection == null) {
			mM_Projection = new float[16];
		}
		if (mM_View == null) {
			mM_View = new float[16];	
		}
		
		if (mM_PVM == null) {
			mM_PVM = new float[16];	
		}
		
		Matrix.setIdentityM(mM_Projection, 0);
		Matrix.setIdentityM(mM_View, 0);
		Matrix.setIdentityM(mM_PVM, 0);
		
		if (mMatrixStack == null) {
			mMatrixStack = new Stack<float[]>();
		}
		mMatrixStack.clear();
	}
	
	public float[] peek() {
		return mMatrixStack.peek();
	}
	
	public void push() {
		mMatrixStack.push(mM_PVM);
	}
	
	public void pop() {
		mM_PVM = mMatrixStack.pop();
	}
	
	public void translate(float x, float y, float z) {
		Matrix.translateM(mM_PVM, 0, x, y, z);
	}
	
	public void rotate(float angle, float x, float y, float z) {
		Matrix.rotateM(mM_PVM, 0, angle, x, y, z);
	}
	
	protected void frustum(float left, float right, float bottom, float top, float near, float far) {
		Matrix.frustumM(mM_Projection, 0, left, right, bottom, top, near, far);
	}
	
	protected void lookAt(float eyeX, float eyeY, float eyeZ, float centerX, float centerY, float centerZ, float upX, float upY, float upZ) {
		Matrix.setLookAtM(mM_View, 0, eyeX, eyeY, eyeZ, centerX, centerY, centerZ, upX, upY, upZ);
	}
	
	protected void updateBaseMVP() {
		Matrix.multiplyMM(mM_PVM, 0, mM_Projection, 0, mM_View, 0);
	}
}
