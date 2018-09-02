package com.mmall.service;

import com.mmall.common.ServerResponse;
import com.mmall.pojo.Category;

import java.util.List;

/**
 * Created by zfx on 2018/8/6.
 */
public interface ICategoryService {

    public ServerResponse addCategory (String categoryName, Integer parentId);

    public ServerResponse updateCategoryName(Integer categoryId,String categoryName);

    public ServerResponse<List<Category>> getChildrenParallerCategory(Integer categoryId);

    public ServerResponse<List<Integer>> selectCategoryAndChildrenById(Integer categoryId);
}
