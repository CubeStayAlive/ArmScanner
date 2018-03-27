package terminaltests;

import gateway.hook.HookOpenOCDTcl;

public class t2
{

	public static void main(String[] args)
	{
		HookOpenOCDTcl hookOpenOCDTcl = new HookOpenOCDTcl();
		hookOpenOCDTcl.snapRegs();
		hookOpenOCDTcl.close();
	}
}
