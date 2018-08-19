package questionone;

public class NestedListUsingArray implements NestedListUsingArrayInterface
{
	private final String ERROR_MESSAGE_INVALID_INDEX = "Invalid Index Specified!";
	private final String ERROR_MESSAGE_EMPTY_LIST ="Empty Nested List!";
	private int[][] nestedArray;
	private int numberOfRows, occupiedRowNumber;
	
	/**
	 *  Parameterized constructor for nested array list with fixed number of rows and variable number of columns
	 *  in each row
	 */
	public NestedListUsingArray(int rows)
	{
		numberOfRows = rows;
		nestedArray = new int[numberOfRows][];
		occupiedRowNumber = 0;
	}

	/**
	 * To add a new list to nested array requires that it should not be null and must contain one or more elements
	 * @param itemList, an array list of integers to be added to the nested list
	 * @return true, if the list is successfully added, otherwise false
	 */
	public boolean addList(int[] itemList) throws NullPointerException
	{
		if((itemList == null)|| (!(occupiedRowNumber <= nestedArray.length - 1) || itemList.length <= 0))	
			return false;
		else
		{
			nestedArray[occupiedRowNumber] = itemList;
			occupiedRowNumber++;
			return true;
		}
	}

	/**
	 * To get an integer stored at given position requires that index of the nested list and sub list
	 * provided must be valid
	 * @param indexOfMainList, index of row of the nested list
	 * @param indexOfSubList, index of column present in row "indexOfMainList" of the nested list
	 */
	public int getElementAtSpecifiedPosition(int indexOfMainList, int indexOfSubList) throws InvalidIndexException
	{
		if(indexOfMainList >= nestedArray.length || indexOfSubList >= nestedArray[ indexOfMainList ].length)		
			throw new InvalidIndexException("ERROR_MESSAGE_INVALID_INDEX");
		return nestedArray[ indexOfMainList ][ indexOfSubList ];
	}

	/**
	 * To get sum of all integers present in the nested list requires that the list should not be empty
	 * @return sum, containing sum of all integers present in nested list
	 */
	@Override
	public int getSumOfAllIntegers() throws EmptyListException
	{
		if(nestedArray[0].length == 0)
			throw new EmptyListException(ERROR_MESSAGE_EMPTY_LIST);
		
		int sum = 0;
		for(int i = 0; i < nestedArray.length; i++)
			for (int j = 0; j < nestedArray[i].length; j++) 
				sum += nestedArray[i][j];
		
		return sum;
	}

	/**
	 * To find largest integer present in the nested list requires that at least one element must be present
	 * in the nested list
	 * @return maximumValue, containing value of maximum integer
	 */
	@Override
	public int getLargestValue() throws EmptyListException
	{
		if(nestedArray[0].length == 0)
			throw new EmptyListException(ERROR_MESSAGE_EMPTY_LIST);
		
		int maximumValue = nestedArray[0][0];
		
		for(int i = 0; i < nestedArray.length; i++)
			for (int j = 0; j < nestedArray[i].length; j++) 
				if(nestedArray[i][j] > maximumValue) 
					maximumValue = nestedArray[i][j];
		
		return maximumValue;	
	}

	/**
	 * To find whether an element is present in the nested list or not, 
	 * requires that at least one element must be present in the nested list
	 * @param elementToSearch, an integer that is to be searched in the nested list
	 * @return true, if element is present, otherwise false
	 */
	@Override
	public boolean searchElement(int elementToSearch) throws EmptyListException
	{
		if(nestedArray[0].length == 0)
			throw new EmptyListException(ERROR_MESSAGE_EMPTY_LIST);
		
		for(int i = 0; i < nestedArray.length; i++)
			for (int j = 0; j < nestedArray[i].length; j++) 
				if(nestedArray[i][j] == elementToSearch) 
					return true;

		return false;
	}
}