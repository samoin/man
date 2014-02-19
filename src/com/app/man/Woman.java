package com.app.man;

import java.util.ArrayList;
import java.util.List;

import com.app.adapter.WomanAdapter;
import com.app.model.WomanItemModel;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.Window;
import android.widget.ListView;

public class Woman extends Activity {

	private ListView womanList;
	private WomanAdapter womanAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.woman);

		womanList = (ListView) findViewById(R.id.woman_list);
		womanAdapter = new WomanAdapter(this);
		womanList.setAdapter(womanAdapter);

		initWomanList();
	}

	private void initWomanList() {
		List<WomanItemModel> models = new ArrayList<WomanItemModel>();

		for (int i = 0; i < 21; i++) {
			WomanItemModel model = new WomanItemModel();
			model.setImg(R.drawable.default_img);
			model.setName("×°Å®ÀÉ" + i);
			model.setVote(100 * i);
			models.add(model);
		}

		womanAdapter.setDatas(models);
		womanAdapter.notifyDataSetChanged();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.woman, menu);
		return true;
	}

}
