package com.czy1344.usrvservice.controller;

import com.czy1344.commonutils.RandomUtil;
import com.czy1344.commonutils.Result;
import com.czy1344.usrvservice.service.MsmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 2020/8/4 19:26
 *
 * @author czy1344
 * 说明：短信业务
 */
@RestController
@RequestMapping("/eduusrv/msm")
public class MsmController {
    private final MsmService msmService;
    private final RedisTemplate<String,String> redisTemplate;

    @Autowired
    public MsmController(RedisTemplate<String, String> redisTemplate, MsmService msmService) {
        this.redisTemplate = redisTemplate;
        this.msmService = msmService;
    }


    @GetMapping("/send/{phone}")
    public Result sendCode(@PathVariable("phone") String phone){
        // 1.从redis中取值，如果能取到值，我们就返回成功
        // （这段逻辑有些问题，如果，用户没收到短信，我们需要重新发送，而不是去取redis）
        String code = redisTemplate.opsForValue().get(phone);
        if (!StringUtils.isEmpty(code))
            return Result.success();

        // 2.如果我们取不到值，我们就生成code
        code = RandomUtil.getSixBitRandom();
        Map<String,Object> param = new HashMap<>();
        param.put("code",code);
        boolean isSend = msmService.setCode(param,phone);
        // 发送成功，我们将手机号作为key保存到redis中
        if (isSend){
            // 保存5分钟
            redisTemplate.opsForValue().set(phone,code,5, TimeUnit.MINUTES);
            return Result.success();
        }else {
            return Result.error().message("短信发送失败！");
        }
    }

}
