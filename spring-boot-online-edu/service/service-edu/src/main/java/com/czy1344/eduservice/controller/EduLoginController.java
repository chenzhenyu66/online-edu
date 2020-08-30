package com.czy1344.eduservice.controller;

import com.czy1344.commonutils.Result;
import org.springframework.web.bind.annotation.*;

/**
 * 2020/7/26 18:37
 *
 * @author czy1344
 * 说明：
 */
@RestController
@RequestMapping("/eduservice")
public class EduLoginController {

    @PostMapping("/user/login")
    public Result login(){
        return Result.success().data("token","admin");
    }
    @GetMapping("/user/info")
    public Result info(){
        return Result.success().data("roles","[admin]").data("name","admin")
                .data("avatar","https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=2805455557,2950883987&fm=26&gp=0.jpg");
    }
}
