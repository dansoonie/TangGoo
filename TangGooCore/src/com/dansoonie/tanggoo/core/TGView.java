package com.dansoonie.tanggoo.core;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

public class TGView extends GLSurfaceView {

	private TGRenderer mRenderer;
	
	public TGView(Context context) {
		super(context);
		setEGLContextClientVersion(2);
	}
	
	public TGView(Context context, AttributeSet attribSet) {
		super(context, attribSet);
		setEGLContextClientVersion(2);
	}
	
	protected void show(TGWorld world) {
		mRenderer = new TGRenderer(world);
		setRenderer(mRenderer);
		setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
	}
}
