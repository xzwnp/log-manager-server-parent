package com.example.xiao.logmanager.server.common.exception;

import com.example.xiao.logmanager.server.common.enums.ResultCode;
import lombok.Getter;

/**
 * com.example.servicebase.exception
 *
 * @author xzwnp
 * 2022/1/27
 * 15:09
 * Steps：
 */
@Getter
public class BizException extends RuntimeException {
    protected ResultCode resultCode;

    public BizException(String message) {
        super(message);
        this.resultCode = ResultCode.BIZ_EXCEPTION;
    }

    public BizException(ResultCode resultCode, String message) {
        super(message);
        this.resultCode = resultCode;
    }

    public BizException(ResultCode resultCode) {
        super(resultCode.getMessage());
        this.resultCode = resultCode;
    }

}
