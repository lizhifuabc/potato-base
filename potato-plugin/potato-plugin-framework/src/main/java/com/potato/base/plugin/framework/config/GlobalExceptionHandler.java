package com.potato.base.plugin.framework.config;

import com.potato.base.plugin.common.dto.Response;
import com.potato.base.plugin.common.exception.CustomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import java.util.Objects;

/**
 * 全局异常处理
 *
 * @author lizhifu
 * @date 2021/12/9
 */
@Slf4j
@ControllerAdvice
@Order(-1)
public class GlobalExceptionHandler {
    private static final String ERR_CODE = String.valueOf(HttpStatus.BAD_REQUEST.value());
    private static final String UN_KNOW_ERR_CODE = String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value());
    /**
     * 自定义的业务异常
     * @param req
     * @param e
     * @return
     */
    @ExceptionHandler(value = CustomException.class)
    @ResponseBody
    public Response BusinessExceptionHandler(HttpServletRequest req, CustomException e) {
        log.error(String.format("CustomException RequestURI::%s", req.getRequestURI()), e);
        return Response.buildFailure(e.getErrCode(), e.getMessage());
    }

    /**
     * 参数类型转换异常
     * @param req
     * @param e
     * @return
     */
    @ExceptionHandler(value = MethodArgumentTypeMismatchException.class)
    @ResponseBody
    public Response MethodArgumentTypeMismatchExceptionHandler(HttpServletRequest req, MethodArgumentTypeMismatchException e) {
        String builder = "参数转换失败，方法：" +
                Objects.requireNonNull(e.getParameter().getMethod()).getName() +
                "，参数：" +
                e.getName() +
                ",信息：" +
                e.getLocalizedMessage();
        log.error("MethodArgumentNotValidException RequestURI:{} msg:{}", req.getRequestURI(), builder);
        return Response.buildFailure(ERR_CODE, "参数" + e.getName() + "类型不正确");
    }

    /**
     * JSON传值出现异常
     * @param req
     * @param e
     * @return
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseBody
    public Response handleMethodArgumentNotValidException(HttpServletRequest req, MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        StringBuilder sb = new StringBuilder();
        sb.append("url=");
        sb.append(req.getRequestURI().replace("/", ""));
        sb.append(",");
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            sb.append("field=");
            sb.append(fieldError.getObjectName());
            sb.append(".");
            sb.append(fieldError.getField());
            sb.append(",error=");
            sb.append(fieldError.getDefaultMessage());
            sb.append(";");
        }
        String msg = sb.toString();
        log.error(String.format("MethodArgumentNotValidException RequestURI:%s msg:%s", req.getRequestURI(), msg), e);
        return Response.buildFailure("400", bindingResult.getFieldError().getDefaultMessage());
    }

    /**
     * 单个参数参数异常
     * @param req
     * @param e
     * @return
     */
    @ExceptionHandler(value = ConstraintViolationException.class)
    @ResponseBody
    public Response handleMethodArgumentNotValidException(HttpServletRequest req, ConstraintViolationException e) {
        log.error(String.format("ConstraintViolationException RequestURI:%s", req.getRequestURI()), e);
        return Response.buildFailure(ERR_CODE, e.getMessage());
    }

    /**
     * 提交FORM参数异常
     * @param req
     * @param e
     * @return
     * @throws BindException
     */
    @ExceptionHandler(value = BindException.class)
    @ResponseBody
    public Response handleBindException(HttpServletRequest req, BindException e) throws BindException {
        // ex.getFieldError():随机返回一个对象属性的异常信息。如果要一次性返回所有对象属性异常信息，则调用ex.getAllErrors()
        FieldError fieldError = e.getFieldError();
        StringBuilder sb = new StringBuilder();
        sb.append(fieldError.getDefaultMessage());
        // 生成返回结果
        log.error("BindException requestURI:{} paramName:{} msg:{}", req.getRequestURI(), e.getObjectName(), fieldError.getDefaultMessage());
        return Response.buildFailure(ERR_CODE, fieldError.getDefaultMessage());
    }

    /**
     * 客户端使用错误的HTTP方法调用rest资源
     * @param req
     * @param e
     * @return
     */
    @ExceptionHandler(value = ServletRequestBindingException.class)
    @ResponseBody
    public Response ServletRequestBindingExceptionHandler(HttpServletRequest req, ServletRequestBindingException e) {
        log.error("ServletRequestBindingException RequestURI:{},{}", req.getRequestURI(), e.getMessage());
        return Response.buildFailure(ERR_CODE, e.getMessage());
    }

    /**
     * 客户端使用错误的Content-Type调用
     * @param req
     * @param e
     * @return
     */
    @ExceptionHandler(value = HttpMediaTypeNotSupportedException.class)
    @ResponseBody
    public Response ServletRequestBindingExceptionHandler(HttpServletRequest req, HttpMediaTypeNotSupportedException e) {
        log.error("HttpMediaTypeNotSupportedException RequestURI:{},{}", req.getRequestURI(), e.getMessage());
        return Response.buildFailure(ERR_CODE,"错误的Content-Type");
    }

    /**
     * 当接收入参为requestBody时传入空值
     * @param req
     * @param e
     * @return
     */
    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    @ResponseBody
    public Response ServletRequestBindingExceptionHandler(HttpServletRequest req, HttpMessageNotReadableException e) {
        log.error("HttpMessageNotReadableException RequestURI:{},{}", req.getRequestURI(), e.getMessage());
        return Response.buildFailure(ERR_CODE,"请求body为空");
    }

    /**
     * 服务不存在
     * @param req
     * @param e
     * @return
     */
    @ExceptionHandler(value = NoHandlerFoundException.class)
    @ResponseBody
    public Response noHandlerFoundException(HttpServletRequest req, NoHandlerFoundException e) {
        log.error("NoHandlerFoundException RequestURI:{},{}", req.getRequestURI(), e.getMessage());
        return Response.buildFailure(String.valueOf(HttpStatus.NOT_FOUND.value()),e.getMessage());
    }
    /**
     * 其他异常记录
     * @param req
     * @param e
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Response ExceptionHandler(HttpServletRequest req, Exception e) {
        log.error(String.format("Exception requestURI:%s", req.getRequestURI()), e);
        return Response.buildFailure(UN_KNOW_ERR_CODE, "服务器内部错误");
    }
}
