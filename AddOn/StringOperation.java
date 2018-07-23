package assignment2;

class StringOperation
{
    /**
     * Find a length of a string.
     * @param string, value to find length requires that it should be not null and can be case-sensitive
     * @return integer named 'length' containing length of input string
     */
    public int getLength(String string)
    {
        int i = 0;
        for(char ch:string.toCharArray())
        {   
            i++ ;
        }
        return i;
    }

    /**
     * Find reverse of a string.
     * @param original, value to reverse requires that it should be not null and can be case-sensitive
     * @return integer 'reverse' containing reversed string of input string
     */
    public String reverseString(String original)
    {
        int len;
        String reverse = "";
        len = getLength(original);
        for(int i = len-1 ; i >= 0 ; i--)
        {
            reverse += original.charAt(i);
        }
        return reverse;
    }

    /**
     * Compare 2 strings.
     * @param first_string, first string to compare requires that it should be not null and can be case-sensitive
     * @param second_string, second string to compare requires that it should be not null and can be case-sensitive
     * @return result, true if first_string = second_string otherwise false
     */
    public int compareStrings(String first_string , String second_string)
    {
        int result = 1;
        int length_of_first = getLength(first_string);
        int length_of_second = getLength(second_string);
        
        if( length_of_first == length_of_second)
        {
            int i = 0;
            while( i <= length_of_first - 1)
            {
                if( first_string.charAt(i) == second_string.charAt(i))
                {
                    i++;
                }
                else
                {
                    result = 0;
                    break;  
                }
            }
        }
        else
            result = 0;
        return result;
    }

    /**
     * Replaces upper case characters with lower case and vice-versa of a string.
     * @param str, value to replace case requires that it should be not null and can be case-sensitive
     * @return new_str string with cases replaced
     */
    public String replaceCase(String str)
    {
        String new_str = "";
        for(char ch:str.toCharArray())
        {   
            if (ch >= 'A' && ch <= 'Z')
                ch = (char) (ch + 32);
            else
                if( ch >= 'a' && ch <= 'z')
                    ch = (char) (ch - 32);
            new_str += ch;      
        }
        return new_str;
    }

    /**
     * Find largest word of a string.
     * @param str, string to search for largest word, requires that it should be not null and can be case-sensitive
     * @return largest requires to be the largest from the last if 2 have equal length
     */
    public String largestWord( String str ) throws NullPointerException
    {
        String max_string = "";
        int max_length = 0;
        int count = 0;
        int lwr = 0;
        int upr = 0;
        int flag = 0;
        for(int i = 0 ; i <= getLength(str) - 1; i++)
        {
            System.out.println("length" + getLength(str));
            if(( str.charAt(i) == ' ') || ( str.charAt(i) == '\n') || ( str.charAt(i) == '\t'))
            {
                flag = 0;
                if(count > max_length)
                {
                    max_length = count;
                    upr = i-1;
                    count = 0;
                }
            }
            else if(flag ==  0)
            {
                flag = 1;
                count++;
                if(count > max_length)
                        {
                    lwr = i;
                }
            }
            else
            {
                count++;
            }
        }
        for(int j = lwr; j <= upr; j++)
        {
            System.out.println("j:" + j);
            max_string += str.charAt(j);
        }
        return max_string;
    }
}
