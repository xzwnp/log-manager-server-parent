package com.example.xiao.logmanager.server.user.dao;

import com.example.xiao.logmanager.server.user.entity.po.SysUserPo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 系统用户信息表 Mapper 接口
 * </p>
 *
 * @author xiaozhiwei
 * @since 2024-02-24
 */
@Mapper
public interface SysUserDao extends BaseMapper<SysUserPo> {

}
