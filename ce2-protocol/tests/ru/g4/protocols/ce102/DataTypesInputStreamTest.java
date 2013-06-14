package ru.g4.protocols.ce102;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.ByteArrayInputStream;
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
public class DataTypesInputStreamTest {
    
    public DataTypesInputStreamTest() {
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
     * Test of readUINT8 method, of class DataTypesInputStream.
     */
    @Test
    @SuppressWarnings("resource")
    public void testReadUINT8() throws Exception {
        System.out.println("readUINT8");
	DataTypesInputStream instance = new DataTypesInputStream(new ByteArrayInputStream(new byte[]{-2}));
        int expResult = 254;
        int result = instance.readUINT8();
        assertEquals(expResult, result);
    }

    /**
     * Test of readUINT16 method, of class DataTypesInputStream.
     */
    @Test
    @SuppressWarnings("resource")
    public void testReadUINT16() throws Exception {
        System.out.println("readUINT16");
        DataTypesInputStream instance = new DataTypesInputStream(new ByteArrayInputStream(new byte[]{0,1}));
        int expResult = 1;
        int result = instance.readUINT16();
        assertEquals(expResult, result);       
        
    }

    /**
     * Test of readUINT32 method, of class DataTypesInputStream.
     */
    @Test
    @SuppressWarnings("resource")
    public void testReadUINT32() throws Exception {
        System.out.println("readUINT32");
        DataTypesInputStream instance = new DataTypesInputStream(new ByteArrayInputStream(new byte[]{-1,-1,-1,-1}));
        long expResult = 256*256*256*256-1;
        long result = instance.readUINT32();
        assertEquals(expResult, result);        
    }

    /**
     * Test of readStrWithOffset method, of class DataTypesInputStream.
     */
    @Test
    @SuppressWarnings("resource")
    public void testReadStrWithOffset() throws Exception {
        System.out.println("readStrWithOffset");
        char[] m = new char[3];
        char[] res = new char[]{'a','b','c'};        
          DataTypesInputStream instance = new DataTypesInputStream(new ByteArrayInputStream(new byte[]{(byte)'a',(byte)'b',(byte)'c'}));
        instance.readStrN(m, 0, 3);        
        assertTrue(Arrays.equals(res, m));
    }

    /**
     * Test of readStrN method, of class DataTypesInputStream.
     */
    @Test
    @SuppressWarnings("resource")
    public void testReadStrN() throws Exception {
  System.out.println("readStrWithOffset");
        char[] m = new char[3];
        char[] res = new char[]{'a','b','c'};        
          DataTypesInputStream instance = new DataTypesInputStream(new ByteArrayInputStream(new byte[]{(byte)'a',(byte)'b',(byte)'c'}));
        instance.readStrN(m);        
        assertTrue(Arrays.equals(res, m));
    }

    /**
     * Test of readBCD method, of class DataTypesInputStream.
     */
    @Test
    @SuppressWarnings("resource")
    public void testReadBCD() throws Exception {
        System.out.println("readBCD");
       DataTypesInputStream instance = new DataTypesInputStream(new ByteArrayInputStream(new byte[]{(byte)0x99}));
        int expResult = 99;
        int result = instance.readBCD();
        assertEquals(expResult, result);
    }

    /**
     * Test of readData2 method, of class DataTypesInputStream.
     */
    @Test
    @SuppressWarnings("resource")
    public void testReadData2() throws Exception {
         System.out.println("readUINT16");
        DataTypesInputStream instance = new DataTypesInputStream(new ByteArrayInputStream(new byte[]{0,1}));
        int expResult = 1;
        int result = instance.readUINT16();
        assertEquals(expResult, result);       
        
    }

    /**
     * Test of readData3 method, of class DataTypesInputStream.
     */
    @Test
    @SuppressWarnings("resource")
    public void testReadData3() throws Exception {
        System.out.println("readData3");
        DataTypesInputStream instance = new DataTypesInputStream(new ByteArrayInputStream(new byte[]{-1,-1,-1}));
        int expResult = 256*256*256-1;
        int result = instance.readData3();
        assertEquals(expResult, result);

    }

    /**
     * Test of readData4 method, of class DataTypesInputStream.
     */
    @Test
    @SuppressWarnings("resource")
    public void testReadData4() throws Exception {
        System.out.println("readUINT32");
        DataTypesInputStream instance = new DataTypesInputStream(new ByteArrayInputStream(new byte[]{-1,-1,-1,-1}));
        long expResult = 256*256*256*256-1;
        long result = instance.readUINT32();
        assertEquals(expResult, result);      
    }

  
}
