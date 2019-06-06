package xingyu.lu.springboot.shiro.config;

import org.apache.shiro.ShiroException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import xingyu.lu.springboot.shiro.utils.result.ResultModel;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class RestCtrlAdvice {

    // 捕捉shiro的异常
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(ShiroException.class)
    public ResultModel handleShiroException(ShiroException e) {
        return ResultModel.commonError(e.getMessage());
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UnauthorizedException.class)
    public ResultModel handleUnauthorizedException() {
        return ResultModel.commonError("Un Authorized!");
    }

    // 捕捉其他所有异常
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResultModel handleExceptions(HttpServletRequest request, Throwable ex) {
        return ResultModel.customError(String.valueOf(getStatus(request).value()), ex.getMessage());
    }

    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
    }
}

