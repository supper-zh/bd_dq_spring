package com.huzhuhua.aliyun.mongodb;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

// // 创建一个单例模式的Mongo客户端，它可以被应用中需要进行数据库操作的其他部分重用
@Component
public class MongoDbClient {

    private static MongoClient mongoClient;

    // 构造函数使用Spring的@Value注解来注入MongoDB的配置项
    public MongoDbClient(@Value("${mongodb.host}") String host,
                         @Value("${mongodb.port}") int port) {
        // 直接使用给定的host和port连接到MongoDB，不通过认证
        mongoClient = MongoClients.create(String.format("mongodb://%s:%d", host, port));
    }

    public static MongoClient getMongoClient() {
        return mongoClient;
    }
}



//
// import com.mongodb.MongoClientSettings;
// import com.mongodb.MongoCredential;
// import com.mongodb.ServerAddress;
// import com.mongodb.client.MongoClient;
// import com.mongodb.client.MongoClients;
// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.stereotype.Component;
//
// import java.util.Collections;
//
// // 创建一个单例模式的Mongo客户端，它可以被应用中需要进行数据库操作的其他部分重用
//
// @Component
// public class MongoDbClient {
//
//     private static MongoClient mongoClient;
//
//     @Value("${mongodb.host}")
//     private String host;
//
//     @Value("${mongodb.port}")
//     private int port;
//
//     @Value("${mongodb.username}")
//     private String username;
//
//     @Value("${mongodb.password}")
//     private String password;
//
//     @Value("${mongodb.database}")
//     private String database;
//
//     // 使用Spring的@Value注解来注入配置项
//     public MongoDbClient(@Value("${mongodb.host}") String host,
//                          @Value("${mongodb.port}") int port,
//                          @Value("${mongodb.username}") String username,
//                          @Value("${mongodb.password}") String password,
//                          @Value("${mongodb.database}") String database) {
//         MongoCredential credential = MongoCredential.createCredential(username, database, password.toCharArray());
//         mongoClient = MongoClients.create(
//                 MongoClientSettings.builder()
//                         .applyToClusterSettings(builder ->
//                                 builder.hosts(Collections.singletonList(new ServerAddress(host, port))))
//                         .credential(credential)
//                         .build());
//     }
//
//     public static MongoClient getMongoClient() {
//         return mongoClient;
//     }
// }



// package example.mongodb;
//
// import com.mongodb.client.MongoClient;
// import com.mongodb.client.MongoClients;
//
// // 单例模式实现
// public class MongoDbClient {
//
//     private static MongoClient mongoClient = null;
//
//     // 私有构造函数防止外部实例化
//     private MongoDbClient() {}
//
//     // 获取MongoClient实例的方法
//     public static MongoClient getMongoClient() {
//         if (mongoClient == null) {
//             synchronized (MongoDbClient.class) {
//                 // 双重检查锁定：是在实现单例模式时用来减少同步带来的开销的一种技术。其目的是在保证线程安全的同时提高性能。
//                 if (mongoClient == null) {
//                     try {
//                         String host = MongoDBConfig.HOST;
//                         int port = MongoDBConfig.PORT;
//                         mongoClient = MongoClients.create("mongodb://" + MongoDBConfig.HOST + ":" + MongoDBConfig.PORT);
//                         // 添加JVM关闭钩子以关闭MongoClient
//                         Runtime.getRuntime().addShutdownHook(new Thread(() -> {
//                             System.out.println("Closing MongoClient.");
//                             mongoClient.close();
//                         }));
//                     } catch (Exception e) {
//                         throw new RuntimeException("Failed to initialize MongoClient.", e);
//                     }
//                 }
//             }
//         }
//         return mongoClient;
//     }
// }

/**
 * 第一次检查（外层if）：这一检查是为了避免在每次调用getMongoClient()方法时都需要进行同步。如果实例已经被创建（绝大多数情况下），就直接返回实例，避免了进入同步块，提高了方法的执行效率。
 *
 * 同步块（synchronized）：只有当实例尚未被创建时，才进入同步块。同步块确保在多线程环境中只有一个线程可以进入代码块创建实例。这是保证线程安全的关键步骤。
 *
 * 第二次检查（内层if）：这一检查是为了确保在当前线程等待进入同步块期间，没有其他线程已经创建了实例。如果不进行这一检查，就可能在实例已经被另一个线程创建后，当前线程再次创建实例，这样就违背了单例模式的原则。**/