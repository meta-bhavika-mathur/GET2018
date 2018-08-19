package virtualcommandprompt;

import java.util.*;

public class VirtualCommandPrompt 
{
	final static String DIRECTORY_EXISTS_ERROR_MESSAGE = "Directory already exist!";
	final static String NAME_NOT_SPECIFIED_ERROR_MESSAGE = "Directory name not specified!";
	static List<Directory> listOfDirectories = new ArrayList<Directory>();
    static String path = ".\\";
    
    /**
     * To display command prompt
     * @throws DirectoryNameNotSpecifiedException, DirectoryExistsException
     */
    public static void displayPrompt() throws DirectoryNameNotSpecifiedException, DirectoryExistsException
    {
        Directory rootDirectory = new Directory("R:");
        listOfDirectories.add(rootDirectory);
        Scanner scanner = new Scanner(System.in);
        System.out.println("--------Welcome to Virtual Command Prompt--------");
        String currentPath = "R:\\";
        Directory currentDirectory = rootDirectory;
        
        while(true)
        {
            System.out.print(currentPath + ">");
            String command = scanner.nextLine();
            String[] commandWords = command.split(" ");
            
            switch(commandWords[0])
            {
            	case "mkdir" : if(commandWords.length == 1)
            				   		throw new DirectoryNameNotSpecifiedException(NAME_NOT_SPECIFIED_ERROR_MESSAGE);
            	
                			   for(Directory subDirectory: currentDirectory.getListOfSubDirectories())
                			   {
                				   if(subDirectory.getName().equals(commandWords[1]))
                					   throw new DirectoryExistsException(DIRECTORY_EXISTS_ERROR_MESSAGE);
                			   }
                			   Directory newDirectory = new Directory(commandWords[1]);
                			   listOfDirectories.add(newDirectory);
                			   currentDirectory.getListOfSubDirectories().add(newDirectory);
                			   break;
                         
                  
            	case "cd"	:  if(isPresent(currentDirectory, commandWords[1]))
                		   	   {
                    	   	       for(Directory directory: listOfDirectories)
                    	   	       {
                    	   	    	   if(directory.getName().equals(commandWords[1]))
                    	   	    	   {
                    	   	    		   currentDirectory = directory;
                    	   	    		   if(currentPath.charAt(currentPath.length() - 1) != '\\')
                    	   	    			   currentPath += "\\";

                    	   	    		   currentPath += currentDirectory.getName();
                    	   	    		   break;
                    	   	    	   }
                    	   	       }
                		   	   }
            				   else
            				  		System.out.println(currentPath + "> No such directory exists");
            				   break;
                              
            	case "bk"	:  String[] directories = currentPath.split("\\\\");
            				   for(Directory directory: listOfDirectories)
            				   {
            					   if(directory.getName().equals(directories[directories.length - 2]))
            						   currentDirectory = directory;
            				   }
          
            				   currentPath = "R:";
            				   for(int i = 1; i < directories.length - 1; i++)
            					   currentPath += "\\" + directories[i];
            				   break;
                     
            	case "ls"   :  for(Directory subDirectory: currentDirectory.getListOfSubDirectories())
            				   {
                			        System.out.println(subDirectory.getDateOfCreation() + "\t" + subDirectory.getName());
            				   }
            				  	System.out.println(currentDirectory.getListOfSubDirectories().size() +  " Folder(s)");                   
            				  	break;
                          
            	case "find"   : path = ".\\";
            				    String directoryPath = findPath(currentDirectory, commandWords[1]);
            				    if(directoryPath != null)
            				    {
            				    	StringBuilder pathToFind = new StringBuilder(directoryPath);
            				    	pathToFind.deleteCharAt(pathToFind.length() - 1);
            				    	System.out.println(pathToFind);
            				    }
            				    else
            				    	System.out.println(currentPath + ">Directory not found");
            				    break;
        
            	case "tree"	  : System.out.println(".");
            					String spacing = "\t";
            			        printTree(currentDirectory, spacing);
            			        break;

            	case "exit"	  : System.exit(0);
            }           
        }    
    }
    
    /**
     * To check if the directory is present in the sub-tree where current directory is root directory
     * @param currentDirectory current directory
     * @param directoryToBeSearched, directory to be searched
     * @return true if directory is present, otherwise false
     */
    public static boolean isPresent(Directory currentDirectory, String directoryToBeSearched)
    {
        boolean found = false;
        
        if(currentDirectory.getName().equals(directoryToBeSearched))
        {
            found = true;
        }
        else
        {
            for(Directory directory: currentDirectory.getListOfSubDirectories())
            {
                    if(isPresent(directory, directoryToBeSearched))
                    {
                        found = true;
                        break;
                    }
            }
            
        }
        return found;
    }
    
    /**
     * To find the path of desired directory from root directory
     * @param currentDirectory current directory
     * @param directoryToBeSearched directory whose path is to be searched
     * @return path to the desired directory, otherwise null
     */
    public static String findPath(Directory currentDirectory, String directoryToBeSearched)
    {
        boolean found = false;
        System.out.println(currentDirectory.getName());
        if(currentDirectory.getName().equals(directoryToBeSearched))
        {
            found = true;
        }
        else
        {
            for(Directory directory: currentDirectory.getListOfSubDirectories())
            {
                path += directory.getName() + "\\";
                
                if(findPath(directory, directoryToBeSearched) != null)
                {
                    found = true;
                    break;
                }
                else
                {
                    String[] directories = path.split("\\\\");
                    path = ".\\";
                    for(int i = 1; i < directories.length - 2; i++)
                    {
                        path += "\\" + directories[i];
                    }
                }
            }           
        }
        
        if(found)
            return path;
        else 
            return null;
    }
    
    /**
     * To print tree with current directory as root directory 
     * @param currentDirectory current directory
     * @param spacing string to provide spacing for sub directories  
     */
    public static void printTree(Directory currentDirectory, String spacing)
    {
        for(Directory subDirectory: currentDirectory.getListOfSubDirectories())
        { 
            System.out.println(spacing + subDirectory.getName());
            printTree(subDirectory, spacing + "\t");
        }       
    }
}