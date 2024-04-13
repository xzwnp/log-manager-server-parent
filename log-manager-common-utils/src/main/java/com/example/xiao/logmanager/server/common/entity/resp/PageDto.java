package com.example.xiao.logmanager.server.common.entity.resp;

import com.example.xiao.logmanager.server.common.entity.req.PageRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * com.example.xiao.logmanager.server.common.entity.resp
 *
 * @author xzwnp
 * 2024/1/26
 * 14:02
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PageDto<T> {
    protected Long total;
    protected Long current;
    protected Long size;
    protected List<T> records;

    public PageDto(Long current, Long size) {
        this.current = current;
        this.size = size;
    }

    public PageDto(PageRequest req) {
        this.current = req.getCurrent();
        this.size = req.getSize();
    }


}
