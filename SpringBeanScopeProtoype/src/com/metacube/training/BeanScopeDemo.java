package com.metacube.training;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class BeanScopeDemo
{
    public static void main(String[] args) 
    {
        ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
        
        Person personOne = (Person) context.getBean("personName");
        personOne.setName("Abc");
        System.out.println("Name of first person is :" + personOne.getName());

        Person personTwo = (Person) context.getBean("personName");
        personTwo.setName("Xyz");
        System.out.println("Name of second person is :" + personTwo.getName());
        
        System.out.println("Name of first person is :" + personOne.getName());
     }
}
