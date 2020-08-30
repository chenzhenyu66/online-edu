package com.czy1344.usrvservice.mapper;

import com.czy1344.usrvservice.entity.UsrvMember;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 会员表 Mapper 接口
 * </p>
 *
 * @author czy1344
 * @since 2020-08-05
 */
public interface UsrvMemberMapper extends BaseMapper<UsrvMember> {

    Integer countRegisterByDay(String day);
}
