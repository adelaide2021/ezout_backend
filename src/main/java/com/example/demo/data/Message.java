package com.example.demo.data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;

@Data
@Document(collection = "messages")
public class Message {
    @Id
    private String id;
    private String sessionId;
    private String shopId;
    private String shopperId;
    private Action actionId;
    private String create_time;
    private String action;
    private String product_name;
    private String product_id;
    private String product_price;
    private String UPC;
    private String category_id;
    private String basket_total;
}

class Action {
    private String $numberInt;
}

