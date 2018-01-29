package com.etoak.web.controller;

import com.blade.mvc.annotation.GetRoute;
import com.blade.mvc.annotation.Path;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Path("/index")
public class IndexController {

    @GetRoute("index")
    public String index() { //到达首页,首页主要功能是展示,以及提供登陆或注册服务;
        return "index.html";
    }
}
