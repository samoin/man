package com.app.adapter;

import java.util.List;

import com.app.man.R;
import com.app.model.WomanItemModel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class WomanAdapter extends BaseAdapter {

	private List<WomanItemModel> womanDatas;
	private LayoutInflater inflater;

	public WomanAdapter(Context context) {
		inflater = LayoutInflater.from(context);
	}

	public void setDatas(List<WomanItemModel> list) {
		womanDatas = list;
	}

	@Override
	public int getCount() {
		return womanDatas == null ? 0 : womanDatas.size();
	}

	@Override
	public Object getItem(int position) {
		return womanDatas == null ? null : womanDatas.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.woman_item, null);

			holder = new ViewHolder();
			holder.headImg = (ImageView) convertView
					.findViewById(R.id.woman_head_img);
			holder.name = (TextView) convertView.findViewById(R.id.woman_name);
			holder.vote = (TextView) convertView.findViewById(R.id.woman_vote);
			holder.headImg2 = (ImageView) convertView
					.findViewById(R.id.woman_head_img2);
			holder.name2 = (TextView) convertView
					.findViewById(R.id.woman_name2);
			holder.vote2 = (TextView) convertView
					.findViewById(R.id.woman_vote2);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		if (position + 1 >= 5 ) {
			return convertView;
		}
		try {

			WomanItemModel model = (WomanItemModel) getItem(2 * position);
			WomanItemModel model2 = (WomanItemModel) getItem(2 * position + 1);

			if (model != null) {
				holder.headImg.setImageResource(model.getImg());
				holder.name.setText(model.getName());
				holder.vote.setText(model.getVote() + "Ʊ");
			}
			if (model2 != null) {
				holder.headImg2.setImageResource(model2.getImg());
				holder.name2.setText(model2.getName());
				holder.vote2.setText(model2.getVote() + "Ʊ");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return convertView;
	}

	private static class ViewHolder {
		public ImageView headImg;
		public TextView name;
		public TextView vote;
		public ImageView headImg2;
		public TextView name2;
		public TextView vote2;
	}

}
