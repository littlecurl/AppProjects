package edu.heuet.android.logindemo.response;
/* 此类是对Controller中方法返回值的通用封装 */
public class CommonReturnType {
    // status：
    // success表示成功，返回data存储viewobject
    // fail表示失败，返回data存储通用的错误码格式
    private String status;
    // data: 正确信息viewobject或异常信息通用错误码或空
    private Object data;

    /*
     正常的流程返回值都是正确的，
     因此只需要传进viewobject对象即可，默认status为success

     对于异常流程，返回值为null
     在Controller层进行拦截并处理
     */
    public static CommonReturnType create(Object result){
        return CommonReturnType.create(result,"success");
    }
    public static CommonReturnType create(Object result, String status){
        CommonReturnType type = new CommonReturnType();
        type.setData(result);
        type.setStatus(status);
        return type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
