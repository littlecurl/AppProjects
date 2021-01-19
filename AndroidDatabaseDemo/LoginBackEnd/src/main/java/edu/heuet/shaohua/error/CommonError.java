package edu.heuet.shaohua.error;

public interface CommonError {
    public int getCode();
    public String getMsg();
    public CommonError setMsg(String errorMsg);
}
