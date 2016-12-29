package com.ionesmile.simplerecycler.manager;

import com.ionesmile.simplerecycler.bean.Music;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by iOnesmile on 2016/12/29 0029.
 */
public class SimulationData {

    private final static int PAGE_SIZE = 10;
    private static final List<Music> ORIGIN_LIST;
    private static final int TOTAL_COUNT = 55;

    static {
        ORIGIN_LIST = new ArrayList<>();
        for (int i = 1; i <= TOTAL_COUNT; i++) {
            ORIGIN_LIST.add(new Music(String.valueOf(i), "Music" + i, "artist" + i));
        }
    }

    public static List<Music> getPageData(int pageIndex){
        List<Music> musics = ORIGIN_LIST.subList((pageIndex - 1) * PAGE_SIZE, Math.min(pageIndex * PAGE_SIZE, TOTAL_COUNT));
        return musics;
    }

    public static int getTotalPageCount() {
        int totalPage = TOTAL_COUNT / PAGE_SIZE + (TOTAL_COUNT % PAGE_SIZE == 0 ? 0 : 1);
        return totalPage;
    }


}
