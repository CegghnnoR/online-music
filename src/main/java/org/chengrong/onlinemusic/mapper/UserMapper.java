package org.chengrong.onlinemusic.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.chengrong.onlinemusic.model.User;

@Mapper
public interface UserMapper {
    User login(User loginUser);
    User selectByName(String username);
}
