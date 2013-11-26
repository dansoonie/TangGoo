package com.dansoonie.tanggoo.core;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.util.Log;

public class TGView extends GLSurfaceView {
	private static final String TAG = "TGView";
	
	private TGRenderer mRenderer;
	private TGWorld mWorld;
	
	public TGView(Context context) {
		super(context);
		mWorld = new TGWorld(context);
	}
	
	public TGView(Context context, AttributeSet attribSet) {
		super(context, attribSet);
		mWorld = new TGWorld(context);
	}
	
	public TGWorld getWorld() {
		return mWorld;
	}
	
	public void show() {
		Log.d(TAG, "show");
		mRenderer = new TGRenderer(mWorld);
		setRenderer(mRenderer);
		setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
	}
	
	@Override
	public void onPause() {
		super.onPause();
		Log.d(TAG, "onPause()");
	}
	
	@Override
	public void onResume() {
		super.onResume();
		Log.d(TAG, "onResume");
	}
}
