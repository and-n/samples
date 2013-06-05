package ru.g4.protocols.ce102;

import java.io.EOFException;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author alena
 */
public class DataTypesInputStream extends FilterInputStream {

    public DataTypesInputStream(InputStream in) {
        super(in);
    }
    
    public void readFully(byte[] buf) throws IOException {
		for (int i = 0, count = 0; i < buf.length; i += count) {
			count = in.read(buf, i, buf.length - i);
			if (count < 0)
				throw new EOFException();
		}
	}

    public int readUINT8() throws IOException {
        int b = in.read();
        if (b < 0) {
            throw new EOFException();
        }
        return b;
    }

    public int readUINT16() throws IOException {
        int a = in.read();
        int b = in.read();
        if ((b < 0) && (a < 0)) {
            throw new EOFException();
        }
        a = a << 8;
        return a + b;
    }

    public long readUINT32() throws IOException {
        int a = in.read();
        int b = in.read();
        int c = in.read();
        int d = in.read();
        if ((b < 0) && (a < 0) && (c < 0) && (d < 0)) {
            throw new EOFException();
        }
        a = a << 24;
        b = b << 16;
        c = c << 8;
        return a + b + c + d;
    }

    public void readStrN(char[] m, int offset, int length) throws IOException {

        int i = offset;
        while (i < length+offset) {
            int x = in.read();
            System.out.println("i="+i+" x="+x);
            if (x < 0) {
                throw new EOFException();
            }
            m[i] = (char) x;
            System.out.println("m[i]="+m[i]);
            i++;
        }
        
    }

    public void readStrN(char[] m) throws IOException {
        readStrN(m, 0, m.length);
    }

    public int readBCD() throws IOException {
        int x = in.read();
        if (x < 0) {
            throw new EOFException();
        }

        int a = 15 & x;
        x = x >> 4;
        int b = 15 & x;

        return b * 10 + a;
    }

    public int readData2() throws IOException {
        readUINT16();
        return 0;
    }

    public int readData3() throws IOException {
        int a = in.read();
        int b = in.read();
        int c = in.read();
        if ((b < 0) && (a < 0) && (c < 0)) {
            throw new EOFException();
        }
        a = a << 16;
        b = b << 8;
        return a + b + c;
    }

    public int readData4() throws IOException {
        readUINT32();
        return 0;
    }

 
}
