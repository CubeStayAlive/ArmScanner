package gateway.hook;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;

public class TerminalTCP
{
	public static final int PORT_TCL = 6666;
	public static final int CAPACITY = 18000;
	public static final byte LIMITER = (byte) 0X1A;

	private SocketChannel out;
	private SelectorProvider selectorProvider;
	private SocketAddress socketAddress;
	private ByteBuffer bbout;
	private ByteBuffer bbin;

	public TerminalTCP()
	{
		bbout = ByteBuffer.allocate(CAPACITY);
		bbin = ByteBuffer.allocate(CAPACITY);
		selectorProvider = SelectorProvider.provider();
		try
		{
			socketAddress = new InetSocketAddress("localhost", PORT_TCL);
			out = selectorProvider.openSocketChannel();
			// out.bind(socketAddress);
			out.connect(socketAddress);
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			x();
		}
	}

	void dialog(StringBuilder sbout, StringBuilder sbin)
	{
		int i;
		boolean reading = true;

		// slice strin into bb
		bbout.clear();
		for (i = 0; i < sbout.length(); i++)
		{
			bbout.put((byte) sbout.charAt(i));
		}
		bbout.put(LIMITER);
		bbout.flip();
		try
		{
			out.write(bbout);
			sbin.setLength(0);
			bbin.clear();
			while (reading)
			{
				bbin.clear();
				out.read(bbin);
				bbin.flip();
				if (bb2sb(bbin, sbin))
				{	
					reading = false;
				}
			}
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		p("Got String <<<" + sbin.toString() + ">>>");
	}

	private void x()
	{
		System.exit(0);
	}

	public void close()
	{
		try
		{
			out.close();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private boolean bb2sb(ByteBuffer bb, StringBuilder sb)
	{
		while (bb.hasRemaining())
		{
			byte b = bb.get();
			if (b == LIMITER)
			{
				if (bb.hasRemaining())
				{
					p("You schould not be here!");
				}
				return true;
			} else
			{
				sb.append((char)b);
			}
		}
		return false;
	}

	private void p(String s)
	{
		System.out.println(s);
	}
}
