package com.mmall.service.impl;

import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.common.TokenCache;
import com.mmall.dao.UserMapper;
import com.mmall.pojo.User;
import com.mmall.service.IUserService;
import com.mmall.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.UUID;

/**
 * Created by zfx on 2018/7/11.
 */
@Service("iUserService")
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public ServerResponse<User> login(String username, String password) {
        int checkUsername = userMapper.checkUsername(username);
        if (checkUsername == 0) {
            return ServerResponse.createdByErrorMsg("用户名不存在！");
        }

        // MD5密码
        String passwordMd5 = MD5Util.MD5EncodeUtf8(password);
        User user = userMapper.userLogin(username, passwordMd5);
        if (user == null) {
            return ServerResponse.createdByErrorMsg("密码不正确！");
        }
        user.setPassword(org.apache.commons.lang3.StringUtils.EMPTY);
        return ServerResponse.createdBySuccess("用户登录成功", user);
    }

    @Override
    public ServerResponse<String> register(User user) {
        ServerResponse validResponse = checkValid(user.getUsername(),Const.USERNAME);
        if(!validResponse.isSuccess()){
            return validResponse;
        }
        validResponse = checkValid(user.getEmail(),Const.EMAIL);
        if(!validResponse.isSuccess()){
            return validResponse;
        }
        user.setRole(Const.Role.ROLE_CUSTOMER);
        //MD5加密
        user.setPassword(MD5Util.MD5EncodeUtf8(user.getPassword()));
        int resultCount = userMapper.insert(user);
        if (resultCount == 0) {
            return ServerResponse.createdByErrorMsg("注册失败！");
        }
        return ServerResponse.createdBySuccessMsg("注册成功！");
    }

    @Override
    public ServerResponse<String> checkValid(String str, String type) {
        if (org.apache.commons.lang3.StringUtils.isNotBlank(type)) {
            if (Const.USERNAME.equals(type)) {
                int checkUsername = userMapper.checkUsername(str);
                if (checkUsername > 0) {
                    return ServerResponse.createdByErrorMsg("用户名已存在！");
                }
            }
            if (Const.EMAIL.equals(type)) {
                int checkEmail = userMapper.checkEmail(str);
                if (checkEmail > 0) {
                    return ServerResponse.createdByErrorMsg("email已存在！");
                }
            }
        }else {
            return ServerResponse.createdByErrorMsg("参数错误！");
        }
        return ServerResponse.createdBySuccessMsg("校验成功！");
    }

    @Override
    public ServerResponse<String> forgetGetQuestion(String username){
        ServerResponse validResponse = this.checkValid(username,Const.USERNAME);
        if(validResponse.isSuccess()){
            return ServerResponse.createdByErrorMsg("用户不存在！");
        }
        String question = userMapper.getQuestionByUsername(username);
        if(org.apache.commons.lang3.StringUtils.isNotBlank(question)){
            return ServerResponse.createdBySuccess(question);
        }
        return ServerResponse.createdByErrorMsg("该用户未设置找回密码问题！");
    }

    @Override
    public ServerResponse<String> forgetCheckAnswer(String username,String question,String answer){
        int resultCount = userMapper.checkAnswer(username,question,answer);
        if(resultCount > 0){
            //说明问题和问题的答案是个用户的，并且是正确的
            String forgetToken = UUID.randomUUID().toString();
            TokenCache.setKey(TokenCache.TOKEN_PREFIX + username,forgetToken);
            return ServerResponse.createdBySuccess(forgetToken);
        }
        return ServerResponse.createdByErrorMsg("问题的答案是错误的!");
    }

    @Override
    public ServerResponse<String> forgetResetPassword(String username,String passwordNew,String forgetToken){
        if(org.apache.commons.lang3.StringUtils.isBlank(forgetToken)){
            return ServerResponse.createdByErrorMsg("参数错误，token需要传递!");
        }
        ServerResponse response = this.checkValid(username,Const.USERNAME);
        if(response.isSuccess()){
            return ServerResponse.createdByErrorMsg("用户不存在!");
        }
        String token = TokenCache.getKey(TokenCache.TOKEN_PREFIX +username);
        if(org.apache.commons.lang3.StringUtils.equals(token,forgetToken)){
            String md5Password = MD5Util.MD5EncodeUtf8(passwordNew);
            int resultCount = userMapper.updatePasswordByUsername(username,md5Password);
            if(resultCount > 0){
                return ServerResponse.createdBySuccessMsg("修改密码成功！");
            }
        }else{
            return ServerResponse.createdByErrorMsg("token获取错误，请重新获取重置密码的token!");
        }
        return ServerResponse.createdByErrorMsg("修改密码失败!");
    }

    @Override
    public ServerResponse<String> resetPassword(String passwordNew,String passwordOld,User user){
        //防止横向越权，要校验一下这个用户的旧密码，一定要指定这个用户
        int resultCount = userMapper.checkPassword(passwordOld,user.getId());
        if(resultCount == 0){
            return ServerResponse.createdByErrorMsg("旧密码不正确!");
        }
        user.setPassword(MD5Util.MD5EncodeUtf8(passwordNew));
        resultCount = userMapper.updateByPrimaryKeySelective(user);
        if(resultCount > 0){
            return ServerResponse.createdBySuccessMsg("密码修改成功!");
        }
        return ServerResponse.createdByErrorMsg("密码修改失败!");
    }

    @Override
    public ServerResponse<User> updateInformation(User user){
        //username不能被更新
        //email要进行校验，校验的email是不是已经存在，并且存在的email如果相同的话，不能是我们当前的这个用户
        int resultCount = userMapper.checkEmailByUserId(user.getEmail(),user.getId());
        if(resultCount > 0){
            return ServerResponse.createdByErrorMsg("email已存在，请更换email再尝试!");
        }
        User updateUser = new User();
        updateUser.setId(user.getId());
        updateUser.setPhone(user.getPhone());
        updateUser.setEmail(user.getEmail());
        updateUser.setQuestion(user.getQuestion());
        updateUser.setAnswer(user.getAnswer());
        resultCount = userMapper.updateByPrimaryKeySelective(user);
        if(resultCount > 0){
            return ServerResponse.createdBySuccess("更新个人信息成功!",updateUser);
        }
        return ServerResponse.createdByErrorMsg("更新个人信息失败!");
    }

    @Override
    public ServerResponse<User> getInformation(Integer userId){
        User user = userMapper.selectByPrimaryKey(userId);
        if(user == null){
            return ServerResponse.createdByErrorMsg("找不到当前用户!");
        }
        user.setPassword(org.apache.commons.lang3.StringUtils.EMPTY);
        return ServerResponse.createdBySuccess(user);
    }
}
