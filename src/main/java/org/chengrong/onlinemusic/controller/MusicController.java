package org.chengrong.onlinemusic.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.ibatis.binding.BindingException;
import org.chengrong.onlinemusic.mapper.MusicMapper;
import org.chengrong.onlinemusic.model.User;
import org.chengrong.onlinemusic.tools.Constant;
import org.chengrong.onlinemusic.tools.ResponseBodyMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("/music")
public class MusicController {

    @Value("${music.local.path}")
    private String SAVE_PATH;

    @Autowired
    private MusicMapper musicMapper;
    @RequestMapping("/upload")
    public ResponseBodyMessage<Boolean> insertMusic(@RequestParam String singer,
                                                    @RequestParam("filename")MultipartFile file,
                                                    HttpServletRequest request) {
        // 1. 检查是否登录
        HttpSession httpSession = request.getSession(false);
        if (httpSession == null || httpSession.getAttribute(Constant.USERINFO_SESSION_KEY) == null) {
            System.out.println("未登录！");
            return new ResponseBodyMessage<>(-1, "请登录后上传", false);
        }
        // 2. 上传到服务器
        String fileNameAndType = file.getOriginalFilename();
        System.out.println("fileNameAndType: " + fileNameAndType);

        String path = SAVE_PATH + fileNameAndType;
        System.out.println(path);
        File dest = new File(path);
        if (!dest.exists()) {
            dest.mkdir();
        }
        try {
            file.transferTo(dest);
//            return new ResponseBodyMessage<>(0, "上传成功！", true);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseBodyMessage<>(-1, "服务器上传失败！", false);
        }
        // 数据库上传
        // 1. 准备数据 2. 调用insert
        int index = fileNameAndType.lastIndexOf('.');
        String title = fileNameAndType.substring(0, index);

        User user = (User) httpSession.getAttribute(Constant.USERINFO_SESSION_KEY);
        int userid = user.getId();

        String url = "/music/get?path=" + title;

        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        String time = sf.format(new Date());

        try {
            int ret = musicMapper.insert(title, singer, time, url, userid);
            if (ret == 1) {
                return new ResponseBodyMessage<>(0, "数据库上传成功！", true);
            } else {
                return new ResponseBodyMessage<>(-1, "数据库上传失败！", false);
            }
        } catch (BindingException e) {
            dest.delete();
            return new ResponseBodyMessage<>(-1, "数据库上传失败！", false);
        }
    }

    @RequestMapping("/get")
    public ResponseEntity<byte[]> func(String path) {
        File file = new File(SAVE_PATH + "/" + path);
        byte[] a = null;
        try {
            a = Files.readAllBytes(file.toPath());
            if (a == null) {
                return ResponseEntity.badRequest().build();
            }
            return ResponseEntity.ok(a);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.badRequest().build();
//        return ResponseEntity.internalServerError().build();
//        return ResponseEntity.notFound().build();
//        return ResponseEntity.ok(a);
    }

}
