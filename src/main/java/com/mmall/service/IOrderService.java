package com.mmall.service;

import com.mmall.common.ServerResponse;

import java.util.Map;

/**
 * Created by zfx on 2018/10/6.
 */
public interface IOrderService {

    public ServerResponse pay(Long orderNo , Integer userId , String path);

    public ServerResponse aliCallback(Map<String,String> params);

    public ServerResponse<Boolean> queryOrderPayStatus(Integer userId , Long orderNo );

}
