package com.leo.mqtt.service.impl;

import com.leo.mqtt.dao.MqttAclMapper;
import com.leo.mqtt.dao.MqttUserMapper;
import com.leo.mqtt.model.MqttAcl;
import com.leo.mqtt.model.MqttUser;
import com.leo.mqtt.service.MqttService;
import com.leo.mqtt.util.HexUtil;
import com.leo.mqtt.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service("mqttServiceImpl")
public class MqttServiceImpl implements MqttService {

    private static final Logger log = LoggerFactory.getLogger(MqttServiceImpl.class);

    @Autowired
    MqttUserMapper mqttUserMapper;

    @Autowired
    MqttAclMapper mqttAclMapper;

    @Override
    public boolean checkUser(String clientid, String username, String password) {
        log.info("this is MqttServiceImpl checkUser !");
        //TODO clientid暂时未启用
        Map<String, String> paramsMap = new HashMap<String, String>();
        paramsMap.put("username", username);
//        paramsMap.put("password", password);
        if(StringUtil.isBlank(username) || StringUtil.isBlank(password)){
            log.info("mqttUser.username is blank !");
            return false;
        }
        log.info("this is MqttServiceImpl checkUser username = " + username);
        MqttUser mqttUser = mqttUserMapper.selectByUserNameAndPassword(paramsMap);
        if(mqttUser != null){
            if(password.equals(mqttUser.getClearPassword())){
                String passWordParam = HexUtil.getSHA256Str(password + mqttUser.getSalt());
                if(passWordParam.equals(mqttUser.getPassword())){
                    log.info("mqttUser.password is illegal !");
                    return true;
                }
            }else{
                log.info("mqttUser.clear_password is illegal !");
                return false;
            }

        }
        log.info("mqttUser is null or otherException !");
        return false;
    }

    @Override
    public boolean checkSuperuser(String clientid, String username) {
        log.info("this is MqttServiceImpl checkSuperuser !");
        //TODO clientid暂未启用
        Map<String, String> paramsMap = new HashMap<String, String>();
        paramsMap.put("username", username);
        log.info("this is MqttServiceImpl checkUser username = " + username);
        if(StringUtil.isBlank(username)){
            return false;
        }
        MqttUser mqttUser = mqttUserMapper.selectByUserNameAndPassword(paramsMap);
        if(mqttUser != null){
            if(mqttUser.getIsSuperuser()){
                log.info("mqttUser is superuser !");
                return true;
            }
        }
        log.info("mqttUser is null or otherException !");
        return false;
    }

    @Override
    public MqttAcl getMqttAclByMap(Map<String, Object> map) {
        return mqttAclMapper.getMqttAclByMap(map);
    }
}
