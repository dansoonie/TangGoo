package com.dansoonie.tanggoo.core;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;

import android.opengl.Matrix;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;
import android.util.Log;

public class TGRenderer implements GLSurfaceView.Renderer {
	private static final String TAG = "TGRenderer";

	TGWorld mWorld;
	private float mViewRatio;
	private float[] mProjection;
	private float[] mLookAt;

	protected TGRenderer(TGWorld world) {
		mWorld = world;
		mProjection = new float[16];
		mLookAt = new float[16];
		
		Matrix.setIdentityM(mProjection, 0);
		Matrix.setIdentityM(mLookAt, 0);
	}

	@Override
	public void onDrawFrame(GL10 gl) {
		/**
		 * Usually, the first thing one might want to do is to clear
		 * the screen. The most efficient way of doing this is to use
		 * glClear().
		 */		
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		
		TGCamera camera = mWorld.getCamera();
		boolean updateProjection = camera.updateProjection();
		boolean updateLookAt = camera.updateLookAt();
		if (updateProjection) {
			Log.d(TAG, "onDrawFrame: updateProjection");
			float nearDistance = camera.getNear();
			float farDistance = camera.getFar();
			float focalDistance = camera.getFocalDisatance();

			float viewWidth = camera.getFocalViewWidth()*nearDistance/focalDistance;
			float viewHeight = viewWidth/mViewRatio;

			Matrix.frustumM(mProjection, 0, -viewWidth*0.5f, viewWidth*0.5f, -viewHeight*0.5f, viewHeight*0.5f, nearDistance, farDistance);
		}
		gl.glMatrixMode(GL10.GL_PROJECTION);
		gl.glLoadIdentity();
		gl.glLoadMatrixf(mProjection, 0);
		
		if (updateLookAt) {
			Log.d(TAG, "onDrawFrame: updateLookAt");
			final float[] cameraLocation = new float[3];
			final float[] cameraCenter = new float[3];
			final float[] cameraUpVector = new float[3];

			camera.getTranslate(cameraLocation);
			camera.getCenter(cameraCenter);
			camera.getUpVector(cameraUpVector);
			
			Matrix.setLookAtM(mLookAt, 0,
					cameraLocation[0], cameraLocation[1], cameraLocation[2],
					cameraCenter[0], cameraCenter[1], cameraCenter[2],
					cameraUpVector[0], cameraUpVector[1], cameraUpVector[2]);
		}
		
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		gl.glLoadIdentity();
		gl.glLoadMatrixf(mLookAt, 0);

		mWorld.drawWorld(gl);
	}

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		Log.d(TAG, "onSurfaceChanged");
		gl.glViewport(0, 0, width, height);
		mViewRatio = (float) width / height;
	}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		Log.d(TAG, "on surface created");
	}
}
