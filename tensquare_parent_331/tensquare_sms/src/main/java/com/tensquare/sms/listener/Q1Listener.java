package com.tensquare.sms.listener;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = {"q1"})
public class Q1Listener {

    @RabbitHandler
    public void reciveMsg(String msg){
        System.out.println("从Q1队列中取出:"+msg);
    }

}
