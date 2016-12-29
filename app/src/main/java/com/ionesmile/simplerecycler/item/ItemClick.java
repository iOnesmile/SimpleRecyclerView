package com.ionesmile.simplerecycler.item;

import android.view.View;

/**
 * Created by Onesmile on 2016/12/18.
 */
public interface ItemClick {

    void setItemClick(ItemClickListener itemClickListener, int position);

    interface ItemClickListener {
        /**
         * 在 ItemView 内面的某个 View 点击的回调
         * @param view
         * @param position
         */
        void onItemContentClick(View view, int position);
    }
}
