package com.leo.mqtt.dao;

import com.leo.mqtt.core.generic.GenericDao;
import com.leo.mqtt.model.MqttUser;

import java.util.Map;

public interface MqttUserMapper extends GenericDao<MqttUser, Long> {
    int deleteByPrimaryKey(Long id);

    int insert(MqttUser record);

    int insertSelective(MqttUser record);

    MqttUser selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(MqttUser record);

    int updateByPrimaryKey(MqttUser record);

    MqttUser selectByUserName(String thirdpartyUserId);

    MqttUser selectByUserNameAndPassword(Map<String, String> map);
}