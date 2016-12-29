package com.ionesmile.simplerecycler.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseFragment extends Fragment {

	protected View rootView;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initBase();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		rootView = inflater.inflate(getLayoutId(), container, false);
		initView();
		initData();
		initListener();
		return rootView;
	}

	/**
	 * 初始化一些与UI无关的资源
	 */
	protected abstract void initBase();
	
	/**
	 * 布局资源id
	 * @return
	 */
	protected abstract int getLayoutId();
	
	protected abstract void initView();
	/**
	 * 在initView之后调用，初始化数据
	 */
	protected abstract void initData();

	protected abstract void initListener();

	protected View findViewById(int id) {
		return rootView.findViewById(id);
	}
}
