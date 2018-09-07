package com.training.metacube;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class CollectionInjectionDemo 
{
    public static void main(String[] args)
    {
        ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
        CollectionClass collections = (CollectionClass)context.getBean("javaCollection");

        collections.getListOfColors();
        collections.getSetOfColors();
        collections.getMapOfColors();
      
     }
}
