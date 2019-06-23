package edu.heuet.android.logindemo.error;
/*
 Enum类也有属性、构造方法和普通方法
 和常规类的区别之处在于
 Enum可以在类内写成下面这种形式
 Enum名称1(构造参数1, 构造参数2),
 Enum名称2(构造参数1, 构造参数2),
 Enum名称3(构造参数1, 构造参数2)
 ;
 */
public enum EmBusinessError implements CommonError{
    /* 通用的错误类型 1开头 */
    PARAMETER_VALIDATION_ERROR(10001,"参数不合法"),
    UNKNOWN_ERROR(10002,"未知错误"),

    /* 用户相关的异常 2开头 */
    USER_NOT_EXIST(20001,"用户不存在"),
    USER_LOGIN_FAIL(20002,"用户手机号或密码不正确")
    ;

    private int errorCode;
    private String errorMsg;

    EmBusinessError(int errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    @Override
    public int getErrorCode() {
        return errorCode;
    }

    @Override
    public String getErrorMsg() {
        return errorMsg;
    }

    @Override
    public CommonError setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
        return this;
    }
}
