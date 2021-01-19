package edu.heuet.shaohua.controller;

import edu.heuet.shaohua.error.BusinessException;
import edu.heuet.shaohua.error.EmBusinessError;
import edu.heuet.shaohua.response.CommonReturnType;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;

/**
 * @author littlecurl
 */
public class BaseController {

    public static final String CONTENT_TYPE_FORMED = "application/x-www-form-urlencoded";

    // 定义ExceptionHandler解决直接抛到Tomcat容器层而未被Controller吸收的Exception

    /**
     * ******************* 对三个注解的解释：*******************
     * 1、@ExceptionHandler(arg) 参数指明要拦截的异常类型，这里直接拦截Exception父类
     * 2、@ResponseStatus(HttpStatus.OK) 如果不加这个注解，返回状态码是500，
     *     加上这个注解后就可以将自定义的错误信息呈现到前端了
     * 3、@ResponseBody 如果不加这个注解，那么返回值就只是一个Object ，
     *     呈现给页面的就是 404 ，
     *     加上这个注解后就会返回Object的JSON序列串
     * @return CommonReturnType 通用返回对象
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Object handlerException(HttpServletRequest request, Exception ex){
//        Map<String, Object> responseData = new HashMap<>();
        if (ex instanceof BusinessException){
            BusinessException businessException = (BusinessException)ex;
//            responseData.put("errorCode",businessException.getCode());
//            responseData.put("errorMsg",businessException.getMsg());
            return CommonReturnType.create(businessException.getCode(),businessException.getMsg());
        } else {
//            responseData.put("errorCode", EmBusinessError.UNKNOWN_ERROR.getCode());
//            responseData.put("errorMsg",EmBusinessError.UNKNOWN_ERROR.getMsg());
            return CommonReturnType.create(EmBusinessError.UNKNOWN_ERROR.getCode(),EmBusinessError.UNKNOWN_ERROR.getMsg());
        }

    }
}
