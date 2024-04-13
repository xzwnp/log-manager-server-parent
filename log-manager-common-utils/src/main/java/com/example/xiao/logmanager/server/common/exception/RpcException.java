package com.example.xiao.logmanager.server.common.exception;

import com.example.xiao.logmanager.server.common.enums.ResultCode;
import com.example.xiao.logmanager.server.common.enums.RoleEnum;
import lombok.Getter;
import lombok.NonNull;

/**
 * com.example.servicebase.exception
 *
 * @author xzwnp
 * 2022/1/27
 * 15:09
 * Steps：
 */
@Getter
public class RpcException extends BizException {
    public RpcException(String message) {
        super(ResultCode.RPC_EXCEPTION, message);
    }

}
