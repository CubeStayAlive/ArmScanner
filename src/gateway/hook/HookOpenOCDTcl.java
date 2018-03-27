package gateway.hook;

public class HookOpenOCDTcl
{
	private TerminalTCP terminalTCP;
	StringBuilder sbout = new StringBuilder();
	StringBuilder sbin = new StringBuilder();
	RegisterList registerList;

	public HookOpenOCDTcl()
	{
		terminalTCP = new TerminalTCP();

	}
	public void t1()
	{
		sbout.setLength(0);;
		sbout.append("reset halt\n");
		terminalTCP.dialog(sbout, sbin);

		sbout.setLength(0);;
		sbout.append("capture \"reg\"\n");
		terminalTCP.dialog(sbout, sbin);

		sbout.setLength(0);;
		sbout.append("reset run\n");
		terminalTCP.dialog(sbout, sbin);
		
		
	}
	public void setRegisterList(RegisterList rl)
	{
		registerList = rl;
	}
	public void get()
	{
		int dotPos = 0;
		for(Register r : registerList.values())
		{
			sbout.setLength(0);;
			sbout.append("capture \"mdw 0X");
			sbout.append( Integer.toHexString(r.address) );
			sbout.append(" 1\"\n");
			terminalTCP.dialog(sbout, sbin);
			boolean searching = true;
			while( searching)
			{
				if( sbin.charAt(dotPos) != ':')
				{
					dotPos++;
					continue;
				}
				if( sbin.charAt(dotPos+1) == ' ')
				{
					searching = false;
				}
			}
			r.data = Integer.parseUnsignedInt(sbin.substring(dotPos+2,sbin.length()-2), 16);
		}
	}
	public void close()
	{
		terminalTCP.close();
	}
	public void snapRegs()
	{
		sbout.setLength(0);;
		sbout.append("halt\n");
		terminalTCP.dialog(sbout, sbin);

		sbout.setLength(0);;
		sbout.append("capture \"reg\"\n");
		terminalTCP.dialog(sbout, sbin);

		sbout.setLength(0);;
		sbout.append("resume\n");
		terminalTCP.dialog(sbout, sbin);
	}
	private void p(String s)
	{
		System.out.println(s);
	}
}
