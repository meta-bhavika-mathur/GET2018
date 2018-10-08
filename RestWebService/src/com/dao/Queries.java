package com.dao;

public class Queries
{
    public static final String setCategory = "INSERT INTO AdvertisementCategory(category_name) VALUES (?) ;";
    
    public static final String getAllCategories = "Select category_id, category_name FROM AdvertisementCategory;";
    
    public static final String setAdvertisement = "INSERT INTO Advertisement(advertisement_title, advertisement_description, category_id) " + 
                                                  "VALUES (?, ?, ?);";
    
    public static final String getAllAdvertisement = "Select advertisement_id, advertisement_title, advertisement_description, category_id " + 
                                                     "FROM Advertisement;";
    
    public static final String getAdvertisementsByCategoryID = "SELECT advertisement_id, advertisement_title, advertisement_description, category_id " +
                                                                "FROM Advertisement WHERE category_id = ? ;";
    
    public static final String updateCategoryName = "UPDATE AdvertisementCategory SET category_name = ? WHERE category_name = ?"; 
    
    public static final String updateAdvertisementName = "UPDATE Advertisement SET advertisement_title = ? WHERE advertisement_title = ? ";
                                                     
    
    public static final String deleteCategory = "DELETE FROM AdvertisementCategory WHERE category_id = ? ;";
    
    public static final String deleteAdvertisement = "DELETE FROM Advertisement WHERE advertisement_id = ? ;"; 
    
 
}