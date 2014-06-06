package com.chinacloud.bd.elasticsearch.demo;

import java.util.List;

import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;

public class ElasticSearchHelper {

    private TransportClient client;
    private String index;
    private String type;

    ElasticSearchHelper(String index, String type) {

        this.index = index;
        this.type = type;

        client = new TransportClient();
        for (String host : client.settings().getAsArray("transport.client.initial_nodes")) {
            int port = 9300;

            // or parse it from the host string...
            String[] splitHost = host.split(":", 2);
            if (splitHost.length == 2) {
                host = splitHost[0];
                port = Integer.parseInt(splitHost[1]);
            }

            System.out.println("host: " + host);

            client.addTransportAddress(new InetSocketTransportAddress(host, port));
        }

        client.addTransportAddress(new InetSocketTransportAddress("master1", 9300));

    }

    public static void main(String[] args) {

        // String clusterName = "elasticsearch";

        // TransportClient client = new TransportClient();

        // GetResponse response = client.prepareGet("megacorp", "employee", "" +
        // 1).execute().actionGet();
        //
        // String string = response.getSourceAsString().toString();
        //
        // System.out.println("response: " + string);

        // Node node =
        // NodeBuilder.nodeBuilder().clusterName(clusterName).node();
        // Client client = node.client();
        //
        // node.close();

        // Node node =
        // NodeBuilder.nodeBuilder().clusterName(clusterName).client(true).local(true).node();
        // Client client = node.client();

        // Client client = new TransportClient().addTransportAddress(new
        // InetSocketTransportAddress("127.0.0.1", 9300))
        // .addTransportAddress(new InetSocketTransportAddress("127.0.0.1",
        // 9300));
        //
        // // on shutdown

        // client.close();

        // // 程序中更改集群结点名称 并且设置client.transport.sniff为true来使客户端去嗅探整个集群的状态
        // Settings settings =
        // ImmutableSettings.settingsBuilder().put("cluster.name", clusterName)
        // .put("client.transport.sniff", true).build();
        // //
        // //
        // // // 创建客户端对象
        // TransportClient client = new TransportClient(settings);
        // // // 客户端对象初始化集群内结点,绑定多个ip
        // client.addTransportAddress(new
        // InetSocketTransportAddress("127.0.0.1", 9300));
        // client.addTransportAddress(new
        // InetSocketTransportAddress("192.168.1.59", 9300));
        //
        // // 搜索,根据Id查询
        // GetResponse response = client.prepareGet("datum", "datum", "" +
        // 130).execute().actionGet();

        // System.out.println("response: " + response.toString());

        //
        // // 查询结果映射成对象类
        // ObjectMapper mapper = new ObjectMapper();
        // Datum datum = mapper.convertValue(response.getSource(), Datum.class);

        // System.out.println("资讯编号:" + datum.getId() + "\t资讯标题:" +
        // datum.getTitle());
        //
        // // 构造查询器查询,第一个参数为要查询的关键字,第二个参数为要检索的索引库中的对应索引类型的域
        // QueryBuilder query = QueryBuilders.multiMatchQuery("恩必普", "keyword");
        // // 第一个参数datum表示索引库,第二个参数datum表示索引类型,from表示开始的位置 size表示查询的条数
        // // ,类似mysql中的limit3,5
        // SearchResponse searchResponse =
        // client.prepareSearch("datum").setTypes("datum").setQuery(query).setFrom(3)
        // .setSize(5).execute().actionGet();
        //
        // // 将搜索结果转换为list集合对象
        // List<Datum> lists = getBeans(searchResponse);
        //
        // System.out.println("查询出来的结果数:" + lists.size());
        // for (Datum dtm : lists) {
        // System.out.println("资讯编号:" + dtm.getId() + "\t资讯标题:" +
        // dtm.getTitle());
        // }

        // 关闭客户端
        // client.close();
    }

    public void search(QueryBuilder builder) {

        SearchResponse response = client.prepareSearch(this.index).setTypes(this.type)

        .setSearchType(SearchType.DFS_QUERY_THEN_FETCH).setQuery(builder) // Query
                // .setPostFilter(FilterBuilders.rangeFilter("age").from(12).to(18))
                // // Filter
                .setFrom(0).setSize(60).setExplain(true).execute().actionGet();

        SearchHits hits = response.getHits();
        System.out.println("total hits: " + hits.getTotalHits());

        SearchHit[] searchHits = hits.getHits();

        if (searchHits.length > 0) {
            for (SearchHit searchHit : searchHits) {
                String sourceAsString = searchHit.getSourceAsString();
                System.out.println("hit: " + sourceAsString);
            }
        }

    }

    public void bulk(List<String> jsons) {

        BulkRequestBuilder bulkRequest = client.prepareBulk();

        // either use client#prepare, or use Requests# to directly build
        // index/delete requests

        int size = jsons.size();

        for (int i = 0; i < size; i++) {
            bulkRequest.add(client.prepareIndex(this.index, this.type, "" + i).setSource(jsons.get(i)));
        }

        BulkResponse bulkResponse = bulkRequest.execute().actionGet();
        if (bulkResponse.hasFailures()) {
            // process failures by iterating through each bulk response item
            System.out.println("fail: " + bulkResponse.buildFailureMessage());
        }

    }

    public String index(String json, int id) {

        // String json = "{" +
        // "\"user\":\"kimchy\"," +
        // "\"postDate\":\"2013-01-30\"," +
        // "\"message\":\"trying out Elasticsearch\"" +
        // "}";

        IndexResponse response = client.prepareIndex(this.index, this.type, "" + id).setSource(json).execute()
                .actionGet();

        response.getIndex();

        String _index = response.getIndex();
        String _type = response.getType();
        String _id = response.getId();
        long _version = response.getVersion();

        System.out.println("_id : " + _id + "index: " + _index + "type: " + _type + "version: " + _version);
        return _id;

    }

    public String get(String id) {

        GetResponse response = client.prepareGet(this.index, this.type, id).execute().actionGet();
        String id2 = response.getId();
        String string = response.getSourceAsString().toString();
        System.out.println("id: " + id2 + "response: " + string);
        return string;

    }

    public String delete(String id) {

        client.prepareDelete(this.index, this.type, id);

        DeleteResponse response = client.prepareDelete(this.index, this.type, id).execute().actionGet();

        String delID = response.getId();
        System.out.println("delete " + "" + delID);
        return delID;

    }

    public void close() {
        // 关闭客户端
        client.close();
    }

}
