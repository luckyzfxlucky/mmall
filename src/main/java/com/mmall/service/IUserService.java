package com.mmall.service;

import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;

/**
 * Created by zfx on 2018/7/11.
 */
public interface IUserService {
    ServerResponse<User> login(String username, String password);

    public ServerResponse<String> register(User user);

    public ServerResponse<String> checkValid(String str, String type);

    public ServerResponse<String> forgetGetQuestion(String username);

    public ServerResponse<String> forgetCheckAnswer(String username,String question,String answer);

    public ServerResponse<String> forgetResetPassword(String username,String passwordNew,String forgetToken);

    public ServerResponse<String> resetPassword(String passwordNew,String passwordOld,User user);

    public ServerResponse<User> updateInformation(User user);

    public ServerResponse<User> getInformation(Integer userId);

    public ServerResponse checkAdminRole(User user);
}
