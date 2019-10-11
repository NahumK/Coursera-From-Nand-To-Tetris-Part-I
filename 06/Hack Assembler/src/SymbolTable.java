
import java.util.Hashtable;
import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class SymbolTable 
{

	private Hashtable<String, Integer> symbolTable;
	private String filePathName;
	private String tempFilePathName;
	
	public SymbolTable(String filePathName)
	{
		symbolTable = new Hashtable<String, Integer>();
		
		// Initialization of the symbol table with predefined symbols
		
		String[] predifinedSymbols = {"SP", "LCL", "ARG", "THIS", "THAT"};

		for(int index = 0;index <= 4; index++)
			symbolTable.put(predifinedSymbols[index], index);
		
		
		for(int index = 0; index <= 15; index++)
		{
			String symbol = "R" + index;
			symbolTable.put(symbol, index);
		}
		
		symbolTable.put("SCREEN", 16384);
		symbolTable.put("KBD", 24576);
		
		//Initialize the filepath name 
		this.filePathName = filePathName;
		
		//Execute the first pass
		firstPass();
	}
	
	private void firstPass() 
	{
		try
		{
			File inputFile = new File(filePathName);
			Scanner scanner = new Scanner(inputFile);
			
			//Temporary file without white spaces and comments
			int endPos = filePathName.indexOf(".asm");
			tempFilePathName = filePathName.substring(0, endPos) + "_temp.asm";
			
			FileWriter outputFile = new FileWriter(tempFilePathName);
			
			//Scan the entire document looking for labels "(xxx) and remove all white space and comments rewrite it in the temp file
			int indexCommand = 0;
			
			while(scanner.hasNextLine())
			{
				String line = scanner.nextLine().replaceAll("\\s", "");
				
				int commentPos = line.indexOf("//");
				
				if((!line.equals("")) && (commentPos != 0))
				{
					if(commentPos > 0)
						line = line.substring(0, commentPos);
					//Put every label found in the table with the address being the number of the next instruction
					if(line.indexOf('(') == 0)
					{
						int endBracketPos = line.indexOf(')');
						String label = line.substring(1, endBracketPos);
						addEntry(label, indexCommand);
					}
					else
					{
						outputFile.write(line + "\r\n");
						indexCommand++;
					}
					
				}
			}
			
			scanner.close();
			outputFile.close();
		}
		catch(IOException exception)
		{
			System.out.println(exception.getMessage());
		}
	}
	
	public void addEntry(String symbol, int address)
	{
		symbolTable.put(symbol, address);
	}
	
	public boolean contains(String symbol)
	{
		return symbolTable.containsKey(symbol);
	}
	
	public int getAddress(String symbol)
	{
		return symbolTable.get(symbol);
	}
	
	public String getTempFilePathName()
	{
		return tempFilePathName;
	}
	
	public void printTable()
	{
		for(String symbol : symbolTable.keySet())
			System.out.println(symbol + " : " + symbolTable.get(symbol));
	}
	
}
