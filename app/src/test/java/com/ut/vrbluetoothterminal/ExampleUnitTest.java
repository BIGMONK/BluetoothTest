package com.ut.vrbluetoothterminal;

import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void hashMap_put()throws Exception{
        HashMap<Integer,Integer> hashMap=new HashMap<>();
        System.out.println("LLLLLLLLLL="+  hashMap.put(1,1));
        System.out.println("LLLLLLLLLL="+  hashMap.put(1,1));
        System.out.println("LLLLLLLLLL="+  hashMap.put(1,2));
        System.out.println("LLLLLLLLLL="+  hashMap.put(2,1));
        System.out.println("LLLLLLLLLL="+  hashMap.put(1,1));
        System.out.println("LLLLLLLLLL="+  hashMap.put(2,2));

    }
}