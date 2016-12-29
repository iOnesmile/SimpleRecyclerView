package com.ionesmile.simplerecycler.fragment;

import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import com.ionesmile.simplerecycler.R;
import com.ionesmile.simplerecycler.bean.Music;
import com.ionesmile.simplerecycler.manager.MusicDao;
import com.ionesmile.simplerecycler.manager.SimulationData;
import com.ionesmile.simplerecycler.view.MusicItemView;

import java.util.List;

/**
 * Created by iOnesmile on 2016/12/28 0028.
 */
public class MusicListFragment extends BaseNetFragment {

    @Override
    protected void initData() {
        super.initData();
        loadFirstPage();
    }

    @Override
    public void onLoadingData(final int pageIndex) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mPageBean.isLoading = false;
                List newList = SimulationData.getPageData(pageIndex);
                onLoadSuccess(newList, pageIndex, SimulationData.getTotalPageCount());
            }
        }, 2000);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_base_audio_net;
    }

    @Override
    public View getRecyclerItemView() {
        return new MusicItemView(getContext());
    }

    @Override
    public void onItemClick(int position, Object data) {
        super.onItemClick(position, data);
        Toast.makeText(getContext(), "播放 " + ((Music) adapterDatas.get(position)).getAudioName(), Toast.LENGTH_SHORT).show();
        recyclerAdapter.setSingleSelected(position);
    }

    @Override
    public void onItemContentClick(View view, int position) {
        super.onItemContentClick(view, position);
        if (view.getId() == R.id.iv_chip_opt) {
            Music music = (Music) adapterDatas.get(position);
            if (!MusicDao.getInstance().exist(music.getMusicId())) {
                MusicDao.getInstance().add(music.getMusicId());
                Toast.makeText(getContext(), "下载 " + music.getAudioName(), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "已下载 " + music.getAudioName(), Toast.LENGTH_SHORT).show();
            }
        }
    }
}
