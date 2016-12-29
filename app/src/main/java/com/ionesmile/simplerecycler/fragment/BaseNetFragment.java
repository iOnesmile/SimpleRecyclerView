package com.ionesmile.simplerecycler.fragment;

import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.ionesmile.simplerecycler.R;
import com.ionesmile.simplerecycler.adapter.BaseRecyclerAdapter;
import com.ionesmile.simplerecycler.adapter.CommonFooterRecyclerAdapter;
import com.ionesmile.simplerecycler.adapter.SimpleRecyclerAdapter;
import com.ionesmile.simplerecycler.item.ItemClick;
import com.ionesmile.simplerecycler.utils.PixelUtil;
import com.ionesmile.simplerecycler.view.Footer4List;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by iOnesmile on 2016/12/16 0016.
 * <br/> 网络请求的基础 Fragment，实现了滑到底部加载更多
 */
public abstract class BaseNetFragment extends BaseFragment
        implements BaseRecyclerAdapter.OnItemClickListener, SimpleRecyclerAdapter.IViewHolderCallback, ItemClick.ItemClickListener {

    private static final String TAG = BaseNetFragment.class.getSimpleName();
    protected RecyclerView mRecyclerView;
    protected CommonFooterRecyclerAdapter recyclerAdapter;
    protected List adapterDatas;
    protected List lastDatas;
    protected Footer4List footView;
    protected int mDivLineWidth;
    protected PageBean mPageBean;
    protected int maxShowItemCount = Integer.MAX_VALUE;

    @Override
    protected void initBase() {
        adapterDatas = new ArrayList<>();
        mDivLineWidth = PixelUtil.dp2px(1, getContext());
        mPageBean = new PageBean();
    }

    @Override
    protected void initView() {
        footView = new Footer4List(getActivity());
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
    }

    @Override
    protected void initData() {
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);
        recyclerAdapter = new CommonFooterRecyclerAdapter<>(this, adapterDatas).setOnItemClickListener(this).setItemClickListener(this);
        mRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {

            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                setItemDividerLine(outRect, view, parent, state);
            }
        });
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(newState == RecyclerView.SCROLL_STATE_IDLE){
                    int lastVisiblePosition = layoutManager.findLastVisibleItemPosition();
                    Log.v(TAG, "onScrollStateChanged() lastVisiblePosition = " +  lastVisiblePosition + "   layoutManager.getItemCount() " + layoutManager.getItemCount());
                    if(lastVisiblePosition >= layoutManager.getItemCount() - 1){
                        Log.d(TAG, "onScrollStateChanged() isLoading = " +  mPageBean.isLoading + "   pageIndex = " + mPageBean.pageIndex + "   totalPage = " + mPageBean.totalPage + "   size = " + adapterDatas.size() + "   max = " + maxShowItemCount);
                        // 加载更多
                        if (!mPageBean.isLoading && (mPageBean.pageIndex < mPageBean.totalPage) && (adapterDatas.size() < maxShowItemCount)){
                            mPageBean.isLoading = true;
                            showLoadingView();
                            onLoadingData(mPageBean.pageIndex + 1);
                        }
                    }
                }
            }
        });
        mRecyclerView.setAdapter(recyclerAdapter);
    }

    @Override
    protected void initListener() {

    }

    /** 设置分割线 **/
    protected void setItemDividerLine(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.top = mDivLineWidth;
    }

    @Override
    public void onRecyclerBindViewHolder(RecyclerView.ViewHolder holder, final int position){
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        holder.itemView.setLayoutParams(params);
    }

    @Override
    public void onItemClick(int position, Object data) {
    }

    @Override
    public void onItemContentClick(View view, int position) {

    }

    /**
     * 加载数据
     * @param pageIndex     要加载的页面
     */
    public abstract void onLoadingData(int pageIndex);

    /**
     * 显示正在加载的 Footer
     */
    protected void showLoadingView() {
        recyclerAdapter.setFooterView(footView);
        footView.showProgressBar();
        footView.setText(R.string.text_loading);
    }

    /**
     * 显示没有结果
     */
    protected void onNoResult() {
        if (footView != null) {
            footView.hideProgressBar();
            footView.setText(R.string.text_no_search_result);
        }
    }

    /**
     * 在加载成功时
     * @param list              本次加载的数据List
     * @param currentPageIndex  本次加载的页面
     * @param totalPage         总页面数
     */
    protected void onLoadSuccess(List list, int currentPageIndex, int totalPage) {
        mPageBean.isLoading = false;
        if (list != null && list.size() > 0){
            lastDatas = list;
            adapterDatas.addAll(list);
            // 当大于最大显示数量时，截取
            if (adapterDatas.size() > maxShowItemCount){
                adapterDatas = adapterDatas.subList(0, maxShowItemCount);
            }
            recyclerAdapter.notifyDataChanged(adapterDatas);
            mPageBean.pageIndex = currentPageIndex;
            mPageBean.totalPage = totalPage;
            if (footView != null) {
                recyclerAdapter.setFooterView(null);
            }
        } else {
            onNoResult();
        }
    }

    /**
     * 在加载失败
     */
    protected void onLoadFailure() {
        mPageBean.isLoading = false;
        if (footView != null) {
            footView.hideProgressBar();
            footView.setText(R.string.text_loading_failure);
        }
    }

    /**
     * 加载第一页
     */
    protected void loadFirstPage() {
        mPageBean.isLoading = true;
        mPageBean.totalPage = 1;
        mPageBean.pageIndex = 1;
        adapterDatas.clear();
        showLoadingView();
        recyclerAdapter.notifyDataChanged(adapterDatas);
        onLoadingData(mPageBean.pageIndex);
    }

    /**
     * 加载页面的Bean
     */
    public class PageBean {
        /** 是否加载中 **/
        public boolean isLoading = false;
        /** 当前加载的页面数 **/
        public int pageIndex = 1;
        /** 总页面数 **/
        public int totalPage = 1;
    }
}
