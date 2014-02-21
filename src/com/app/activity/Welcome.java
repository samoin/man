package com.app.activity;

import com.app.man.R;
import com.app.util.DensityUtil;

import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.Window;

public class Welcome extends Activity implements Callback {

	private static final int GO_MAIN = 1;
	private static final long DELAY = 1000;

	private Handler handler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.welcome);
		
		DensityUtil.initWindow(getWindowManager());

		handler = new Handler(this);

		handler.sendEmptyMessageDelayed(GO_MAIN, DELAY);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.welcome, menu);
		return true;
	}

	private void goMain() {
		Intent intent = new Intent(this, Woman.class);
		startActivity(intent);
		finish();
	}

	@Override
	public boolean handleMessage(Message msg) {
		switch (msg.what) {
		case GO_MAIN:
			goMain();
			break;

		default:
			break;
		}

		return true;
	}

}
