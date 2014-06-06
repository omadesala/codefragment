package com.chinacloud.bd.basicdemo;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;

public class TestMain {

    public static void main(String[] args) {

        TestMain testMain = new TestMain();
        // testMain.testaddSourceToOrbit();
        // testMain.testaddModel();
        testMain.testgetAllSource();
        // testMain.testgetAllModel();
        // testMain.testgetTemplateAllModel();

    }

    public void testaddSourceToOrbit() {

        HttpClient client = new HttpClient();
        // String uri = "http://192.168.1.116:8089/chinacloud/orbit/source";
        String uri = "http://127.0.0.1:8089/chinacloud/orbit/source";
        PostMethod post = new PostMethod(uri);

        // String json =
        // "{\"dbtype\": \"logstash\",\"ip\": \"127.0.0.1\",  \"port\": \"3306\",  \"dbinstance\": \"logstash\",\"username\": \"root\", \"password\": \"123456\",  \"tables\": []}";
        String json = "{\"dbtype\": \"logstash\",\"ip\": \"127.0.0.1\", \"port\": \"3306\", \"dbinstance\": \"logstash02\",\"username\": \"root\", \"password\": \"123456\",\"tables\":[{\"name\": \"\",\"schemas\": [{\"colname\": \"\",\"datatype\": \"\",\"coltype\": 0},{\"colname\": \"\", \"datatype\": \"\",\"coltype\": 0}]},{\"name\": \"\",\"schemas\": [{ \"colname\": \"\",\"datatype\": \"\",\"coltype\": 0 },{\"colname\": \"\",\"datatype\": \"\",\"coltype\": 2}]}]} ";

        RequestEntity requestEntity = null;
        try {
            requestEntity = new StringRequestEntity(json, "text/plain", "utf-8");
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        post.setRequestEntity(requestEntity);

        String jsonString = null;
        try {
            int statusCode = client.executeMethod(post);
            if (statusCode == 200) {
                jsonString = post.getResponseBodyAsString(2048 * 10);
            }

            System.out.println("statusCode: " + statusCode);
        } catch (IOException e) {
            jsonString = null;
            e.printStackTrace();
        } finally {
            client.getHttpConnectionManager().closeIdleConnections(1);
        }

        System.out.println("response: " + jsonString);
    }

    public void testaddModel() {

        HttpClient client = new HttpClient();
        // String uri = "http://192.168.1.116:8089/chinacloud/orbit/all";
        String uri = "http://127.0.0.1:8089/chinacloud/orbit/all";
        PostMethod post = new PostMethod(uri);

        String json = "{\"queue\": {\"ip\": \"192.168.1.116\", \"port\": \"6379\"},\"sources\": ["
                + "{\"dbtype\": \"forward\",\"instance\": \"test_agent001\",\"ip\": \"192.168.1.116\","
                + " \"name\": \"user\", \"port\": \"3306\",  \"pwd\": \"root\",\"sourcefields\": ["
                + "{\"column\": \"\",\"type\": \"\"}, {    \"column\": \"\", \"type\": \"\"},"
                + "{  \"column\": \"sex\", \"type\": \"string\" } ], \"user\": \"root\" },{ \"dbtype\": \"mysql\","
                + "  \"instance\": \"test\", \"ip\": \"192.168.1.116\", \"joinfield\": \"member_id\","
                + "    \"name\": \"order1\",\"port\": \"3306\", \"pwd\": \"root\", \"sourcefields\": ["
                + "  {\"column\": \"member_id\", \"type\": \"string\" }, { \"column\": \"count\",\"type\": \"string\""
                + "}, {\"column\": \"company\",\"type\": \"string\"}],\"user\": \"root\"}],"
                + "  \"target\": { \"name\": \"member_order\",\"targetfields\": [{    \"column\": \"id\","
                + "  \"type\": \"string\"  }, {  \"column\": \"name\", \"type\": \"string\" },  { \"column\": \"sex\","
                + "  \"type\": \"string\"  }, { \"column\": \"count\",  \"type\": \"string\"},{\"column\": \"company\","
                + " \"type\": \"string\" } ], \"uniqueKey\": \"id\"},\"type\": \"join\"} ";

        RequestEntity requestEntity = null;
        try {
            requestEntity = new StringRequestEntity(json, "text/plain", "utf-8");
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        post.setRequestEntity(requestEntity);

        String jsonString = null;
        try {
            int statusCode = client.executeMethod(post);
            if (statusCode == 200) {
                jsonString = post.getResponseBodyAsString(2048 * 10);
            }

            System.out.println("statusCode: " + statusCode);
        } catch (IOException e) {
            jsonString = null;
            e.printStackTrace();
        } finally {
            client.getHttpConnectionManager().closeIdleConnections(1);
        }

        System.out.println("response: " + jsonString);

    }

    public void testgetAllModel() {

        HttpClient client = new HttpClient();
        // String uri = "http://192.168.1.116:8089/chinacloud/orbit/all";
        String uri = "http://127.0.0.1:8089/chinacloud/orbit/all";
        GetMethod method = new GetMethod(uri);

        String jsonString = null;;
        try {
            // int statusCode = client.executeMethod(method);
            int statusCode = client.executeMethod(method);
            if (statusCode == 200) {
                jsonString = method.getResponseBodyAsString();
            }
            System.out.println("statusCode: " + statusCode);
        } catch (IOException e) {
            jsonString = null;
            e.printStackTrace();
        } finally {
            client.getHttpConnectionManager().closeIdleConnections(1);
        }

        System.out.println("response: " + jsonString);

    }

    public void testgetTemplateAllModel() {

        HttpClient client = new HttpClient();
        // String uri =
        // "http://192.168.1.116:8089/chinacloud/orbit/template/all";
        String uri = "http://127.0.0.1:8089/chinacloud/orbit/template/all";
        GetMethod method = new GetMethod(uri);

        String jsonString = null;;
        try {
            // int statusCode = client.executeMethod(method);
            int statusCode = client.executeMethod(method);
            if (statusCode == 200) {
                jsonString = method.getResponseBodyAsString();
            }
            System.out.println("statusCode: " + statusCode);
        } catch (IOException e) {
            jsonString = null;
            e.printStackTrace();
        } finally {
            client.getHttpConnectionManager().closeIdleConnections(1);
        }

        System.out.println("response: " + jsonString);

    }

    public void testgetAllSource() {

        HttpClient client = new HttpClient();
        // String uri =
        // "http://192.168.1.116:8089/chinacloud/orbit/reporting/sources";
        String uri = "http://127.0.0.1:8089/chinacloud/orbit/reporting/sources";
        GetMethod method = new GetMethod(uri);

        String jsonString = null;;
        try {
            // int statusCode = client.executeMethod(method);
            int statusCode = client.executeMethod(method);
            if (statusCode == 200) {
                // jsonString = method.getResponseBodyAsString();
                String response = getStringByInputStream(method.getResponseBodyAsStream());
                System.out.println("response: " + response);

            }
            System.out.println("statusCode: " + statusCode);
        } catch (IOException e) {
            jsonString = null;
            e.printStackTrace();
        } finally {
            client.getHttpConnectionManager().closeIdleConnections(1);
        }

        System.out.println("response: " + jsonString);

    }

    private String getStringByInputStream(InputStream responseBodyAsStream) throws IOException {

        int available = responseBodyAsStream.available();

        if (available == 0) {
            return "no data avalible ";
        }

        System.out.println("data length: " + available);

        StringBuffer out = new StringBuffer();
        byte[] b = new byte[4096];
        for (int n; (n = responseBodyAsStream.read(b)) != -1;) {
            out.append(new String(b, 0, n));
        }

        return out.toString();
    }

}
