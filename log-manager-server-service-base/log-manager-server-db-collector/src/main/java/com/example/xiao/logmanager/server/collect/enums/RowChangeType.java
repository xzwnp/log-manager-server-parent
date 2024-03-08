package com.example.xiao.logmanager.server.collect.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RowChangeType {
    INSERT(1, "INSERT"),
    UPDATE(2, "UPDATE"),
    DELETE(3, "DELETE")
    ;
    @JsonValue
    private final int code;

    private final String name;

    //code转枚举类
    public static RowChangeType of(int code) {
        return switch (code) {
            case 1 -> INSERT;
            case 2 -> UPDATE;
            case 3 -> DELETE;
            default -> null;
        };
    }
}
