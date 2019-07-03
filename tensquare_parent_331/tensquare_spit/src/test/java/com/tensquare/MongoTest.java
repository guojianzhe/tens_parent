package com.tensquare;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.junit.Test;


public class MongoTest {

    @Test
    public void fun1(){
        MongoClient client = new MongoClient("47.93.184.17");

        MongoDatabase spitdb = client.getDatabase("spitdb");

        MongoCollection<Document> spit = spitdb.getCollection("spit");


        FindIterable<Document> documents = spit.find();
        for (Document document : documents) {
            System.out.println(document.get("_id"));
        }


    }
}
