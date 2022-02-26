package com.imooc.ad.advice;

import com.imooc.ad.exception.AdException;
import com.imooc.ad.vo.CommonResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionAdvice {

    // 一旦我们throw出AdException,就会被这个管理器捕获，它会封装一个异常的统一处理
    // 只处理AdException这种异常
    @ExceptionHandler(value = AdException.class)
    public CommonResponse<String> handlerAdException(HttpServletRequest req, AdException ex){
        CommonResponse<String> response = new CommonResponse<>(-1, "bussiness error");
        response.setData(ex.getMessage());
        return response;
    }

    /**
     * 可以扩展多个ExceptionHandler(value= xxx)，这样就可以处理多个异常了
     */
}
