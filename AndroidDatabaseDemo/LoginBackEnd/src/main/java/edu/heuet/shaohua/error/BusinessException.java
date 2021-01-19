package edu.heuet.shaohua.error;

/*
CommonError通过异常机制来使用
而这个实现方式属于一个设计模式：包装器业务异常类实现
 */
public class BusinessException extends Exception implements CommonError {
    private CommonError commonError;

    /*
      直接接收EmBusinessError的传参用于构造业务异常
      这里参数类型是EmBusinessError的接口类型
      是一种良好设计的体现
      */
    public BusinessException(CommonError commonError){
        /*  这里调用了Exception类的构造方法 */
        super();
        this.commonError = commonError;
    }

    // 接收自定义errMsg的方式构造业务异常
    public BusinessException(CommonError commonError, String errorMsg){
        super();
        this.commonError = commonError;
        this.commonError.setMsg(errorMsg);
    }

    @Override
    public int getCode() {
        return this.commonError.getCode();
    }

    @Override
    public String getMsg() {
        return this.commonError.getMsg();
    }

    @Override
    public CommonError setMsg(String errorMsg) {
        this.commonError.setMsg(errorMsg);
        return this;
    }
}
