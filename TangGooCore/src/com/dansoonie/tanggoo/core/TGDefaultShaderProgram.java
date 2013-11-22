package com.dansoonie.tanggoo.core;

import android.opengl.GLES20;
import android.util.Log;

public class TGDefaultShaderProgram extends TGShaderProgram {
	private static final String LOG_TAG = "TGDefaultShaderProgram";
	private int muMVPMatrixHandle;
	private int maPositionHandle;
	private int maTextureHandle;
	
	public TGDefaultShaderProgram() {
		super();
	}
	
	@Override
	protected void setProgramName() {
		mProgramName = "default_shader";		
	}

	@Override
	protected void onLoadProgram() {
		maPositionHandle = GLES20.glGetAttribLocation(getProgram(), "aPosition");

        if (maPositionHandle == -1) {
            throw new RuntimeException("Could not get attrib location for aPosition");
        }
        maTextureHandle = GLES20.glGetAttribLocation(getProgram(), "aTextureCoord");
        Log.e(LOG_TAG, "glGetAttribLocation aTextureCoord");
        if (maTextureHandle == -1) {
            throw new RuntimeException("Could not get attrib location for aTextureCoord");
        }

        muMVPMatrixHandle = GLES20.glGetUniformLocation(getProgram(), "uMVPMatrix");
        Log.e(LOG_TAG, "glGetUniformLocation uMVPMatrix");
        if (muMVPMatrixHandle == -1) {
            throw new RuntimeException("Could not get attrib location for uMVPMatrix");
        }
	}
	
	public int getUniformMVPMatrixHandle() {
		return muMVPMatrixHandle;
	}
	
	public int getAttributePositionHandle() {
		return maPositionHandle;
	}
	
	public int getAttributeTextureHandle() {
		return maTextureHandle;
	}

}
