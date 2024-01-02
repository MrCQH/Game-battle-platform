package com.kob.backend.common;

/**
 *  分页查询统一返回对象
 */
public class EasyResult {
    private Integer code;
    private String msg;
    private Long count;
    private Object data;

    public static EasyResult buildEasyResultError(String msg) {
        return new EasyResult(1, msg);
    }

    public static EasyResult buildEasyResultError(Exception e){
        return new EasyResult(1, e.getMessage());
    }

    public static EasyResult buildEasyResultSuccess(Object data) {
        return new EasyResult(0, "", 1L, data);
    }

    public static EasyResult buildEasyResultSuccess(Long count, Object data) {
        return new EasyResult(0, "", count, data);
    }

    public EasyResult(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public EasyResult(Integer code, String msg, Long count, Object data) {
        this.code = code;
        this.msg = msg;
        this.count = count;
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "EasyResult{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", count=" + count +
                ", data=" + data +
                '}';
    }
}
