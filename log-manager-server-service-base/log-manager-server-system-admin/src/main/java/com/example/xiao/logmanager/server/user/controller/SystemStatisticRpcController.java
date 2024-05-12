package com.example.xiao.logmanager.server.user.controller;

import cn.hutool.core.collection.CollectionUtil;
import com.example.xiao.log.annotation.LogRecordTrace;
import com.example.xiao.logmanager.api.feign.SystemAdminFeignClient;
import com.example.xiao.logmanager.api.feign.SystemStatisticFeignClient;
import com.example.xiao.logmanager.api.resp.SystemStatisticInfoResp;
import com.example.xiao.logmanager.api.resp.UserInfoRpcResp;
import com.example.xiao.logmanager.server.common.entity.resp.R;
import com.example.xiao.logmanager.server.common.enums.RoleEnum;
import com.example.xiao.logmanager.server.common.exception.BizException;
import com.example.xiao.logmanager.server.common.exception.NoPermissionException;
import com.example.xiao.logmanager.server.user.service.biz.UserBizService;
import com.example.xiao.logmanager.server.user.service.biz.UserPermissionService;
import com.example.xiao.logmanager.server.user.service.data.SysAppService;
import com.example.xiao.logmanager.server.user.service.data.SysUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("rpc/sys/statistic")
@LogRecordTrace
@RequiredArgsConstructor
public class SystemStatisticRpcController{
	private final SysUserService sysUserService;
	private final SysAppService sysAppService;

	@GetMapping
	public R<SystemStatisticInfoResp> getSystemStatisticInfo() {
		long appCount = sysAppService.count();
		long userCount = sysUserService.count();
		SystemStatisticInfoResp resp = new SystemStatisticInfoResp((int) appCount, (int) userCount);
		return R.success(resp);
	}
}
