package questionone;

public interface NestedListUsingArrayInterface 
{
	boolean addList(int[] item);
	int getElementAtSpecifiedPosition(int indexOfMainList, int indexOfSubList) throws InvalidIndexException;
	int getSumOfAllIntegers()throws EmptyListException;
	int getLargestValue()throws EmptyListException;
	boolean searchElement(int elementToSearch) throws EmptyListException;

}
