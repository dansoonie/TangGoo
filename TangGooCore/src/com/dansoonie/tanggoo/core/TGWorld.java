package com.dansoonie.tanggoo.core;

import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.opengl.GLES20;
import android.util.Log;

public class TGWorld {
	private static final String TAG = "TGWorld";
	
	private Context mContext;
	private TGView mView;
	private TGCamera mCamera;
	private TGObjectGroup mRootContainer;

	public TGWorld(Context context) {
		mContext = context;
		mCamera = new TGCamera();
		mRootContainer = new TGObjectGroup();
	}

	public TGCamera getCamera() {
		return mCamera;
	}

	public boolean put(TGModel model) {
		return mRootContainer.add(model);
	}

	public boolean put(TGObjectGroup modelGroup) {
		return mRootContainer.add(modelGroup);
	}

	public boolean remove(TGModel model) {
		return mRootContainer.remove(model);
	}

	public boolean remove(TGObjectGroup modelGroup) {
		return mRootContainer.remove(modelGroup);
	}

	protected void drawWorld(GL10 gl) {
		mRootContainer.draw(gl);
	}

	public static void checkGlError(String op, GL10 gl) {
		int error;
		while ((error = gl.glGetError()) != GL10.GL_NO_ERROR) {
			Log.e(TAG, op + ": glError " + error);
			throw new RuntimeException(op + ": glError " + error);
		}
	}
}
