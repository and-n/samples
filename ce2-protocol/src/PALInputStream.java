import java.io.EOFException;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public class PALInputStream extends FilterInputStream {

	private DataTypesInputStream din;
	
	public PALInputStream(InputStream in) {
		super(in);
		din = new DataTypesInputStream(in);
	}


	public PALRequest readRequest() throws IOException {
		PALRequest req = new PALRequest();
		req.setPassw(readPassw());
		req.setServ(readServ());
		req.setCommandCode(readAddr());
		byte[] data = new byte[req.getServ().getDataLength()];
		din.readFully(data);
		req.setData(ByteBuffer.wrap(data));
		return req;
	}

	public PALResponse readResponse() throws IOException {
		PALResponse resp = new PALResponse();
		resp.setServ(readServ());
		resp.setCommandCode(readAddr());
		byte[] data = new byte[resp.getServ().getDataLength()];
		din.readFully(data);
		resp.setData(ByteBuffer.wrap(data));
		return resp;
	}

	public int readAddr() throws IOException {
		return din.readUINT16();
	}

	public Passw readPassw() throws IOException
	{
		byte[] pb = new byte[4];
		din.readFully(pb);
		return Passw.fromByte(pb);
	}

	public Serv readServ() throws IOException {
		Serv serv = new Serv();
		int b = read();
		if (b < 0 )
		{
			throw new EOFException();
		}
		serv.setDirection(b & 0x80);
		serv.setAccessCode((b & 0x70) >> 4);
		serv.setDataLength(b & 0xF);
		return serv;
	}

}
