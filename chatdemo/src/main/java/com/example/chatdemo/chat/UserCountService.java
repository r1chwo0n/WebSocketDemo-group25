package com.example.chatdemo.chat;

import org.springframework.stereotype.Service;
import java.util.concurrent.atomic.AtomicInteger;
@Service
public class UserCountService {
    private final AtomicInteger count = new AtomicInteger(0);
    public int incrementCount() {
        return count.incrementAndGet();
    }
    public int decrementCount() {
        return count.decrementAndGet();
    }
}