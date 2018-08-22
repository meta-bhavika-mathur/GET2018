package jdbctest;

public class Category
{
    int id;
    String name;
    int subCategoryCount;
    
    // Constructor for Category 
    public Category(int id, String name, int subCategoryCount)
    {
        this.id = id;
        this.name = name;
        this.subCategoryCount = subCategoryCount;
    }
    
    // To get Category Id
    public int getID()
    {
        return id;
    }
    
    // To get Category name
    public String getName()
    {
        return name;
    }
    
    // To get sub category count
    public int getSubCategoryCount()
    {
        return subCategoryCount;
    }
}