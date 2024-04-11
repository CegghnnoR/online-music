package org.chengrong.onlinemusic.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.chengrong.onlinemusic.model.Music;

@Mapper
public interface MusicMapper {
    int insert(String title, String singer, String time, String url, int userid);

    Music findMusicById(int id);

    int deleteMusicById(int musicId);
}
