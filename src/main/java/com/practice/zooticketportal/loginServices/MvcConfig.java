package com.practice.zooticketportal.loginServices;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/userpage").setViewName("userpage");
        registry.addViewController("/adminpage").setViewName("adminpage");
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/adminlogin").setViewName("adminlogin");
        registry.addViewController("/officerpage").setViewName("officerpage");
        registry.addViewController("/establishments").setViewName("establishments");
        registry.addViewController("/createestablishment2").setViewName("createestablishment2");
        registry.addViewController("/adminProfile").setViewName("adminProfile");
    }


}