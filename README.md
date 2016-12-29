## RecyclerView 使用的简单封装

本 Demo 主要是对网络请求分页加载数据做的一个简易的封装，实现了如下功能：   

1，RecyclerView 滑动到底部时加载下一页   

2，ItemView 和它内部View的点击事件   

3，Item 的选中

**效果如下：**   

![RecyclerView 运行Demo](/RecyclerView.gif)

**使用方法：**   

1，继承 BaseNetFragment   

2，实现 onLoadingData(int pageIndex)，getRecyclerItemView() 等方法   

3，Item 的点击事件在 onItemClick(int position, Object data) 方法中实现   

4，Item 内面 View 的点击事件在 onItemContentClick(View view, int position) 方法中实现，并且 ItemView 实现 ItemClick 接口

5，单选调用 recyclerAdapter.setSingleSelected(position) 方法，并且 ItemView 实现 ItemSelect 接口



**代码如下：**   

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



**ItemView** 的实现：

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



此 Demo 是对分页加载中的一个提炼，让代码变得简介清晰，同时提高开发效率。由于开发时间比较紧，代码的封装有很多不足的地方，望大家见谅。同时希望大家提出建议和改善的办法，让我们共同维护这个项目。   