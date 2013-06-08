package ru.g4.protocols.ce102;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.Arrays;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author alena
 */
public class DataTypesOutputStreamTest {

    public DataTypesOutputStreamTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of writeUINT8 method, of class DataTypesOutputStream.
     */
    @Test
    public void testWriteUINT8() throws Exception {
        System.out.println("writeUINT8");
        byte[] s = new byte[]{1, -1, -2};
        ByteArrayOutputStream array = new ByteArrayOutputStream();
        DataTypesOutputStream outputStream = new DataTypesOutputStream(array);

        outputStream.writeUINT8(1);
        outputStream.writeUINT8(255);
        outputStream.writeUINT8(254);
        assertTrue(Arrays.equals(s, array.toByteArray()));

        // TODO review the generated test code and remove the default call to fail.

    }

    /**
     * Test of writeUINT16 method, of class DataTypesOutputStream.
     */
    @Test
    public void testWriteUINT16() throws Exception {
        System.out.println("writeUINT16");
        byte[] s = new byte[]{1, 1, -1,-1};
        ByteArrayOutputStream array = new ByteArrayOutputStream();
        DataTypesOutputStream outputStream = new DataTypesOutputStream(array);

        outputStream.writeUINT16(257);
        outputStream.writeUINT16(256*256-1);
        
        assertTrue(Arrays.equals(s, array.toByteArray()));
    }

    /**
     * Test of writeUINT32 method, of class DataTypesOutputStream.
     */
    @Test
    public void testWriteUINT32() throws Exception {
        System.out.println("writeUINT16");
        byte[] s = new byte[]{1, 1, -1,-1};
        ByteArrayOutputStream array = new ByteArrayOutputStream();
        DataTypesOutputStream outputStream = new DataTypesOutputStream(array);

        outputStream.writeUINT32(257);
        outputStream.writeUINT32(256*256-1);
        
        assertTrue(Arrays.equals(s, array.toByteArray()));
    }

    /**
     * Test of writeStrN method, of class DataTypesOutputStream.
     */
    @Test
    public void testWriteStrN_3args() throws Exception {
        System.out.println("writeStrN");
        char[] m = null;
        int offset = 0;
        int length = 0;
        DataTypesOutputStream instance = null;
        instance.writeStrN(m, offset, length);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of writeStrN method, of class DataTypesOutputStream.
     */
    @Test
    public void testWriteStrN_charArr() throws Exception {
        System.out.println("writeStrN");
        char[] m = null;
        DataTypesOutputStream instance = null;
        instance.writeStrN(m);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of writeBCD method, of class DataTypesOutputStream.
     */
    @Test
    public void testWriteBCD() throws Exception {
        System.out.println("writeBCD");
        int x = 0;
        DataTypesOutputStream instance = null;
        instance.writeBCD(x);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of writeData3 method, of class DataTypesOutputStream.
     */
    @Test
    public void testWriteData3() throws Exception {
        System.out.println("writeData3");
        int x = 0;
        DataTypesOutputStream instance = null;
        instance.writeData3(x);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of writeData2 method, of class DataTypesOutputStream.
     */
    @Test
    public void testWriteData2() throws Exception {
        System.out.println("writeData2");
        int x = 0;
        DataTypesOutputStream instance = null;
        instance.writeData2(x);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of writeData4 method, of class DataTypesOutputStream.
     */
    @Test
    public void testWriteData4() throws Exception {
        System.out.println("writeData4");
        int x = 0;
        DataTypesOutputStream instance = null;
        instance.writeData4(x);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}
