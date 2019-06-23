package edu.heuet.android.logindemo.controller;

import edu.heuet.android.logindemo.error.BusinessException;
import edu.heuet.android.logindemo.error.EmBusinessError;
import edu.heuet.android.logindemo.response.CommonReturnType;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public class BaseController {

    public static final String CONTENT_TYPE_FORMED = "application/x-www-form-urlencoded";

    // 定义ExceptionHandler解决直接抛到Tomcat容器层而未被Controller吸收的Exception
    /*
        ******************* 对三个注解的解释：*******************
        1、@ExceptionHandler(arg) 参数指明要拦截的异常类型，这里直接拦截Exception父类
        2、@ResponseStatus(HttpStatus.OK) 如果不加这个注解，返回状态码是500，
            加上这个注解后就可以将自定义的错误信息呈现到前端了
        3、@ResponseBody 如果不加这个注解，那么返回值就只是一个Object ，
            呈现给页面的就是 404 ，
            加上这个注解后就会返回Object的JSON序列串
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Object handlerException(HttpServletRequest request, Exception ex){
        /* 返回异常，指定状态为fail，将data设置为异常对象 */
        /*
        CommonReturnType commonReturnType = new CommonReturnType();
        commonReturnType.setStatus("fail");
        commonReturnType.setData(ex);
        return commonReturnType;
        */

        /*
        如果就像上面代码那样，直接把ex设置给data，
        返回的是Exception对象的JSON序列化，存在一些像stackTrace这样的冗余信息，前端还是看不懂的，
        现在就需要我们写的BusinessException出场了，来自定义data内容，只要errorCode和errorMsg即可
         */

        // 将Exception类型的ex强转为子类型BusinessException
        /*
        BusinessException businessException = (BusinessException)ex;

        CommonReturnType commonReturnType = new CommonReturnType();
        commonReturnType.setStatus("fail");

        Map<String, Object> responseData = new HashMap<>();
        responseData.put("errorCode",businessException.getErrorCode());
        responseData.put("errorMsg",businessException.getErrorMsg());

        commonReturnType.setData(responseData);
        return commonReturnType;
        */

        /* 上面代码可以借助CommonReturnType.create(Object result, String status)方法进行重构 */
        /*
        BusinessException businessException = (BusinessException)ex;
        Map<String, Object> responseData = new HashMap<>();
        responseData.put("errorCode",businessException.getErrorCode());
        responseData.put("errorMsg",businessException.getErrorMsg());
        return CommonReturnType.create(responseData,"fail");
        */
        /*
         上面代码只考虑了Exception是BusinessException的情况
         作为一个健壮程序，还有处理其他异常
         */
        Map<String, Object> responseData = new HashMap<>();
        if (ex instanceof BusinessException){
            BusinessException businessException = (BusinessException)ex;
            responseData.put("errorCode",businessException.getErrorCode());
            responseData.put("errorMsg",businessException.getErrorMsg());
        } else {
            responseData.put("errorCode", EmBusinessError.UNKNOWN_ERROR.getErrorCode());
            responseData.put("errorMsg",EmBusinessError.UNKNOWN_ERROR.getErrorMsg());
        }
        return CommonReturnType.create(responseData,"fail");
    }
}
