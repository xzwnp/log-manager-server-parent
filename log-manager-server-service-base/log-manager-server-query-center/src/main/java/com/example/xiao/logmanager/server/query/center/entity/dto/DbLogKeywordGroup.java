package com.example.xiao.logmanager.server.query.center.entity.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DbLogKeywordGroup {
    @NotNull
    private Boolean matchNewColumn;
    @NotBlank
    private String columnName;
    @NotBlank
    private String columnValue;
}
