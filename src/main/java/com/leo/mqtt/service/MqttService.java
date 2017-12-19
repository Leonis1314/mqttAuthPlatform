package com.leo.mqtt.service;

import com.leo.mqtt.model.MqttAcl;

import java.util.Map;

public interface MqttService {

    /**
     * 验证mqtt-broker用户名/密码合法性
     * @param clientid
     * @param username
     * @param password
     * @return
     */
    public boolean checkUser(String clientid, String username, String password);

    /**
     * 验证mqtt-broker连接用户是否为管理员权限
     * @param clientid
     * @param username
     * @return
     */
    public boolean checkSuperuser(String clientid, String username);

    /**
     * 根据参数查询client对topic的权限
     * @param map
     * @return
     */
    MqttAcl getMqttAclByMap(Map<String, Object> map);
}
