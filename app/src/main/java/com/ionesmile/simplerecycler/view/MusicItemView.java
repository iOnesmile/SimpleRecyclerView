package com.ionesmile.simplerecycler.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ionesmile.simplerecycler.R;
import com.ionesmile.simplerecycler.item.IMusicItemViewRender;
import com.ionesmile.simplerecycler.item.ItemClick;
import com.ionesmile.simplerecycler.item.ItemRender;
import com.ionesmile.simplerecycler.item.ItemSelect;
import com.ionesmile.simplerecycler.manager.MusicDao;


/**
 * Created by iOnesmile on 2016/12/15 0015.
 */
public class MusicItemView extends BaseView implements ItemRender<IMusicItemViewRender>, ItemClick, ItemSelect {

    private View viewPlayTag;
    private ImageView ivMainImage;
    private TextView tvTitle, tvDescription;
    private ImageView ivOpt;
    private ItemClickListener itemClickListener;
    private int clickPosition;
    private MusicDao musicDao;

    public MusicItemView(Context context) {
        super(context);
    }

    public MusicItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MusicItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.item_view_music_common;
    }

    @Override
    protected void initView() {
        musicDao = MusicDao.getInstance();
        viewPlayTag = findViewById(R.id.view_play_tag);
        ivMainImage = (ImageView) findViewById(R.id.iv_main_image);
        tvTitle = (TextView) findViewById(R.id.tv_chip_title);
        tvDescription = (TextView) findViewById(R.id.tv_chip_description);
        ivOpt = (ImageView) findViewById(R.id.iv_chip_opt);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        ivOpt.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()){
            case R.id.iv_chip_opt:
                if (itemClickListener != null){
                    ivOpt.setImageResource(R.mipmap.btn_music_download_checked);
                    itemClickListener.onItemContentClick(view, clickPosition);
                }
                break;
        }
    }

    @Override
    public void renderItem(int position, IMusicItemViewRender data) {
        isNotEmptySetText(tvTitle, data.getItemTitle());
        isNotEmptySetText(tvDescription, data.getItemDescription());

        isNotEmptyLoadImageView(ivMainImage, data.getItemImagePath());

        boolean isDownloaded = musicDao.exist(data.getItemDownloadMusicId());
        if (isDownloaded) {
            ivOpt.setImageResource(R.mipmap.btn_music_download_checked);
        } else {
            ivOpt.setImageResource(R.mipmap.btn_music_download_normal);
        }
    }

    @Override
    public void setItemClick(ItemClickListener itemClickListener, int position) {
        this.itemClickListener = itemClickListener;
        this.clickPosition = position;
    }

    @Override
    public void onSelected(int position) {
        viewPlayTag.setVisibility(View.VISIBLE);
    }

    @Override
    public void onUnselected(int position) {
        viewPlayTag.setVisibility(View.INVISIBLE);
    }
}
