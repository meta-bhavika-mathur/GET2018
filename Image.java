package jdbctest;

public class Image
{
    int product_id;
    String image_link;
    final String alternative_text = "Image not found!";
    
    public Image(int id, String url)
    {
        product_id = id;
        image_link = url;
   }
    
   public int getProductId()
   {
       return product_id;
   }
   
   public String getImageLink()
   {
       return image_link;
   }
   
   public String getAlternativeText()
   {
       return alternative_text;
   }
   
   public void setProductId(int id)
   {
       product_id = id;
   }
   
   public void setImageLink(String link)
   {
       image_link = link;
   }
}
