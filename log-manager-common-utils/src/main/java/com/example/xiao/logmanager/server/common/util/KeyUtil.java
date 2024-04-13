package com.example.xiao.logmanager.server.common.util;

import lombok.NonNull;

public class KeyUtil {
    public static String generateAppGroupKey(@NonNull String appName, @NonNull String group) {
        return String.join("::", appName, group);
    }
}
