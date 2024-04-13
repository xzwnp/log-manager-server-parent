package com.example.xiao.logmanager.api.feign;

import com.example.xiao.logmanager.api.req.SendMessageRequest;
import com.example.xiao.logmanager.server.common.entity.resp.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("log-manager-server-messaging")
public interface MessagingFeignClient {
    @PostMapping("rpc/messaging/send")
    R<Void> sendMessage(@RequestBody SendMessageRequest request);
}
