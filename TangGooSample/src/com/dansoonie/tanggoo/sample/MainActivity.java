package com.dansoonie.tanggoo.sample;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

import com.dansoonie.tanggoo.core.TGCamera;
import com.dansoonie.tanggoo.core.TGModel;
import com.dansoonie.tanggoo.core.TGView;
import com.dansoonie.tanggoo.core.TGWorld;
import com.dansoonie.tanggoo.core.model.TGPanel;

public class MainActivity extends Activity {

	private TGView mTGView;
	private TGWorld mWorld;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mTGView = new TGView(this);
		setContentView(mTGView);
		mWorld = mTGView.getWorld();
		TGCamera camera = mWorld.getCamera();
		camera.setCameraView(2, 10, 0.5f, 2.0f);
		camera.setTranslate(0, 0, camera.getFocalDisatance());
		mTGView.show();
		TGModel model = new TGPanel(1.0f, 1.0f);
		model.translate(0, 0, 0.0f);
		mWorld.put(model);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onPause() {
		super.onPause();
		mTGView.onPause();
	}
	
	@Override
	public void onResume() {
		super.onResume();
		mTGView.onResume();
	}
}
