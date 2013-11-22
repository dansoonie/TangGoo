package com.dansoonie.tanggoo.core;

import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.opengl.GLES20;
import android.util.Log;

public class TGWorld {
	private static final String TAG = "TGWorld";

	private Context mContext;
	private TGCamera mCamera;
	private TGModelGroup mRootContainer;
	Activator mActivator;

	public interface Activator {
		public void activateWorld();
	}

	public TGWorld(Context context, Activator activator) {
		mContext = context;
		mCamera = new TGCamera();
		mRootContainer = new TGModelGroup();
		mActivator = activator;
	}

	public TGCamera getCamera() {
		return mCamera;
	}

	public void show(TGView view) {
		view.show(this);
	}	

	public boolean put(TGModel model) {
		return mRootContainer.add(model);
	}

	public boolean put(TGModelGroup modelGroup) {
		return mRootContainer.add(modelGroup);
	}

	public boolean remove(TGModel model) {
		return mRootContainer.remove(model);
	}

	public boolean remove(TGModelGroup modelGroup) {
		return mRootContainer.remove(modelGroup);
	}

	protected void drawWorld(GL10 gl, TGMatrixManager matrixManager) {
		mRootContainer.draw(gl, matrixManager);
	}

	public static void checkGlError(String op) {
		int error;
		while ((error = GLES20.glGetError()) != GLES20.GL_NO_ERROR) {
			Log.e(TAG, op + ": glError " + error);
			throw new RuntimeException(op + ": glError " + error);
		}
	}

	public void resume() {
		Log.d(TAG, "TGWorld#onResume()");
		TGShaderManager.getInstance(mContext);
	}

	public void pause() {
		Log.d(TAG, "TGWorld#onPause()");
		TGShaderManager.getInstance().clearContext();
	}
}
