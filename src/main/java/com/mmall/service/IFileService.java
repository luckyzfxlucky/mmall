package com.mmall.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * Created by zfx on 2018/8/26.
 */
public interface IFileService {

    public String upload(MultipartFile file , String path);
}
