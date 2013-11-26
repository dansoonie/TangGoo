package com.dansoonie.tanggoo.core;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

import android.graphics.Bitmap;
import android.opengl.GLES20;
import android.opengl.GLUtils;
import android.util.Log;

public class TGModel extends TGObject {	
	private static final String TAG = "TGModel";
	
	private int[] mTextures;

	private static final int FLOAT_SIZE_BYTES = 4;
	
	protected float[] mVertices;
	protected float[] mUV;
	
	private FloatBuffer mBufferVertices;
	private FloatBuffer mBufferUV;

	public TGModel() {
		
	}
	
	protected void init(float[] vertices, float[] uv) {
		mVertices = new float[vertices.length];
		System.arraycopy(vertices, 0, mVertices, 0, vertices.length);
		mBufferVertices = toFloatBuffer(mVertices);
		
		mUV = new float[uv.length];
		System.arraycopy(uv, 0, mUV, 0, uv.length);
		mBufferUV = toFloatBuffer(mUV);
	}
	
	private FloatBuffer toFloatBuffer(float[] array) {
		FloatBuffer floatBuffer = ByteBuffer.allocateDirect(array.length*FLOAT_SIZE_BYTES).order(ByteOrder.nativeOrder()).asFloatBuffer();
		floatBuffer.put(array).position();
		return floatBuffer;
	}
	
	protected void draw(GL10 gl) {
		Log.d(TAG, "draw");
		gl.glPushMatrix();
		gl.glTranslatef(mTransformation.mTranslate[0], mTransformation.mTranslate[1], mTransformation.mTranslate[2]);
		gl.glScalef(mTransformation.mScale[0], mTransformation.mScale[1], mTransformation.mScale[2]);
		
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		mBufferVertices.position(0);
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, mBufferVertices);
		gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);
		TGWorld.checkGlError("glDrawArrays", gl);
		gl.glPopMatrix();
	}

	public void setTexture(Bitmap bitmap) {
		if (mTextures == null) {
			mTextures = new int[1];
		}

		if (mTextures[0] >= 0) {
			GLES20.glDeleteTextures(1, mTextures, 0);
		}		

		GLES20.glGenTextures(1, mTextures, 0);
		GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, mTextures[0]);

		GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, 
				GLES20.GL_TEXTURE_MIN_FILTER,
				GLES20.GL_NEAREST);
		GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D,
				GLES20.GL_TEXTURE_MAG_FILTER,
				GLES20.GL_LINEAR);

		GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_REPEAT);
		GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_REPEAT);

		GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0);
		//bitmap.recycle();
	}
}
