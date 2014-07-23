package com.omade.guava;

import static org.junit.Assert.fail;

import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.google.common.base.Function;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;

public class GuavaDemoTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testFunction() {

        // List<Map<String, String>> listOfMaps = mapOf("type", "blog", "id",
        // "292", "author", "john");
        // Multimap<String, Map<String, String>> partitionedMap =
        // Multimaps.index(listOfMaps,
        // new Function<Map<String, String>, String>() {
        // public String apply(final Map<String, String> from) {
        // return from.get("type");
        // }
        // });
    }

    @Test
    @Ignore
    public void test() {
        fail("Not yet implemented");
    }

}
