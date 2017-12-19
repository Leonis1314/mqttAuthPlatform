package com.leo.mqtt.service;

import com.leo.mqtt.core.generic.GenericService;
import com.leo.mqtt.model.MqttUser;

/**
 * mqtt-broker用户
 * @since 2017年11月5日 上午11:53:33
 **/
public interface MqttUserService extends GenericService<MqttUser, Long> {


    MqttUser selectByUserName(String thirdpartyUserId);
}
