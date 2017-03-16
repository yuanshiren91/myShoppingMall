package com.netease.myShoppingMall.base.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 错误页面跳转控制器
 *
 * @author 陈俊良
 */
@Controller
@RequestMapping(value = "/error")
public class ErrorController {

    @RequestMapping(value = "/{errorCode}")
    public ModelAndView error(@PathVariable int errorCode) {
        switch (errorCode) {
            case 404:
                return new ModelAndView("errors/error404");  
            default:
                return new ModelAndView("errors/error404");
        }
    }
}
