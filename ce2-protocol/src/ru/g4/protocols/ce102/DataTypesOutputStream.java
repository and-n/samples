package ru.g4.protocols.ce102;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author alena
 */
public class DataTypesOutputStream extends FilterOutputStream {

    public DataTypesOutputStream(OutputStream os) {
        super(os);
    }

    public void writeUINT8(int x) throws IOException {
        out.write(x);
    }

    public void writeUINT16(int x) throws IOException {
        int y = x;
        y = y >> 8;
        out.write(y);
        out.write(x);
    }

    public void writeUINT32(int x) throws IOException {
        int a = x;
        a = a >> 24;
        out.write(a);

        int b = x;
        b = b >> 16;
        out.write(b);

        int c = x;
        c = c >> 8;
        out.write(c);

        out.write(x);
    }

    public void writeStrN(char[] m, int offset, int length) throws IOException {
        int i = offset;
        while (i <= length + offset) {
            out.write(m[i]);
            i++;
        }
    }

    public void writeStrN(char[] m) throws IOException {
        writeStrN(m, 0, m.length);
    }

    public void writeBCD(int x) throws IOException {
        int a = x % 10;
        int b = x / 10 % 10;
        
        b=b<<4;
        out.write(a+b);
    }

    public void writeData3(int x) throws IOException {
        int a = x;
        a = a >> 16;
        out.write(a);

        int b = x;
        b = b >> 8;
        out.write(b);

        out.write(x);
    }

    public void writeData2(int x) throws IOException {
        writeUINT16(x);
    }

    public void writeData4(int x) throws IOException {
        writeUINT32(x);
    }
}
