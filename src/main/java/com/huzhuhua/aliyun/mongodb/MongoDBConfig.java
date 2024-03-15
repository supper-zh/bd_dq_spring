package com.aliyun.mongodb;

public class MongoDBConfig {
    public static final String HOST = "localhost";
    public static final int PORT = 27017;
    public static final String DATABASE = "MonitorSysDb";
    public static final String COLLECTION = "bdata";

    // 使用静态方法获取配置
    public static String getHost() {
        return HOST;
    }

    public static int getPort() {
        return PORT;
    }

    public static String getDatabase() {
        return DATABASE;
    }

    public static String getCollection() {
        return COLLECTION;
    }

    // 从属性文件中加载配置：
    // private String host;
    // private int port;
    // private String database;
    // private String collection;
    // public MongoDBConfig() {
    //     loadConfig();
    // }
    //
    // private void loadConfig() {
    //     Properties props = new Properties();
    //     try (InputStream input = getClass().getClassLoader().getResourceAsStream("config.properties")) {
    //         if (input == null) {
    //             throw new RuntimeException("Unable to find config.properties");
    //         }
    //         props.load(input);
    //         host = props.getProperty("mongo.host", "localhost");
    //         port = Integer.parseInt(props.getProperty("mongo.port", "27017"));
    //         database = props.getProperty("mongo.database", "myDatabase");
    //         collection = props.getProperty("mongo.collection", "myCollection");
    //     } catch (IOException ex) {
    //         ex.printStackTrace();
    //         throw new RuntimeException("Error loading configuration properties", ex);
    //     }
    // }
    //
    // // Getters
    // public String getHost() {
    //     return host;
    // }
    //
    // public int getPort() {
    //     return port;
    // }
    //
    // public String getDatabase() {
    //     return database;
    // }
    //
    // public String getCollection() {
    //     return collection;
    // }
}
