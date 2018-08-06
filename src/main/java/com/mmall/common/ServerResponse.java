package com.mmall.common;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.io.Serializable;

/**
 * 通用的用泛型构造的服务端响应对象
 * Created by zfx on 2018/7/11.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
//保证序列化json的时候，如果是null的对象，key也会消失
public class ServerResponse<T> implements Serializable {
    private int status;
    private String msg;
    private T data;

    private  ServerResponse(int status){
        this.status = status;
    }
    private ServerResponse(int status , String msg){
        this.status = status;
        this.msg = msg;
    }
    private ServerResponse(int status, T data){
        this.status = status;
        this.data = data;
    }
    private ServerResponse(int status , String msg , T data){
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    @JsonIgnore
    //使之不在json序列化结果当中
    public boolean isSuccess(){
        return this.status == ResponseCode.SUCCESS.getCode();
    }
    public int getStatus(){
        return status;
    }
    public String getMsg(){
        return msg;
    }
    public T getData(){
        return data;
    }

    public static <T> ServerResponse<T> createdBySuccess(){
        return new ServerResponse<T>(ResponseCode.SUCCESS.getCode());
    }
    public static <T> ServerResponse<T> createdBySuccessMsg(String msg){
        return new ServerResponse<T>(ResponseCode.SUCCESS.getCode(),msg);
    }
    public static <T> ServerResponse<T> createdBySuccess(T date){
        return new ServerResponse<T>(ResponseCode.SUCCESS.getCode(),date);
    }
    public static <T> ServerResponse<T> createdBySuccess(String msg ,T date){
        return new ServerResponse<T>(ResponseCode.SUCCESS.getCode(),msg,date);
    }

    public static <T> ServerResponse<T> createdByError(){
        return new ServerResponse<T>(ResponseCode.ERROR.getCode(),ResponseCode.ERROR.getDesc());
    }
    public static <T> ServerResponse<T> createdByErrorMsg(String msg){
        return new ServerResponse<T>(ResponseCode.ERROR.getCode(),msg);
    }
    public static <T> ServerResponse<T> createdByErrorCodeMsg(int codeError , String msg){
        return new ServerResponse<T> (codeError,msg);
    }
}
