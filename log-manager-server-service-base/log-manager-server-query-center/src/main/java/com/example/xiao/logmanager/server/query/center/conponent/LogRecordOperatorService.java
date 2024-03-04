package com.example.xiao.logmanager.server.query.center.conponent;

import com.example.xiao.log.entity.Operator;
import com.example.xiao.log.operator.IOperatorGetService;
import com.example.xiao.logmanager.server.common.util.UserThreadLocalUtil;
import com.example.xiao.logmanager.server.common.util.jwt.JwtEntity;
import org.springframework.stereotype.Component;

@Component
public class LogRecordOperatorService implements IOperatorGetService {

    @Override
    public Operator getOperator() {
        JwtEntity operator = UserThreadLocalUtil.getUserInfo();
        return new Operator(operator.getUserId().toString(), operator.getUsername());
    }
}
