package terminaltests;

import gateway.hook.HookOpenOCDTcl;

public class t1
{

	public static void main(String[] args)
	{
		HookOpenOCDTcl hookOpenOCDTcl = new HookOpenOCDTcl();
		hookOpenOCDTcl.t1();
		hookOpenOCDTcl.close();
	}
}
