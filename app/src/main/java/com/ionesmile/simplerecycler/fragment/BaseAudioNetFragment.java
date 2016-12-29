package com.ionesmile.simplerecycler.fragment;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ionesmile.simplerecycler.R;
import com.ionesmile.simplerecycler.item.IAudioPlayable;
import com.ionesmile.simplerecycler.manager.MusicDao;
import com.ionesmile.simplerecycler.retrofit.CloudMusicHandle;
import com.ionesmile.simplerecycler.retrofit.ContentTask;
import com.ionesmile.simplerecycler.utils.PixelUtil;
import com.ionesmile.simplerecycler.view.MusicItemView;

import okhttp3.ResponseBody;
import retrofit2.Call;


/**
 * Created by iOnesmile on 2016/12/21 0021.
 *
 * <br/> 基础网络音乐的Fragment，实现了 Item 的点击播放、下载事件
 * <br/> 默认使用 @MusicItemView 作为Item样式，
 *
 * <p/>适配的显示数据必须实现 @IMusicItemViewRender 接口
 * <p/>适配的能播放的数据必须实现 @IAudioPlayable 接口
 */
public abstract class BaseAudioNetFragment extends BaseNetFragment {

    private MusicDao musicDao;

    @Override
    protected void initBase() {
        super.initBase();
        musicDao = MusicDao.getInstance();
        mDivLineWidth = PixelUtil.dp2px(1, getContext());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_base_audio_net;
    }

    @Override
    protected void initData() {
        super.initData();
        loadFirstPage();
    }

    @Override
    protected void setItemDividerLine(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.top = mDivLineWidth;
    }

    @Override
    public View getRecyclerItemView() {
        return new MusicItemView(getContext());
    }

    @Override
    public void onItemClick(int position, Object data) {
        if (data instanceof IAudioPlayable) {
            // 开启播放

        }
    }

    @Override
    public void onItemContentClick(View view, int position) {
        Object data = adapterDatas.get(position);
        if (data instanceof IAudioPlayable) {
            downloadAudio(((IAudioPlayable) data));
        }
    }

    @Override
    public void onLoadingData(final int pageIndex) {
        new ContentTask(getContext()){
            @Override
            public Call<ResponseBody> getCall(CloudMusicHandle handle) {
                return BaseAudioNetFragment.this.getCall(handle, pageIndex);
            }

            @Override
            protected void onRequestSuccess(String content) {
                mPageBean.isLoading = false;
                BaseAudioNetFragment.this.onRequestSuccess(content);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                mPageBean.isLoading = false;
                super.onFailure(call, t);
                BaseAudioNetFragment.this.onRequestFailure();
            }
        }.exec();
    }

    /**
     * 要请求网络的方法
     * @param handle        请求网络的接口
     * @param pageIndex     当前要请求的页面
     * @return
     */
    public abstract Call<ResponseBody> getCall(CloudMusicHandle handle, int pageIndex);

    /**
     * 在请求成功时
     * @param content   内容
     */
    protected abstract void onRequestSuccess(String content);

    /**
     * 在请求失败时
     */
    protected void onRequestFailure() {

    }

    protected void downloadAudio(IAudioPlayable audioPlayable) {

    }
}
