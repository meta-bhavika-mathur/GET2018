package com.metacube.training.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeController
{
     /**
     * To go to home page
     * @param model
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String welcome(ModelMap model)
    {
        return "home";
    }
 } 
