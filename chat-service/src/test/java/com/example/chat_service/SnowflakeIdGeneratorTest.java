package com.example.chat_service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import com.example.chat_service.id_generator.SnowflakeIdGenerator;

public class SnowflakeIdGeneratorTest {
    @Test 
    public void testGenerateId() {
        SnowflakeIdGenerator idGenerator = new SnowflakeIdGenerator();
        long id = idGenerator.generateId();
        Set<Long> generatedIds = new HashSet<>();
        generatedIds.add(id);
        for (int i = 0; i < 1000; i++) {
            long newId = idGenerator.generateId();
            System.out.println("Generated ID: " + newId);
            generatedIds.add(newId);
        }
        assertEquals(1001, generatedIds.size());
    }
    // @Test
    // public void add(int a,int b){
    //     a=2;
    //     b=3;
    //     assertEquals(5,a+b);
    // }
    

}
