package com.wuyiccc.tianxuan.api.config;

import com.wuyiccc.tianxuan.common.exception.CustomException;
import com.wuyiccc.tianxuan.common.result.CommonResult;
import com.wuyiccc.tianxuan.common.result.ResponseStatusEnum;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wuyiccc
 * @date 2023/6/26 21:59
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {


    @ExceptionHandler(CustomException.class)
    public CommonResult<String> returnMyCustomException(CustomException e) {
        return CommonResult.exception(e.getResponseStatusEnum());
    }

    @ExceptionHandler({ExpiredJwtException.class
            , UnsupportedJwtException.class
            , MalformedJwtException.class
            , SignatureException.class
    })
    public CommonResult<String> returnSignatureException(JwtException e) {
        log.error("jwt异常", e);
        return CommonResult.errorCustom(ResponseStatusEnum.JWT_SIGNATURE_ERROR);
    }
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public CommonResult<Map<String, String>> returnNotValidException(MethodArgumentNotValidException e) {
        BindingResult result = e.getBindingResult();
        Map<String, String> errors = getErrors(result);
        return CommonResult.errorData(errors);
    }

    private Map<String, String> getErrors(BindingResult result) {
        Map<String, String> errorMap = new HashMap<>();
        List<FieldError> errorList = result.getFieldErrors();
        for (FieldError error : errorList) {
            // 错误字段名称
            String fieldName = error.getField();
            // 错误信息
            String errMsg = error.getDefaultMessage();
            errorMap.put(fieldName, errMsg);
        }
        return errorMap;
    }


}
