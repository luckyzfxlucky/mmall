package com.mmall.service;

import com.github.pagehelper.PageInfo;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.Shipping;

/**
 * Created by zfx on 2018/9/8.
 */
public interface IShippingService {

    public ServerResponse add (Integer userId , Shipping shipping);

    public ServerResponse<String> delete (Integer userId , Integer shippingId);

    public ServerResponse<String> update (Integer userId , Shipping shipping);

    public ServerResponse<Shipping> select (Integer userId , Integer shippingId);

    public ServerResponse<PageInfo> list(Integer userId , int pageNum , int pageSize);
}
