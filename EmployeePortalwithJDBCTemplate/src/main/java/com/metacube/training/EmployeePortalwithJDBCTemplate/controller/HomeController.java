package com.metacube.training.EmployeePortalwithJDBCTemplate.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeController
{
     /**
     * goto home page
     * @param model
     * @return
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String welcome(ModelMap model)
    {
        return "home";
    }
 } 