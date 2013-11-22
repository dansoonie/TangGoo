package com.dansoonie.tanggoo.core;

import android.util.Log;

public abstract class TGShaderProgram {
	private static final String LOG_TAG = "TGShaderProgram";
	
	protected String mProgramName;
	private int mProgram = -1;
	
	public TGShaderProgram() {		
		setProgramName();
		if (mProgramName==null) {
			Log.e(LOG_TAG, "TGShaderProgram: The name of the shader program is null. Override setProgramName()");
		}
	}
	
	public String getProgramName() {
		return mProgramName;
	}
	
	public void setProgram(int program) {
		mProgram = program;
	}
	
	public int getProgram() {
		return mProgram;
	}
	
	protected abstract void setProgramName();
	protected abstract void onLoadProgram();
}
