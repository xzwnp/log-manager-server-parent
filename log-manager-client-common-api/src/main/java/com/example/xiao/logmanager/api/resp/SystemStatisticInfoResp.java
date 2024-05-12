package com.example.xiao.logmanager.api.resp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * com.example.xiao.logmanager.api.resp
 *
 * @author xzwnp
 * 2024/4/20
 * 16:06
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SystemStatisticInfoResp {
	private Integer userNum;
	private Integer appNum;
}
