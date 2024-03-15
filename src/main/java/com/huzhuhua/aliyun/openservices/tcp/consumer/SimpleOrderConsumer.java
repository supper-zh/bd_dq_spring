/**
 * Copyright (C) 2010-2016 Alibaba Group Holding Limited
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.huzhuhua.aliyun.openservices.tcp.consumer;

import com.aliyun.openservices.ons.api.ONSFactory;
import com.aliyun.openservices.ons.api.PropertyKeyConst;
import com.aliyun.openservices.ons.api.order.MessageOrderListener;
import com.aliyun.openservices.ons.api.order.OrderConsumer;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


import java.util.Properties;

/**
 * MQ 接收消息示例 Demo
 */

// @Component注解，Spring Boot会自动检测到这个类并将其作为一个Bean注册到Spring应用上下文中。
// 一旦BdDqSpringApplication启动并且Spring应用上下文被初始化，
// SimpleOrderConsumer中使用@PostConstruct注解的start方法就会被自动执行。
// 使用Spring的@Value注解来注入配置项
@Component
public class SimpleOrderConsumer {
    @Value("${mq.orderGroupId}")
    private String orderGroupId;

    @Value("${mq.accessKey}")
    private String accessKey;

    @Value("${mq.secretKey}")
    private String secretKey;

    @Value("${mq.namesrvAddr}")
    private String namesrvAddr;

    @Value("${mq.orderTopic}")
    private String orderTopic;

    @Value("${mq.tag:}") // 默认值为空字符串
    private String tag;

    private final MessageOrderListener messageOrderListener;

    // Spring推荐依赖于接口编程而不是具体实现
    // 通过构造函数注入MessageOrderListener，Spring会自动将其作为 MessageOrderListener 的一个实现MessageOrderListenerImpl注入到任何需要它的地方
    // MessageOrderListenerImpl 的 consume 方法将会被调用来处理接收到的消息。
    // 这是利用Spring依赖注入特性的典型用法，允许将具体的实现（在这个是 MessageOrderListenerImpl）解耦和动态地注入到使用它的组件中
    @Autowired
    public SimpleOrderConsumer(MessageOrderListener messageOrderListener) {
        this.messageOrderListener = messageOrderListener;
    }

    // @PostConstruct注解的方法会在Spring容器实例化相应的Bean之后、
    // 并完成依赖注入之前自动被调用，这意味着Spring将会自动调用SimpleOrderConsumer的start方法，而无需手动调用
    @PostConstruct
    public void start() {
        Properties consumerProperties = new Properties();
        consumerProperties.setProperty(PropertyKeyConst.GROUP_ID, orderGroupId);
        consumerProperties.setProperty(PropertyKeyConst.AccessKey, accessKey);
        consumerProperties.setProperty(PropertyKeyConst.SecretKey, secretKey);
        consumerProperties.setProperty(PropertyKeyConst.NAMESRV_ADDR, namesrvAddr);
        OrderConsumer consumer = ONSFactory.createOrderedConsumer(consumerProperties);
        // consumer.subscribe(orderTopic, tag, new MessageOrderListener() {
        //             @Override
        //             public OrderAction consume(final Message message, final ConsumeOrderContext context) {
        //                 try {
        //                     String body = new String(message.getBody(), "UTF-8");
        //                     System.out.println(body);
        //                 } catch (UnsupportedEncodingException e) {
        //                     e.printStackTrace();
        //                 }
        //                 return OrderAction.Success;
        //             }
        //         });
        // consumer.subscribe(orderTopic, tag, new MessageOrderListenerImpl());
        // // Spring推荐依赖于接口编程而不是具体实现，可以增加代码的灵活性和可维护性---依赖反转原则（Dependency Inversion Principle）和面向接口编程
        consumer.subscribe(orderTopic, tag, messageOrderListener); // 使用注入的messageOrderListener实例

        consumer.start();
        System.out.println("Consumer start success.");
    }
}
