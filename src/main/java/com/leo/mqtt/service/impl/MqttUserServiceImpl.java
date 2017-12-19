package com.leo.mqtt.service.impl;

import com.leo.mqtt.core.generic.GenericDao;
import com.leo.mqtt.core.generic.GenericServiceImpl;
import com.leo.mqtt.dao.MqttUserMapper;
import com.leo.mqtt.model.MqttUser;
import com.leo.mqtt.service.MqttUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 用户Service实现类
 *
 * @author
 * @since 2014年7月5日 上午11:54:24
 */
@Service
public class MqttUserServiceImpl extends GenericServiceImpl<MqttUser, Long> implements MqttUserService {

    public static  final Logger logger = LoggerFactory.getLogger(MqttUserServiceImpl.class);

    @Resource
    private MqttUserMapper mqttUserMapper;


    @Override
    public int insert(MqttUser model) {
        return mqttUserMapper.insertSelective(model);
    }

    @Override
    public int update(MqttUser model) {
        return mqttUserMapper.updateByPrimaryKeySelective(model);
    }

    @Override
    public int delete(Long id) {
        return mqttUserMapper.deleteByPrimaryKey(id);
    }


    @Override
    public MqttUser selectById(Long id) {
        return mqttUserMapper.selectByPrimaryKey(id);
    }

    @Override
    public GenericDao<MqttUser, Long> getDao() {
        return mqttUserMapper;
    }


    @Override
    public MqttUser selectByUserName(String thirdpartyUserId) {
        return mqttUserMapper.selectByUserName(thirdpartyUserId);
    }
}
