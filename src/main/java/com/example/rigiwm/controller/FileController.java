package com.example.rigiwm.controller;

import com.example.rigiwm.comment.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/common")
public class FileController {

    @Value("${dishPath}")
    private String dishPath;

    @PostMapping("/upload")
    public R<String> fileUp(MultipartFile file){
        // 修改文件名 防止数据库中文件名字段重复
        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        String fileName = UUID.randomUUID() + suffix;
        // 创建文件存放路径
        File dir = new File(dishPath);
        try {
            if (!dir.exists()) dir.mkdirs();
        } catch (Exception e){
            e.printStackTrace();
        }
        try {
            file.transferTo(new File(dishPath + fileName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // 返回文件名
        return R.success(fileName);
    }

    @GetMapping("/download")
    public R<String> fileLoad(HttpServletRequest request, HttpServletResponse response){
        String fileName = request.getParameter("name");
        // 获取文件路径
        String filePath = dishPath + fileName;
        // 下载图片
        FileInputStream fileInputStream = null;
        ServletOutputStream fileOutputStream = null;
        try {
             fileInputStream = new FileInputStream(new File(filePath));
             fileOutputStream = response.getOutputStream();
            byte[] b = new byte[1024];
            int len = 0;
            while ((len = fileInputStream.read(b)) != -1){
                fileOutputStream.write(b, 0, len);
                fileOutputStream.flush();
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {

            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        // 返回给前端
        return R.success(fileName);


    }

}
