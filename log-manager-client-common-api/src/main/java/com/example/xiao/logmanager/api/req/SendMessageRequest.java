package com.example.xiao.logmanager.api.req;

import com.example.xiao.logmanager.api.enums.NotificationTypeEnum;
import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SendMessageRequest {
    private List<String> toUsers;
    private String title;
    private String content;
    private List<NotificationTypeEnum> notificationTypes;
}
