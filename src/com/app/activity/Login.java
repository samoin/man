package com.app.activity;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.app.Activity;
import android.os.Bundle;
import android.os.Looper;
import android.os.Message;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.app.common.BaseUtils;
import com.app.common.HttpCallBackHandler;
import com.app.common.HttpRequestUtils;
import com.app.man.R;

public class Login extends Activity {

	private Button loginBt = null;
	private EditText passport;
	private EditText password;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		passport = (EditText) findViewById(R.id.login_passport);
		password = (EditText) findViewById(R.id.login_password);
		loginBt = (Button) findViewById(R.id.login_bt);
		loginBt.setOnClickListener(loginOnclickListener);

	}

	OnClickListener loginOnclickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			MyListViewHttpHandler myListViewHttpHandler = new MyListViewHttpHandler();
			Message msg = myListViewHttpHandler.obtainMessage();
			Bundle bundle = new Bundle();
			bundle.putString(HttpRequestUtils.BUNDLE_KEY_HTTPURL,
					HttpRequestUtils.BASE_HTTP_CONTEXT
							+ "Login.shtml?phoneNumber="
							+ passport.getText().toString() + "&password="
							+ password.getText().toString() + "&platform=1");
			bundle.putBoolean(HttpRequestUtils.BUNDLE_KEY_ISPOST, false);
			msg.setData(bundle);
			msg.sendToTarget();
		}
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	class MyListViewHttpHandler extends HttpCallBackHandler {

		public MyListViewHttpHandler(Looper looper) {
			super(looper);
		}

		public MyListViewHttpHandler() {
		}

		@Override
		public void callAfterResponseStr(String resultStr) {
			System.out.println("handler---->" + Thread.currentThread().getId());
			JSONTokener jsonParser = new JSONTokener(resultStr);
			// 此时还未读取任何json文本，直接读取就是一个JSONObject对象。
			try {
				JSONObject resultObj = (JSONObject) jsonParser.nextValue();
				JSONObject map = (JSONObject) resultObj.get("data");
				Boolean success = resultObj.getBoolean("success");
				Map<String, Object> resultMap = new HashMap<String, Object>();
				if (success) {
					while (map.keys().hasNext()) {
						String key = (String) map.keys().next();
						resultMap.put(key, map.get(key));
					}
					BaseUtils.CUR_USER_MAP = resultMap;
				} else {
					Toast.makeText(Login.this, "网络返回异常", Toast.LENGTH_SHORT)
							.show();
				}
				System.out.println(BaseUtils.CUR_USER_MAP);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

	}

}
