package com.chinacloud.bd.basicdemo;

import java.util.Collection;
import java.util.Iterator;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

public class TestMainTest {

    @Before
    public void setUp() throws Exception {

        System.out.println("setUp");
    }

    @After
    public void tearDown() throws Exception {
    }

    @Before
    public void init() {
        System.out.println("init");

    }

    @Test
    @Ignore
    public void test() {

        TestMain.main(null);
        System.out.println("test in test");

    }

    @Test
    public void testTTTT() {

        System.out.println("test in test");

    }

    @Test
    public void testMultiMap() {

        Multimap<String, String> mp = ArrayListMultimap.create();

        mp.put("test1", "test001");
        mp.put("test1", "test002");

        // mp.put("test2", "test002");
        // mp.put("test2", "test002");
        // mp.put("test2", "");
        // mp.put("test2", "");

        Iterator<String> iterator = mp.keys().iterator();

        while (iterator.hasNext()) {
            String key = iterator.next();
            System.out.println("the key: " + key);
        }

        Collection<String> collection = mp.get("test1");

        for (String string : collection) {
            System.out.println("value:" + string);
        }

        System.out.println("collection size: " + mp.size());
    }

}
