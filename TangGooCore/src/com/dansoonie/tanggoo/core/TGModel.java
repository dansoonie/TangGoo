package com.dansoonie.tanggoo.core;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

import android.graphics.Bitmap;
import android.opengl.GLES20;
import android.opengl.GLUtils;

public class TGModel extends TGObject {	
	private static final String TAG = "TGModel";

	private String mShaderProgramName = "default_shader";
	private TGDefaultShaderProgram mShaderProgram;
	private int[] mTextures;

	private static final int FLOAT_SIZE_BYTES = 4;
	private static final int TRIANGLE_VERTICES_DATA_STRIDE_BYTES = 5 * FLOAT_SIZE_BYTES;
	private static final int TRIANGLE_VERTICES_DATA_POS_OFFSET = 0;
	private static final int TRIANGLE_VERTICES_DATA_UV_OFFSET = 3;
	private final float[] mTriangleVerticesData = {
			// X, Y, Z, U, V
			-1.0f, -1.0f, 0f, -0.5f, 0.0f,
			1.0f, -1.0f, 0f, 1.5f, -0.0f,
			0f,  1.0f, 0f, 0.5f,  1.61803399f };

	private FloatBuffer mTriangleVertices;
	private TGShaderManager mShaderManager;

	public TGModel() {
		this(true);
	}

	public TGModel(boolean terminalNode) {
		if (terminalNode) {
			mShaderManager = TGShaderManager.getInstance();
			mShaderProgram = (TGDefaultShaderProgram)mShaderManager.getShaderProgram(mShaderProgramName);

			mTriangleVertices = ByteBuffer.allocateDirect(mTriangleVerticesData.length
					* FLOAT_SIZE_BYTES).order(ByteOrder.nativeOrder()).asFloatBuffer();
			mTriangleVertices.put(mTriangleVerticesData).position(0);
		}
	}

	protected void bindShader(TGMatrixManager matrixManager) {
		GLES20.glUseProgram(mShaderProgram.getProgram());

		if (mTextures!= null) {
			GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
			GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, mTextures[0]);
		}

		mTriangleVertices.position(TRIANGLE_VERTICES_DATA_POS_OFFSET);
		GLES20.glVertexAttribPointer(mShaderProgram.getAttributePositionHandle(), 3, GLES20.GL_FLOAT, false,
				TRIANGLE_VERTICES_DATA_STRIDE_BYTES, mTriangleVertices);
		TGWorld.checkGlError("glVertexAttribPointer maPosition");
		mTriangleVertices.position(TRIANGLE_VERTICES_DATA_UV_OFFSET);
		GLES20.glEnableVertexAttribArray(mShaderProgram.getAttributePositionHandle());
		TGWorld.checkGlError("glEnableVertexAttribArray maPositionHandle");
		GLES20.glVertexAttribPointer(mShaderProgram.getAttributeTextureHandle(), 2, GLES20.GL_FLOAT, false,
				TRIANGLE_VERTICES_DATA_STRIDE_BYTES, mTriangleVertices);
		TGWorld.checkGlError("glVertexAttribPointer maTextureHandle");
		GLES20.glEnableVertexAttribArray(mShaderProgram.getAttributeTextureHandle());
		TGWorld.checkGlError("glEnableVertexAttribArray maTextureHandle");

		GLES20.glUniformMatrix4fv(mShaderProgram.getUniformMVPMatrixHandle(), 1, false, matrixManager.peek(), 0);
	}

	protected void draw(GL10 gl, TGMatrixManager matrixManager) {

		matrixManager.push();
		matrixManager.translate(mTransformation.mTranslate[0], mTransformation.mTranslate[1], mTransformation.mTranslate[2]);
		//matrixManager.rotate(angle, x, y, z)
		bindShader(matrixManager);
		GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, 3);
		TGWorld.checkGlError("glDrawArrays");
		matrixManager.pop();
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
