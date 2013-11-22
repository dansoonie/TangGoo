package com.dansoonie.tanggoo.core;

import java.util.LinkedList;

import javax.microedition.khronos.opengles.GL10;

public class TGModelGroup extends TGModel {
	private LinkedList<TGModel> mObjectList;
	private TGModel[] mReferredObjectList;
	
	public TGModelGroup() {
		super(false);
		mObjectList = new LinkedList<TGModel>();
		mReferredObjectList = new TGModel[5];
	}
	
	private void updateReferredObjectList() {
		// TODO: need shrinking mechanism when array is too large.
		mReferredObjectList = mObjectList.toArray(mReferredObjectList);
	}
	
	public boolean add(TGModel object) {
		return mObjectList.add(object);
	}
	
	public boolean remove(TGModel object) {
		return mObjectList.remove(object);
	}
	
	@Override
	protected void draw(GL10 gl, TGMatrixManager matrixManager) {
		if (mObjectList.size()>0) {
			updateReferredObjectList();
			matrixManager.push();
			matrixManager.translate(mTransformation.mTranslate[0], mTransformation.mTranslate[1], mTransformation.mTranslate[2]);
			//matrixManager.rotate(angle, x, y, z)
			for (TGModel model : mReferredObjectList) {
				if (model == null) {
					break;
				}
				model.draw(gl, matrixManager);
			}
			matrixManager.pop();
		}
	}
	
}
