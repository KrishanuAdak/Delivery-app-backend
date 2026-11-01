package com.example.chat_service.id_generator;

import org.springframework.stereotype.Service;

@Service
public class SnowflakeIdGenerator {
    private final long epoch = 1704067200000L;
    private final long workerIdBits = 5L;
    private final long datacenterBits = 5L;
    private final long sequenceIdBits = 12L;
    private final long maxWorkerId = -1L ^ (-1L << workerIdBits);
    private final long maxDatacenterId = -1L ^ (-1L << datacenterBits);
    private final long workerIdShift = sequenceIdBits;
    private final long datacenterIdShift = sequenceIdBits + workerIdBits;
    private final long timestampLeftShift = sequenceIdBits + workerIdBits + datacenterBits;
    private final long sequenceMask = -1L ^ (-1L << sequenceIdBits);
    private final long workerId = 1L;
    private final long datacenterId = 1L;
    private long lastTimestamp = -1L;
    private long sequence = 0L;

    @SuppressWarnings("unused")
    public synchronized long generateId() {
        if (workerId > maxWorkerId || workerId < 0) {
            throw new IllegalArgumentException(
                    String.format("Worker Id can't be greater than %d or less than 0", maxWorkerId));
        }
        if (datacenterId > maxDatacenterId || datacenterId < 0) {
            throw new IllegalArgumentException(
                    String.format("Datacenter Id can't be greater than %d or less than 0", maxDatacenterId));
        }
        long timestamp = System.currentTimeMillis();
        if (timestamp < lastTimestamp) {
            throw new RuntimeException("Clock is moving backwards. Rejecting requests until " + lastTimestamp);
        }
        if (lastTimestamp == timestamp) {
            sequence = (sequence + 1) & sequenceMask;
            if (sequence == 0) {
                timestamp = waitForNextMillis(lastTimestamp);
            }
        } else {
            sequence = 0;
        }
        lastTimestamp = timestamp;
        return (((timestamp - epoch) << timestampLeftShift) |
                (datacenterId << datacenterIdShift) |
                (workerId << workerIdShift) |
                sequence);
    }

    private long waitForNextMillis(long lastTimestamp) {
        long timestamp = System.currentTimeMillis();
        while (timestamp <= lastTimestamp) {
            timestamp = System.currentTimeMillis();
        }
        return timestamp;
    }

}
