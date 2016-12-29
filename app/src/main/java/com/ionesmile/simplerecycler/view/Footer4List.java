package com.ionesmile.simplerecycler.view;


import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ionesmile.simplerecycler.R;


public class Footer4List extends FrameLayout {
	private ProgressBar progressBar;
	private TextView titleView;

	public Footer4List(Context context) {
		super(context);
		View view = LayoutInflater.from(context).inflate(
				R.layout.listview_footer, null);
		LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		params.gravity = Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL;
		this.addView(view, params);
		progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
		titleView = (TextView) view.findViewById(R.id.title_view);
	}

	public void hideProgressBar() {
		progressBar.setVisibility(View.GONE);
	}

	public void showProgressBar() {
		progressBar.setVisibility(View.VISIBLE);
	}

	public void setText(String msg) {
		titleView.setText(msg);
	}

	public void setText(int msg) {
		titleView.setText(msg);
	}

	public void show(String msg) {
		progressBar.setVisibility(View.VISIBLE);
		titleView.setText(msg);
	}

	public void show(int msg) {
		progressBar.setVisibility(View.VISIBLE);
		titleView.setText(msg);
	}
}
