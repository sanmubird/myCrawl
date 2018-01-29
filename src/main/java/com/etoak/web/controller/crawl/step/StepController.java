package com.etoak.web.controller.crawl.step;

import com.blade.mvc.annotation.GetRoute;
import com.blade.mvc.annotation.Path;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Path("crawl/step")
public class StepController {

    @GetRoute("/step1")
    public String gotoStep1Page(){
        return "/crawl/step/step1.html";
    }

    @GetRoute("/step2")
    public String gotoStep2Page(){
        return "/crawl/step/step2.html";
    }

    @GetRoute("/step3")
    public String gotoStep3Page(){
        return "/crawl/step/step3.html";
    }

    @GetRoute("/step4")
    public String gotoStep4Page(){
        return "/crawl/step/step4.html";
    }

    @GetRoute("/easytour")
    public String gotoEasyTour(){
        return "/crawl/step/easytour.html";
    }

}
