package com.ionesmile.simplerecycler.item;

/**
 * Created by iOnesmile on 2016/11/10 0010.
 */
public interface ItemRender<T> {

    void renderItem(int position, T data);
}
