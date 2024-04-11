package org.chengrong.onlinemusic.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.chengrong.onlinemusic.mapper.UserMapper;
import org.chengrong.onlinemusic.model.User;
import org.chengrong.onlinemusic.tools.Constant;
import org.chengrong.onlinemusic.tools.ResponseBodyMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @RequestMapping("/login1")
    public ResponseBodyMessage<User> login1(@RequestParam String username, @RequestParam String password,
                                           HttpServletRequest request) {
        User userLogin = new User();
        userLogin.setUsername(username);
        userLogin.setPassword(password);

        User user = userMapper.login(userLogin);
        if (user == null) {
            System.out.println("登录失败");
            return new ResponseBodyMessage<>(-1, "登录失败", userLogin);
        } else {
            request.getSession().setAttribute(Constant.USERINFO_SESSION_KEY, user);
            return new ResponseBodyMessage<>(0, "登录成功", userLogin);
        }
    }


    @RequestMapping("/login")
    public ResponseBodyMessage<User> login(@RequestParam String username, @RequestParam String password,
                                           HttpServletRequest request) {
        User user = userMapper.selectByName(username);

        if (user == null) {
            System.out.println("登录失败");
            return new ResponseBodyMessage<>(-1, "登录失败", user);
        } else {
            boolean flg = bCryptPasswordEncoder.matches(password, user.getPassword());
            if (!flg) {
                return new ResponseBodyMessage<>(-1, "用户名或者密码错误！", user);
            }
            request.getSession().setAttribute(Constant.USERINFO_SESSION_KEY, user);
            return new ResponseBodyMessage<>(0, "登录成功", user);
        }
    }
}
