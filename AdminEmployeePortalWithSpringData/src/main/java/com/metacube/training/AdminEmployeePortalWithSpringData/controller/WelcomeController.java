package com.metacube.training.AdminEmployeePortalWithSpringData.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class WelcomeController
{

    /**
     * To direct to home page
     * @param model
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String welcome(ModelMap model)
    {
        return "home";
    }
}
