package com.dansoonie.tanggoo.core.model;

import com.dansoonie.tanggoo.core.TGModel;

public class TGPanel extends TGModel {
	private static final String TAG = "TGPanel";
	
	public TGPanel(float width, float height) {
		float[] vertices = {
			-0.5f*width,	0.5f*height,	0.0f,	// top left
			-0.5f*width,	-0.5f*height,	0.0f,	// bottom left
			0.5f*width,	0.5f*height,	0.0f,		// top right
			0.5f*width,	-0.5f*height,	0.0f		// bottom right
		};
		
		float[] uv = {
				0.0f, 0.0f,
				0.0f, 1.0f,
				1.0f, 0.0f,
				1.0f, 1.0f
		};
		
		init(vertices, uv);
	}
}
