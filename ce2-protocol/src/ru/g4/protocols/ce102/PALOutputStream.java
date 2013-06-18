package ru.g4.protocols.ce102;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import ru.g4.utils.HEXUtils;

public class PALOutputStream extends FilterOutputStream {

    public PALOutputStream(OutputStream out) {
	super(out);
    }

    public void writePALRequest(PALRequest request) throws IOException {
	writePassw(request.getPassw());
	writeServ(request.getServ());
	writeAddr(request.getCommandCode());
	out.write(request.getData().array());

    }

    public void writePALResponse(PALResponse response) throws IOException {
	writeServ(response.getServ());
	writeAddr(response.getCommandCode());
	out.write(response.getData().array());
    }

    public void writeServ(Serv serv) throws IOException {
	int b = serv.getDirection() | (serv.getAccessCode() << 4)
		| serv.getDataLength();
	out.write(b);
    }

    public void writePassw(long passw) throws IOException {
	out.write(HEXUtils.long2BytesLH(passw, 4));
    }

    public void writeAddr(CommandEnum command) throws IOException {
	writeAddr(command.getAddr());
    }

    public void writeAddr(int command) throws IOException {
	out.write(command >> 8);
	out.write(command & 0xFF);
    }
}
