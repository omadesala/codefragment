package com.chinacloud.bd.basicdemo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.elasticsearch.common.collect.Lists;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
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
    public void test() {

        // String str = String.format("格式参数$的使用：,%2$s,%1$d", 99, "abc");
        // System.out.println(str);
        // String id = "123,234,345";
        String id = "123";

        Iterable<String> split = Splitter.on(",").split(id);
        ArrayList<String> list = Lists.newArrayList(split);

        for (String string : list) {
            System.out.println("list: " + string);
        }

    }

    @Test
    public void testTTTT() {

        System.out.println("test in test");
        List<String> names = Lists.newArrayList();

        names.add("hello1");
        // names.add("hello2");
        // names.add("hello3");

        String parameter = Joiner.on(",").join(names);

        System.out.println("test in test: " + parameter);

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
