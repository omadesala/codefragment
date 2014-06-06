package com.chinacloud.bd.elasticsearch.demo;

import static org.junit.Assert.fail;

import java.util.List;

import org.elasticsearch.common.base.Strings;
import org.elasticsearch.common.collect.Lists;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class ElasticSearchHelperTest {

    private ElasticSearchHelper elasticsearch;

    @Before
    public void setUp() throws Exception {

        elasticsearch = new ElasticSearchHelper("megacorp", "employee");

    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testGet() {

        String string = elasticsearch.get("" + 100);
        Assert.assertTrue(!Strings.isNullOrEmpty(string));
    }

    @Test
    public void testIndex() {

        String json = "{" + "\"first_name\":\"daniel_test\"," + "\"last_name\":\"fortest\"," + "\"age\": 35,"
                + "\"about\":\"我们首先导入数据，选择相应的数据源\"" + "}";

        String string = elasticsearch.index(json, 7);
        Assert.assertTrue(!Strings.isNullOrEmpty(string));
    }

    @Test
    public void testBulk() {

        List<String> jsons = Lists.newArrayList();

        String[] contents = new String[3];

//        contents[0] = " 学习Lucene是为了更深入搜索，学习Lucene4是为了弄懂ES中没有解释的疑问，等看完Lucene，才发现，搜索的核心原来都是Lucene，Elasticsearch只不过是包在Lucene外面的一层皮。";
//        contents[1] = "Lucene是一个搜索引擎包，它并非一个像tomcat一样的产品。它衍生出了solr和elasticsearch。当然，我学习的是后者。solr是apache孵化的一个搜索引擎。";
//        contents[2] = "马XX说过，学习是一个概念，判断，推理的过程，现在想想，老人家说的很多话，还真是那么回事。下面就是Lucene4中的一些基础核心概念。";

        contents[0] = "学习Lucene是为了更深入搜索";
        contents[1] = "Lucene是一个搜索引擎包";
        contents[2] = "马XX说过，学习是一个概念，判断，推理的过程";
        
        
        long t1 = System.currentTimeMillis();
        for (int i = 0; i < contents.length; i++) {
            // String content = "我们首先导入数据，选择相应的数据源" + i + UUID.randomUUID();
            String json = "{" + "\"first_name\":\"daniel_test\"," + "\"last_name\":\"fortest\"," + "\"age\": 35,"
                    + "\"about\":" + "\"" + contents[i] + "\"" + "}";
            jsons.add(json);
        }

        elasticsearch.bulk(jsons);

        t1 = System.currentTimeMillis() - t1;

        System.out.println("time used: " + t1 / 1000 + "s " + t1 + "ms");

    }

    @Test
    public void testDelete() {

        long t1 = System.currentTimeMillis();
        for (int i = 0; i < 100; i++) {
            elasticsearch.delete("" + i);
        }

        t1 = System.currentTimeMillis() - t1;

        System.out.println("time used: " + t1 / 1000 + "s " + t1 + "ms");

    }

    @Test
    public void testSearch() {

//        QueryBuilder builder = QueryBuilders.multiMatchQuery("马XX", "first_name", "about");
//        QueryBuilder builder = QueryBuilders.multiMatchQuery("马XX", "first_name", "about");

//        QueryBuilder builder = QueryBuilders.queryString("为了更深入").field("about").field("first_name");
//        QueryBuilder builder = QueryBuilders.queryString("为了").field("about");
        
        
        QueryBuilder builder = QueryBuilders.matchQuery("about", "更深入");
        
        
        elasticsearch.search(builder);
    }

    @Test
    @Ignore
    public void test() {
        fail("Not yet implemented");
    }

}
