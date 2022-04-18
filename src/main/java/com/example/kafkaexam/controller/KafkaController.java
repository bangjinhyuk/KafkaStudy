package com.example.kafkaexam.controller;

import com.example.kafkaexam.service.KafkaProducer;
import com.example.kafkaexam.service.Producer;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.listener.MessageListenerContainer;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/kafka")
@RequiredArgsConstructor
public class KafkaController {
    private final Producer nonAutoProducer;
    private final KafkaProducer autoProducer;
    private final KafkaListenerEndpointRegistry kafkaListenerEndpointRegistry;

    @PostMapping("non-auto")
    public String sendMessage(@RequestParam("key") String key, @RequestParam("value") String value) {
        this.nonAutoProducer.sendMessage(key,value);

        return "success";
    }

    @PostMapping("/auto")
    public String sendAutoMessage(@RequestParam("key") String key, @RequestParam("value") String value) {
        this.autoProducer.sendMessage(key,value);

        return "success";
    }

    @GetMapping
    public String consumeMessage(@RequestParam("id") String id) {
        MessageListenerContainer listenerContainer = kafkaListenerEndpointRegistry.getListenerContainer(id);
        listenerContainer.start();

        return "success";
    }
}
