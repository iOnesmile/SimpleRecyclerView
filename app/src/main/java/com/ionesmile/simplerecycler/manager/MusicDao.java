package com.ionesmile.simplerecycler.manager;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by iOnesmile on 2016/12/28 0028.
 */
public class MusicDao {

    private static MusicDao instance = null;
    private Set<String> downloadedSet;

    private MusicDao() {
        downloadedSet = new HashSet<>();
    }

    public static MusicDao getInstance() {
        if (instance == null) {
            instance = new MusicDao();
        }
        return instance;
    }

    public boolean exist(String musicId) {
        return downloadedSet.contains(musicId);
    }

    public boolean add(String musicId) {
        return downloadedSet.add(musicId);
    }
}
