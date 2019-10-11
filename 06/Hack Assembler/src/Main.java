import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Main 
{

	public static void main(String[] args) 
	{
		//Open an "- .asm" file
		JFileChooser chooser = new JFileChooser();
		
		chooser.setDialogTitle("Please select a file");
		chooser.setAcceptAllFileFilterUsed(false);
		
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Hack assembly files", "asm");
		
		chooser.addChoosableFileFilter(filter);
		chooser.showOpenDialog(null);
		
		String filePathName = chooser.getSelectedFile().getAbsolutePath();
		
		//Assembly process
		SymbolTable symbolTable = new SymbolTable(filePathName);
		
		//symbolTable.printTable();
		
		try
		{
			String inputFilePath = symbolTable.getTempFilePathName();
			File inputFile = new File(inputFilePath);
			Scanner scanner = new Scanner(inputFile);
			
			int endPos = filePathName.indexOf(".asm");
			String outputFilePath = filePathName.substring(0, endPos) + ".hack";
			FileWriter outputFile = new FileWriter(outputFilePath);
			
			Code code = new Code();
			int variableAddress = 16;
			
			while(scanner.hasNextLine())
			{
				String command = scanner.nextLine();
				Parser parser = new Parser(command);
				String commandType = parser.commandType();
				String outputLine = "";
				
				if(commandType.equals("A_COMMAND"))
				{
					outputLine += "0";
					
					String symbol = parser.symbol();
					String value = symbol;
					
					if(!code.symbolIsNumeric(symbol))
					{
						if(symbolTable.contains(symbol))
							value = "" + symbolTable.getAddress(symbol);
						else
						{
							value = "" + variableAddress;
							symbolTable.addEntry(symbol, variableAddress);
							variableAddress++;
						}
					}
					
					value = code.valueToBinary(value);
					
					outputLine += value;
					
				}
				else
				{
					outputLine += "111";
					
					String comp = parser.comp();
					String dest = parser.dest();
					String jump = parser.jump();
					
					comp = code.comp(comp);
					dest = code.dest(dest);
					jump = code.jump(jump);
					
					outputLine += comp + dest + jump;
				}
				
				outputFile.write(outputLine + "\r\n");
			}
			
			scanner.close();
			outputFile.close();
			//Delete temp file
			inputFile.delete();
			
		}
		catch(IOException exception)
		{
			System.out.println(exception.getMessage());
		}
		
	}

}
