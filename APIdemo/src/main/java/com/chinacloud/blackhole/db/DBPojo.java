package com.chinacloud.blackhole.db;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class DBPojo implements Serializable {

    private String dbtype;
    private String ip;
    private String port;
    private String instance;
    private String user;
    private String pwd;
    private String tableName;
    private List<SourceField> sourcefields;

    public String getInstance() {
        return instance;
    }

    public void setInstance(String instance) {
        this.instance = instance;
    }

    public String getDbtype() {
        return dbtype;
    }

    public void setDbtype(String dbType) {
        this.dbtype = dbType;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getName() {
        return tableName;
    }

    public void setName(String name) {
        this.tableName = name;
    }

    public List<SourceField> getSourcefields() {
        if (sourcefields == null) {
            sourcefields = new ArrayList<SourceField>();
        }
        return sourcefields;
    }

    public void setSourcefields(List<SourceField> sourcefields) {
        this.sourcefields = sourcefields;
    }

    public String toString() {
        return "{dbtype:" + dbtype + ", ip:" + ip + ", port:" + port + ", instance:" + instance + ", tablename:" + tableName
                + ", user:" + user + ", pwd:" + pwd + ", sourcefields:" + sourcefields.toString() + "}";
    }

}
