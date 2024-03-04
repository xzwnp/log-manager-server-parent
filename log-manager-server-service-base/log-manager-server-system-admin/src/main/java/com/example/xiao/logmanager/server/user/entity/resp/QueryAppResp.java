package com.example.xiao.logmanager.server.user.entity.resp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QueryAppResp {
    private Long id;
    private String appName;
    private String description;
    private Set<String> groups;
    private Set<String> admins;
    private Set<String> users;
}
