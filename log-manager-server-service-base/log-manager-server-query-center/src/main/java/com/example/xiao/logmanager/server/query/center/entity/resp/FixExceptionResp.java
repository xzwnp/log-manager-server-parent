package com.example.xiao.logmanager.server.query.center.entity.resp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FixExceptionResp {
    private String exceptionMessage;
    private String suggestionMessage;
}
