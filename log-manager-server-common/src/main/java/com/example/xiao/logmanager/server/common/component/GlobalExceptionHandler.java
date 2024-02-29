package com.example.xiao.logmanager.server.common.component;

import com.example.xiao.logmanager.server.common.entity.resp.R;
import com.example.xiao.logmanager.server.common.enums.ResultCode;
import com.example.xiao.logmanager.server.common.exception.BizException;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * @description: 自定义异常处理
 * @author: DT
 * @date: 2021/4/19 21:51
 * @version: v1.0
 */
@ControllerAdvice
@ConditionalOnClass(WebMvcAutoConfiguration.class)
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 处理自定义的业务异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = BizException.class)
    @ResponseBody
    public R<Void> bizExceptionHandler(BizException e) {
        logger.warn("handle bizException,code:{} message：{}", e.getResultCode().getCode(), e.getMessage());
        return R.error(e.getResultCode(), e.getMessage());
    }


    /**
     * 处理其他异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public R<Void> exceptionHandler(Exception e) {
        logger.error("server internal error", e);
        String message = "系统异常.原因:" + e.getMessage();
        return R.error(ResultCode.ERROR, message);
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        logger.warn("handleHttpRequestMethodNotSupported", ex);
        R<Void> body = R.error(ResultCode.PATH_NOT_EXIST, "请求方式不支持");
        return ResponseEntity.ok().body(body);
    }

    @Override
    protected ResponseEntity<Object> handleMissingPathVariable(MissingPathVariableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        logger.warn("handleMissingPathVariable", ex);
        R<Void> body = R.error(ResultCode.PATH_NOT_EXIST, "缺少请求路径参数");
        return ResponseEntity.ok().body(body);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        logger.warn("handleMissingServletRequestParameter", ex);
        R<Void> body = R.error(ResultCode.PATH_NOT_EXIST, "缺少请求参数");
        return ResponseEntity.ok().body(body);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestPart(MissingServletRequestPartException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        logger.warn("handleMissingServletRequestPart", ex);
        R<Void> body = R.error(ResultCode.PATH_NOT_EXIST, "缺少RequestPart");
        return ResponseEntity.ok().body(body);
    }

    @Override
    protected ResponseEntity<Object> handleServletRequestBindingException(ServletRequestBindingException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        return super.handleServletRequestBindingException(ex, headers, status, request);
    }

    //处理请求参数格式错误 @RequestBody上validate失败后抛出的异常是MethodArgumentNotValidException异常。
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        BindingResult bindingResult = ex.getBindingResult();
        StringBuilder sb = new StringBuilder("参数错误:");
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            sb.append("[").append(fieldError.getField()).append("]").append(fieldError.getDefaultMessage()).append(",");
        }
        String msg = sb.toString();
        logger.warn(msg);
        return ResponseEntity.ok().body(R.error(ResultCode.PARAM_ERROR, msg));
    }


    //处理请求参数格式错误 @RequestParam上validate失败后抛出的异常是javax.validation.ConstraintViolationException
    @ExceptionHandler(value = ConstraintViolationException.class)
    public R<Void> handleConstraintViolationException(ConstraintViolationException ex) {
        return R.error(ResultCode.PARAM_ERROR, ex.getMessage());
    }

}
