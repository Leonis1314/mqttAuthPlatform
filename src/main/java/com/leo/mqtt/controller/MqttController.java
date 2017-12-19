package com.leo.mqtt.controller;

import com.leo.mqtt.model.MqttAcl;
import com.leo.mqtt.service.MqttService;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "/mqtt")
public class MqttController{

    private static Logger log = LoggerFactory.getLogger(MqttController.class);

    @Autowired
    MqttService mqttServiceImpl;

    @RequestMapping(value = "/auth", method = RequestMethod.POST)
    public void authCheck(@RequestParam String clientid, @RequestParam String username, @RequestParam String password, HttpServletResponse response){

        log.info("MqttController.authCheck: 普通用户；clientid = " + clientid + "; username = " + username + "; password = " + password);
        if (mqttServiceImpl.checkUser(clientid, username, password)) {
            response.setStatus(200);
        } else {
            response.setStatus(401);
        }
    }

    @RequestMapping(value = "/superuser", method = RequestMethod.POST)
    public void superuserCheck(@RequestParam String clientid, @RequestParam String username, HttpServletResponse response){

        log.info("MqttController.authCheck: clientid = " + clientid + "; username = " + username);
        if (mqttServiceImpl.checkSuperuser(clientid, username)) {
            response.setStatus(200);
        } else {
            response.setStatus(401);
        }
    }

    @RequestMapping(value = "/acl", method = RequestMethod.GET)
    public @ResponseBody
    JSONObject aclCheack(@RequestParam String clientid, @RequestParam String username,
                         @RequestParam String access, @RequestParam String ipaddr, @RequestParam String topic, HttpServletResponse response){
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        JSONObject json = new JSONObject();
        try{
            paramsMap.put("clientid", clientid);
            paramsMap.put("username", username);
            paramsMap.put("access", Integer.valueOf(access));
//        paramsMap.put("ipaddr", ipaddr);
            paramsMap.put("topic", topic);
            log.info("MqttController.authCheck: paramsMap = " + JSONObject.fromObject(paramsMap).toString() + "; ipaddr = " + ipaddr);
            if(topic.contains("#")){
                topic = topic.substring(0, topic.indexOf("#"));
            }
            MqttAcl mqttAcl = mqttServiceImpl.getMqttAclByMap(paramsMap);
            if(mqttAcl != null){
                if(mqttAcl.getAllow() == 1){
                    json = JSONObject.fromObject(mqttAcl);
                    response.setStatus(200);
                }else{
                    response.setStatus(401);
                }
            }else{
                response.setStatus(401);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return json;
    }
}
