package com.leo.mqtt.dao;

import com.leo.mqtt.model.MqttAcl;

import java.util.Map;

public interface MqttAclMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MqttAcl record);

    int insertSelective(MqttAcl record);

    MqttAcl selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MqttAcl record);

    int updateByPrimaryKey(MqttAcl record);

    MqttAcl getMqttAclByMap(Map<String, Object> map);

    void deleteMqttAclByMap(Map<String, Object> map);
}