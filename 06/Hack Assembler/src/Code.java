import java.util.Hashtable;

public class Code 
{
	private Hashtable<String, String> compTable;
	private Hashtable<String, String> destTable;
	private Hashtable<String, String> jumpTable;
	
	public Code()
	{
		compTable = new Hashtable<String, String>();
		destTable = new Hashtable<String, String>();
		jumpTable = new Hashtable<String, String>();
		initCompTable();
		initDestTable();
		initJumpTable();
	}
	
	public boolean symbolIsNumeric(String symbol)
	{
		try
		{
			Integer.parseInt(symbol);
		}
		catch(NumberFormatException exception)
		{
			return false;
		}
		
		return true;
	}
	
	public String valueToBinary(String value)
	{
		int val = Integer.parseInt(value), lenZero = 0;
		String result = Integer.toBinaryString(val);
		int len = result.length();
		
		if(len < 15)
			lenZero = 15 - len;
		
		for(int loop = 0; loop < lenZero; loop++)
			result = "0" + result;
		
		return result;
		
	}
	
	public String jump(String mnemonic)
	{
		return jumpTable.get(mnemonic);
	}
	
	public String comp(String mnemonic)
	{
		return compTable.get(mnemonic);
	}
	
	public String dest(String mnemonic)
	{
		return destTable.get(mnemonic);
	}
	
	private void initDestTable()
	{
		destTable.put("null", "000");
		destTable.put("M", "001");
		destTable.put("D", "010");
		destTable.put("MD", "011");
		destTable.put("A", "100");
		destTable.put("AM", "101");
		destTable.put("AD", "110");
		destTable.put("AMD", "111");
	}
	
	private void initJumpTable()
	{
		jumpTable.put("null", "000");
		jumpTable.put("JGT", "001");
		jumpTable.put("JEQ", "010");
		jumpTable.put("JGE", "011");
		jumpTable.put("JLT", "100");
		jumpTable.put("JNE", "101");
		jumpTable.put("JLE", "110");
		jumpTable.put("JMP", "111");
	}
	
	private void initCompTable()
	{
		compTable.put("0", "0101010");
		compTable.put("1", "0111111");
		compTable.put("-1", "0111010");
		compTable.put("D", "0001100");
		compTable.put("A", "0110000");
		compTable.put("!D", "0001101");
		compTable.put("!A", "0110001");
		compTable.put("-D", "0001111");
		compTable.put("-A", "0110011");
		compTable.put("D+1", "0011111");
		compTable.put("A+1", "0110111");
		compTable.put("D-1", "0001110");
		compTable.put("A-1", "0110010");
		compTable.put("D+A", "0000010");
		compTable.put("D-A", "0010011");
		compTable.put("A-D", "0000111");
		compTable.put("D&A", "0000000");
		compTable.put("D|A", "0010101");
		
		compTable.put("M", "1110000");
		compTable.put("!M", "1110001");
		compTable.put("-M", "1110011");
		compTable.put("M+1", "1110111");
		compTable.put("M-1", "1110010");
		compTable.put("D+M", "1000010");
		compTable.put("D-M", "1010011");
		compTable.put("M-D", "1000111");
		compTable.put("D&M", "1000000");
		compTable.put("D|M", "1010101");
	}
	
}
