
public class Parser 
{
	private String command;
	private String dest;
	private String comp;
	private String jump;
	
	public Parser(String command)
	{
		this.command = command;
		dest = "null";
		comp = "null";
		jump = "null";
		parseCommand();
	}
	
	public String dest()
	{
		return dest;
	}
	
	public String comp()
	{
		return comp;
	}
	
	public String jump()
	{
		return jump;
	}
	
	private void parseCommand()
	{
		String type = commandType();
		
		if(type.equals("C_COMMAND"))
		{
			String[] parts = command.split("=");
			
			if(parts.length > 1)
			{
				dest = parts[0];
				
				parts = parts[1].split(";");
				comp = parts[0];
				
				if(parts.length > 1)
					jump = parts[1];
			}
			else
			{
				parts = command.split(";");
				comp = parts[0];
				jump = parts[1];
			}
			
		}
	}
	
	public String commandType()
	{
		String type = "C_COMMAND";
		
		if(command.charAt(0) == '@')
			type = "A_COMMAND";
		
		return type;
	}
	
	//Should be call only on A_COMMAND
	public String symbol()
	{
		return command.substring(1);
	}
	
	
}
