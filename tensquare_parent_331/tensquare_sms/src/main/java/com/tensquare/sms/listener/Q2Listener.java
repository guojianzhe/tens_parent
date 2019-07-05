package com.tensquare.sms.listener;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = {"q2"})
public class Q2Listener {

    @RabbitHandler
    public void reciveMsg(String msg){
        System.out.println("从Q2队列中取出:"+msg);
    }

}
