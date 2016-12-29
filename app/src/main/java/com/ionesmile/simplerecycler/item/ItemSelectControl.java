package com.ionesmile.simplerecycler.item;

/**
 * Created by iOnesmile on 2016/12/21 0021.
 */
public interface ItemSelectControl {

    void cancelItemSelect(int position);

    void setItemSelected(int position);

    boolean isItemSelected(int position);
}
