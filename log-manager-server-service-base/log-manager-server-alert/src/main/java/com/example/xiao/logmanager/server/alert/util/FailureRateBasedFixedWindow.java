package com.example.xiao.logmanager.server.alert.util;

import java.util.concurrent.TimeUnit;

/**
 * 基于失败率计数的固定窗口算法
 */
public class FailureRateBasedFixedWindow {
    /**
     * 访问时间差
     */
    private final long cycleMillis;

    /**
     * 计数窗口开始时间
     */
    private long windowStartTime;
    /**
     * 失败率
     */
    private final double failureRate;

    /**
     * 请求总数,等于请求成功数+请求失败数
     */
    private int totalCount;
    /**
     * 失败请求计数器
     */
    private int failureCount;

    /**
     * @param cycle       计时周期
     * @param timeUnit    时间单位
     * @param failureRate 限流百分比
     */
    public FailureRateBasedFixedWindow(int cycle, TimeUnit timeUnit, double failureRate) {
        this.cycleMillis = timeUnit.toMillis(cycle);
        this.failureRate = failureRate;
    }

    public synchronized boolean incrFailureAndJudge() {
        long now = System.currentTimeMillis();
        if (now >= windowStartTime + cycleMillis) {
            // 进入新的计时周期 ,该请求作为周期唯一一个请求,失败率为100%,触发报警
            resetWindow();
            return false;
        }
        failureCount++;
        boolean result = (double) failureCount / totalCount >= failureRate;
        if (result) {
            //重置计数
            resetWindow();
        }
        return result;
    }

    private void resetWindow() {
        windowStartTime = 0L;
        failureCount = 0;
        totalCount = 0;
    }

    /**
     * 请求成功时调用此方法,增加成功计数
     */
    public synchronized void incrTotal() {
        long now = System.currentTimeMillis();
        if (now >= windowStartTime + cycleMillis) {
            // 进入新的计时周期
            windowStartTime = now;
            failureCount = 0;
            totalCount = 1;
        } else {
            this.totalCount++;
        }
    }
}
