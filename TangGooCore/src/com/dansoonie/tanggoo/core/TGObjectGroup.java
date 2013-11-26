package com.dansoonie.tanggoo.core;

import java.util.LinkedList;

import javax.microedition.khronos.opengles.GL10;

import android.util.Log;

public class TGObjectGroup extends TGObject {
	private static final String TAG = "TGModelGroup";
	
	private LinkedList<TGObject> mObjectList;
	private TGModel[] mReferredObjectList;
	
	public TGObjectGroup() {
		mObjectList = new LinkedList<TGObject>();
		mReferredObjectList = new TGModel[5];
	}
	
	private void updateReferredObjectList() {
		// TODO: need shrinking mechanism when array is too large.
		mReferredObjectList = mObjectList.toArray(mReferredObjectList);
	}
	
	public boolean add(TGObject object) {
		return mObjectList.add(object);
	}
	
	public boolean remove(TGObject object) {
		return mObjectList.remove(object);
	}
	
	@Override
	protected void draw(GL10 gl) {
		Log.d(TAG, "draw");
		if (mObjectList.size()>0) {
			updateReferredObjectList();
			gl.glPushMatrix();
			for (TGModel model : mReferredObjectList) {
				if (model == null) {
					break;
				}
				model.draw(gl);
			}
			gl.glPopMatrix();
		}
	}
	
}
