package com.dansoonie.tanggoo.core;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.util.Log;

public class TGRenderer implements GLSurfaceView.Renderer {
	private static final String LOG_TAG = "TGRenderer";

	private TGWorld mWorld;
	private Context mContext;
	private float mViewRatio;
	private TGMatrixManager mMatrixManager;

	protected TGRenderer(TGWorld world) {
		mWorld = world;
		if (mContext == null) {
			Log.d(LOG_TAG, "TGRenderer context is null");
		}
		mMatrixManager = new TGMatrixManager();		
	}

	@Override
	public void onDrawFrame(GL10 gl) {
		TGCamera camera = mWorld.getCamera();
		boolean updateProjection = camera.updateProjection();
		boolean updateLookAt = camera.updateLookAt();
		if (updateProjection) {
			float nearDistance = camera.getNear();
			float farDistance = camera.getFar();
			float focalDistance = nearDistance + (farDistance-nearDistance)*camera.getFocalPosition();

			float viewWidth = camera.getFocalViewWidth()*nearDistance/focalDistance;
			float viewHeight = viewWidth/mViewRatio;

			mMatrixManager.frustum(-viewWidth*0.5f, viewWidth*0.5f,-viewHeight*0.5f, viewHeight*0.5f, nearDistance, farDistance);			
		}
		if (updateLookAt) {
			final float[] cameraLocation = new float[3];
			final float[] cameraCenter = new float[3];
			final float[] cameraUpVector = new float[3];

			camera.getTranslate(cameraLocation);
			camera.getCenter(cameraCenter);
			camera.getUpVector(cameraUpVector);

			mMatrixManager.lookAt(cameraLocation[0], cameraLocation[1], cameraLocation[2],
					cameraCenter[0], cameraCenter[1], cameraCenter[2],
					cameraUpVector[0], cameraUpVector[1], cameraUpVector[2]);
		}

		if (updateProjection || updateLookAt) {
			mMatrixManager.updateBaseMVP();
		}

		/**
		 * Usually, the first thing one might want to do is to clear
		 * the screen. The most efficient way of doing this is to use
		 * glClear().
		 */		
		GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);

		mWorld.drawWorld(gl, mMatrixManager);
	}

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		GLES20.glViewport(0, 0, width, height);
		mViewRatio = (float) width / height;		
	}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		Log.d(LOG_TAG, "on surface created");
		TGShaderManager shaderManager = TGShaderManager.getInstance();
		shaderManager.createShaderProgram(new TGDefaultShaderProgram());
		mWorld.mActivator.activateWorld();
		// Set the background frame color
		GLES20.glClearColor(0.5f, 0.5f, 0.5f, 1.0f);		
	}



}
