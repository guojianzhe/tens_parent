package com.tensquare.sms.listener;

import com.aliyuncs.exceptions.ClientException;
import com.tensquare.sms.util.SmsUtil;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RabbitListener(queues = {"sms"})
public class SmsListener {
    @Value("${aliyun.sms.template}")
    private String templateCode;
    @Value("${aliyun.sms.sign}")
    private String sign;

    @RabbitHandler
    public void reciveMsg(Map<String,String> map){
        map.get("phoneNumber");

        System.out.println("手机号是:"+map.get("phoneNumber")+"____验证码是:"+map.get("checkCode"));

        try {
            SmsUtil.sendSms(map.get("phoneNumber"),templateCode,sign,"{\"code\":\""+map.get("checkCode")+"\"}");
        } catch (ClientException e) {
            e.printStackTrace();
        }

    }

}
