package com.dansoonie.tanggoo.core;

public class TGQuaternion {

	private float mW;
	private float mX;
	private float mY;
	private float mZ;

	private float[] mMatrix;

	private double convertDegreeToRadian(float angle) {
		return Math.PI*angle/180.0;
	}

	public TGQuaternion() {
		mMatrix = new float[16];
		mW = 1;
		mX = mY = mZ = 0;
	}

	public float[] getRotaionMatrix(float angle, float x, float y, float z) {
		double magnitude = Math.sqrt(x*x + y*y + z*z);
		double rad = convertDegreeToRadian(angle);
		double sinAngle = Math.sin(rad*0.5);
		double w = Math.cos(rad*0.5);
		double i = x/magnitude*sinAngle;
		double j = y/magnitude*sinAngle;
		double k = z/magnitude*sinAngle;

		// This is the column major matrix of the quaternion 
		mMatrix[0] = (float)(1 - 2*j*j - 2*k*k);
		mMatrix[1] = (float)(2*i*j + 2*w*k);
		mMatrix[2] = (float)(2*i*k - 2*w*j);
		mMatrix[3] = 0;
		mMatrix[4] = (float)(2*i*j - 2*w*k);
		mMatrix[5] = (float)(1 - 2*i*i - 2*k*k);
		mMatrix[6] = (float)(2*j*k + 2*w*i);
		mMatrix[7] = 0;
		mMatrix[8] = (float)(2*i*k + 2*w*j);
		mMatrix[9] = (float)(2*j*k - 2*w*i);
		mMatrix[10] = (float)(1 - 2*i*i - 2*j*j);
		mMatrix[11] = 0;
		mMatrix[12] = mMatrix[13] = mMatrix[14] = 0;
		mMatrix[15] = 1;
		return mMatrix;

	}
}