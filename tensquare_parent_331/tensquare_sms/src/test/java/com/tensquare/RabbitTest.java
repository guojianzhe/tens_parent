package com.tensquare;

import com.tensquare.sms.SmsApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = SmsApplication.class)
@RunWith(SpringRunner.class)
public class RabbitTest {


    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test  //直接模式发送消息
    public void fun1(){
        rabbitTemplate.convertAndSend("q1","hello word");
    }


    @Test  //分列模式发送消息
    public void fun2(){
        //参数1:指定将消息发送给那个路由
        rabbitTemplate.convertAndSend("q1&q2","","hello everyBody");
    }

    @Test  //主题模式发送消息
    public void fun3(){
        //参数1:指定将消息发送给那个路由
//        rabbitTemplate.convertAndSend("topic_ex","ds.hehe","hello everyBody");
        rabbitTemplate.convertAndSend("topic_ex","haha.hehe","hello everyBody");
    }


}
