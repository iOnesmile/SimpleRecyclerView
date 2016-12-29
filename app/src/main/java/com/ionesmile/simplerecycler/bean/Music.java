package com.ionesmile.simplerecycler.bean;

import com.ionesmile.simplerecycler.item.IAudioPlayable;
import com.ionesmile.simplerecycler.item.IMusicItemViewRender;

/**
 * Created by iOnesmile on 2016/12/28 0028.
 */
public class Music implements IMusicItemViewRender, IAudioPlayable {

    private String musicId;
    private String name;
    private String artist;
    private String imageUrl;
    private String path;

    public Music() {
    }

    public Music(String musicId, String name, String artist) {
        this.musicId = musicId;
        this.name = name;
        this.artist = artist;
    }

    public String getMusicId() {
        return musicId;
    }

    public void setMusicId(String musicId) {
        this.musicId = musicId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String getAudioId() {
        return musicId;
    }

    @Override
    public String getAudioName() {
        return name;
    }

    @Override
    public String getAudioArtist() {
        return artist;
    }

    @Override
    public String getAudioUrl() {
        return path;
    }

    @Override
    public String getAudioImagePath() {
        return imageUrl;
    }

    @Override
    public String getItemTitle() {
        return name;
    }

    @Override
    public String getItemDescription() {
        return artist;
    }

    @Override
    public String getItemImagePath() {
        return imageUrl;
    }

    @Override
    public String getItemDownloadMusicId() {
        return musicId;
    }
}
