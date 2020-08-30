package com.czy1344.usrvservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.czy1344.commonutils.JwtUtils;
import com.czy1344.commonutils.MD5;
import com.czy1344.servicebase.exception.OnlineEduException;
import com.czy1344.usrvservice.entity.UsrvMember;
import com.czy1344.usrvservice.entity.vo.LoginVo;
import com.czy1344.usrvservice.entity.vo.RegisterVo;
import com.czy1344.usrvservice.mapper.UsrvMemberMapper;
import com.czy1344.usrvservice.service.UsrvMemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author czy1344
 * @since 2020-08-05
 */
@Service
public class UsrvMemberServiceImpl extends ServiceImpl<UsrvMemberMapper, UsrvMember> implements UsrvMemberService {

    @Autowired
    RedisTemplate<String, String> redisTemplate;

    /**
     * 用户注册
     *
     * @param registerVo
     */
    @Override
    public void toRegister(RegisterVo registerVo) {
        String code = registerVo.getCode();
        String memberName = registerVo.getMemberName();
        String phone = registerVo.getPhone();
        String password = registerVo.getPassword();
        // 1.检查信息是否完整
        if (StringUtils.isEmpty(phone) || StringUtils.isEmpty(password)
                || StringUtils.isEmpty(memberName) || StringUtils.isEmpty(code)) {
            throw new OnlineEduException(400, "请检查信息！");
        }
        // 2.检查数据库中是否已有该手机号
        QueryWrapper<UsrvMember> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile", registerVo.getPhone());
        int count = baseMapper.selectCount(wrapper);
        if (count > 0) throw new OnlineEduException(400, "手机号已注册！");

        // 3.检测验证码是否是发送的验证码(先模拟下验证码，太费钱！！)
        String redisCode = redisTemplate.opsForValue().get(phone);
        if (!code.equals(redisCode)) {
            throw new OnlineEduException(400, "验证码错误！");
        }

        // 4.注册，MD5加密密码
        UsrvMember member = new UsrvMember();
        member.setNickname(memberName);
        member.setIsDisabled(false);
        member.setMobile(phone);
        member.setAvatar("https://edu-online1024.oss-cn-beijing.aliyuncs.com/2020/08/05/1f4424d4-19ea-4008-a978-cc3adb772707logo.jpg");
        member.setPassword(MD5.encrypt(password));

        baseMapper.insert(member);
    }

    /**
     * 用户登录
     *
     * @param loginVo
     * @return
     */
    @Override
    public String toLogin(LoginVo loginVo) {
        String password = loginVo.getPassword();
        String phone = loginVo.getPhone();
        // 1.检查登录信息
        if (StringUtils.isEmpty(password) || StringUtils.isEmpty(phone)) {
            throw new OnlineEduException(400, "请检查登录信息！");
        }
        // 2.检查用户名密码是否存在,并用MD5加密检测
        QueryWrapper<UsrvMember> wrapper = new QueryWrapper<>();

        wrapper.eq("mobile", phone);
        wrapper.eq("password", MD5.encrypt(password));
        UsrvMember member = baseMapper.selectOne(wrapper);
        if (member == null) {
            throw new OnlineEduException(400, "用户名或密码错误！");
        }
        // 3.检测一下用户是否被禁用
        if (member.getIsDisabled()){
            throw new OnlineEduException(400, "您的账号已被禁用，请充钱激活 o(∩_∩)o！");
        }

        // 4.登录，返回一个token信息
        String token = JwtUtils.getJwtToken(member.getId(), member.getNickname());

        return token;
    }


    @Override
    public Integer countRegisterByDay(String day) {
        Integer count = baseMapper.countRegisterByDay(day);

        return count;
    }


}
