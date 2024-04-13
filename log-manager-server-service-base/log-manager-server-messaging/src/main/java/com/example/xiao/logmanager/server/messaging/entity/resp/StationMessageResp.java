package com.example.xiao.logmanager.server.messaging.entity.resp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StationMessageResp {
    private Long id;
    private String avatar = "https://gw.alipayobjects.com/zos/rmsportal/ThXAXghbEsBCCSDihZxY.png";
    private String title;
    private String dateTime;
    private String description;
    private Integer type;
}
