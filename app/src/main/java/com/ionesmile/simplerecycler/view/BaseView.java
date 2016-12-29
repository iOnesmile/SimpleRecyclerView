package com.ionesmile.simplerecycler.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.ImageViewTarget;
import com.ionesmile.simplerecycler.R;

/**
 * Created by Administrator on 2016/9/2 0002.
 */
public abstract class BaseView extends FrameLayout implements View.OnClickListener {

    public BaseView(Context context) {
        super(context);
        initBase();
    }

    public BaseView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initBase();
    }

    public BaseView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initBase();
    }

    private void initBase() {
        LayoutInflater.from(getContext()).inflate(getLayoutId(), this);
        initView();
        initData();
        initListener();
    }

    protected abstract int getLayoutId();

    protected abstract void initView();

    protected abstract void initData();

    protected abstract void initListener();

    @Override
    public void onClick(View view) {

    }

    public static boolean isEmpty(CharSequence text) {
        return TextUtils.isEmpty(text);
    }

    public static boolean isNotEmptySetText(TextView textView, CharSequence text) {
        if (!isEmpty(text)) {
            textView.setText(text);
            return true;
        } else {
            textView.setText(" ");
        }
        return false;
    }

    public static void isNotEmptyLoadImageView(final ImageView imageView, String iconPath, final OnLoadedImageCallback callback) {
        if (!isEmpty(iconPath)){
            Glide.with(imageView.getContext())
                    .load(iconPath)
                    .placeholder(R.mipmap.loading_image)
                    .crossFade()
                    .into(new ImageViewTarget<GlideDrawable>(imageView) {
                        @Override
                        public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                            super.onResourceReady(resource, glideAnimation);
                            imageView.setImageDrawable(resource);
                            if (callback != null) {
                                callback.onLoadedImage(resource);
                            }
                        }

                        @Override
                        protected void setResource(GlideDrawable resource) {

                        }
                    });
        } else {
            imageView.setImageResource(R.mipmap.loading_image);
        }
    }

    public static void isNotEmptyLoadImageView(ImageView imageView, String iconPath) {
        isNotEmptyLoadImageView(imageView, iconPath, null);
    }

    public interface OnLoadedImageCallback {
        void onLoadedImage(Drawable drawable);
    }
}
