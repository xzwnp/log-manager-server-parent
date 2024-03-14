package com.example.xiao.logmanager.server.alert.util;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class SlidingWindow {
    private final int limit;
    private final long cycleMills;
    private final Deque<Long> queue;

    /**
     * @param cycle    计时周期
     * @param timeUnit 时间单位
     * @param limit    限制次数
     */
    public SlidingWindow(int cycle, TimeUnit timeUnit, int limit) {
        this.limit = limit;
        this.cycleMills = timeUnit.toMillis(cycle);
        this.queue = new ArrayDeque<>(limit);
    }

    /**
     * @return 是否达到阈值
     */
    public synchronized boolean incrAndJudge() {
        // 获取当前时间
        long nowTime = System.currentTimeMillis();
        // 如果队列还没满，说明未达到限制次数，并添加当前时间戳到队列开始位置
        if (queue.size() < limit) {
            queue.offerFirst(nowTime);
            return false;
        }

        // 队列已满（达到限制次数），则获取队列中最早添加的时间戳
        Long farTime = queue.peekLast();
        assert farTime != null;
        // 用当前时间戳 减去 最早添加的时间戳
        if (nowTime - farTime <= cycleMills) {
            // 若结果小于等于timeWindow，则说明在timeWindow内，通过的次数大于count
            // 达到限制次数,并重置计数
            queue.clear();
            return true;
        } else {
            // 若结果大于timeWindow，则说明在timeWindow内，通过的次数小于等于count
            // 允许通过，并删除最早添加的时间戳，将当前时间添加到队列开始位置
            queue.pollLast();
            queue.offerFirst(nowTime);
            return false;
        }
    }
}
