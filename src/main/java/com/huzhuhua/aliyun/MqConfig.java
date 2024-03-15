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
package com.aliyun;

/**
 * MQ 配置
 */
public class MqConfig {
    /**
     * 启动测试之前请替换如下 XXX 为您的配置
     */
    public static final String TOPIC = "sg_ax_beidou_track";
    public static final String GROUP_ID = "GID_sg_beidou_ZFW";
    public static final String ORDER_TOPIC = "sg_ax_beidou_track";
    public static final String ORDER_GROUP_ID = "GID_sg_beidou_ZFW";
    public static final String ACCESS_KEY = "6f181321efd9449ba45d2c69796b17f5";
    public static final String SECRET_KEY = "OopH5XhRhlZfCg/O7iFaWotHkLQ=";
    public static final String TAG = null;

    /**
     * NAMESRV_ADDR, 请在mq控制台 https://ons.console.aliyun.com 通过"实例管理--获取接入点信息--TCP协议接入点"获取
     */
    public static final String NAMESRV_ADDR = "http://mq.namesrv.paas.sgpt.gov:9876";

}
