package bowler;

public class Bowler 
{
    String name;
    int quotaOfBalls;
    
    // Constructor for bowler class
    public Bowler()
    {
        name = "";
        quotaOfBalls = 0;
    }
    
    // Parameterized constructor for bowler class
    public Bowler(String name, int quotaOfBalls)
    {
        this.name = name;
        this.quotaOfBalls = quotaOfBalls;
    }
    
    /**
     * To set name of bowler
     * @param name, name of bowler
     */
    public void setName(String name)
    {
        this.name = name;
    }
    
    /**
     * To get name of bowler
     * @return name, name of bowler
     */
    public String getName()
    {
         return name;
    }
    
    /**
     * To set quota of balls of bowler
     * @param quota, quota of bowler
     */
    public void setQuota(int quota)
    {
        quotaOfBalls = quota;
    }
    
    /**
     * To get quota of balls of bowler i.e number of balls assigned to the bowler
     * @return quotaOfBalls, number of balls assigned to bowler
     */
    public int getQuota()
    {
        return quotaOfBalls;
    }
    
    /**
     * To decrement number of balls in the quota of a baller, after he has bowled one time
     */
    public void decrementNumberOfBalls()
    {
        if(quotaOfBalls != 0)
        	quotaOfBalls--;
    }
}