package com.huzhuhua.aliyun.openservices.tcp.consumer;

import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.order.ConsumeOrderContext;
import com.aliyun.openservices.ons.api.order.MessageOrderListener;
import com.aliyun.openservices.ons.api.order.OrderAction;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.InsertOneResult;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MessageOrderListenerImpl implements MessageOrderListener {
    private static final Logger logger = LoggerFactory.getLogger(MessageOrderListenerImpl.class);

    private final MongoCollection<Document> collection;

    @Autowired
    public MessageOrderListenerImpl(MongoClient mongoClient,
                                    @Value("${mongodb.database}") String databaseName,
                                    @Value("${mongodb.collection}") String collectionName) {
        MongoDatabase database = mongoClient.getDatabase(databaseName);
        this.collection = database.getCollection(collectionName);
        // System.out.println("注入了");
    }

    @Override
    public OrderAction consume(final Message message, final ConsumeOrderContext context) {
        try {
            String body = new String(message.getBody(), "UTF-8");
            logger.info("Received message: {}", body);
            // System.out.println(body);

            if (body.isEmpty()) {
                logger.warn("Received empty message body.");
                return OrderAction.Success; // Or consider using OrderAction.Suspend if this condition should not occur
            }

            Document doc = Document.parse(body);
            // List<Document> documents = new ArrayList<>();
            // documents.add(doc);
            // collection.insertMany(documents);
            InsertOneResult insertOneResult = collection.insertOne(doc);
            logger.info("Inserted documents into MongoDB"+insertOneResult.getInsertedId());

        } catch (Exception e) {
            logger.error("Error processing message: ", e);
            return OrderAction.Suspend; // Suspend the message for retry if processing failed
        }
        return OrderAction.Success; // Message processed successfully
    }
}
