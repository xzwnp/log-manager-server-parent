package com.example.xiao.logmanager.server.common.entity.req;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * com.example.xiao.logmanager.server.common.entity.req
 *
 * @author xzwnp
 * 2024/1/26
 * 14:01
 */
@Data
public class PageRequest {
    @NotNull
    @Min(1)
    protected Long current = 1L;

    @NotNull
    @Min(1)
    @Max(100)
    protected Long size = 10L;
}
