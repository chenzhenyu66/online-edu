package com.czy1344.eduservice.client;

import com.czy1344.commonutils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 2020/8/9 19:43
 *
 * @author czy1344
 * 说明：
 */
@FeignClient(name = "service-usrv")
@Component
public interface StsClient {
    @GetMapping("/eduusrv/member/register/{day}")
    public Result registerCount(@PathVariable String day);
}
