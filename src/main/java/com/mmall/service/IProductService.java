package com.mmall.service;

import com.github.pagehelper.PageInfo;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.Product;
import com.mmall.vo.ProductDetailVo;
import org.springframework.stereotype.Service;

/**
 * Created by zfx on 2018/8/25.
 */
public interface IProductService {

    public ServerResponse saveOrUpdateProduct(Product product);

    public ServerResponse<String> setSaleStatus(Integer productId , Integer status);

    public ServerResponse<ProductDetailVo> manageProoductDetail(Integer productId);

    public ServerResponse<PageInfo> getProductList(int pageNum, int pageSize);

    public ServerResponse<PageInfo> searchProduct(String productName, Integer productId,int pageNum, int pageSize);

    public ServerResponse<ProductDetailVo> prooductDetail(Integer productId);

    public ServerResponse<PageInfo> getProductByKeywordCategory(String keyword , Integer categoryId , int pageNum , int pageSize , String orderBy);
}
