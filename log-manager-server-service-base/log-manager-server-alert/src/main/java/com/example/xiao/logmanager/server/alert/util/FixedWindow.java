package com.example.xiao.logmanager.server.alert.util;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class FixedWindow {
    /**
     * 最大访问数量
     */
    private final int limit;
    /**
     * 访问时间差
     */
    private final long cycleMillis;

    /**
     * 计数窗口开始时间
     */
    private long windowStartTime;

    /**
     * 当前计数器
     */
    private AtomicInteger counter;

    /**
     * @param cycle    计时周期
     * @param timeUnit 时间单位
     * @param limit    限制次数
     */
    public FixedWindow(int cycle, TimeUnit timeUnit, int limit) {
        this.limit = limit;
        this.cycleMillis = timeUnit.toMillis(cycle);
    }

    /**
     * @return 是否达到阈值
     */
    public boolean incrAndJudge() {
        long now = System.currentTimeMillis();
        if (now >= windowStartTime + cycleMillis) {
            // 进入新的计时周期
            windowStartTime = now;
            counter = new AtomicInteger(1);
            return true;
        }
        boolean need = counter.addAndGet(1) >= limit;
        if (need) {
            //清空计数,避免重复触发
            counter.set(0);
        }
        return need;
    }
}
