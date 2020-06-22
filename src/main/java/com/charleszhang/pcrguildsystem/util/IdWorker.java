package com.charleszhang.pcrguildsystem.util;

/**
 * @author Charles Zhang
 * <p>
 * Random ID by Snow Flake
 */
public class IdWorker {

    private final long workerIdBits = 5L;
    private final long dataCenterIdBits = 5L;

    private final long workerId;
    private final long dataCenterId;
    private long sequence = 0L;
    private long lastTimestamp = -1L;

    public IdWorker(long workerId, long dataCenterId) {
        long maxWorkerId = ~(-1L << workerIdBits);
        if (workerId > maxWorkerId || workerId < 0) {
            throw new IllegalArgumentException(String.format("Worker Id can't be greater than %d or less than 0", maxWorkerId));
        }
        long maxDataCenterId = ~(-1L << dataCenterIdBits);
        if (dataCenterId > maxDataCenterId || dataCenterId < 0) {
            throw new IllegalArgumentException(String.format("Data center Id can't be greater than %d or less than 0", maxDataCenterId));
        }
        this.workerId = workerId;
        this.dataCenterId = dataCenterId;
    }

    public synchronized long nextId() {
        long timestamp = timeGen();
        if (timestamp < lastTimestamp) {
            throw new RuntimeException(String.format("Clock moved backwards. Refusing to generate id for %d milliseconds", lastTimestamp - timestamp));
        }
        long sequenceBits = 12L;
        if (lastTimestamp == timestamp) {
            long sequenceMask = ~(-1L << sequenceBits);
            sequence = (sequence + 1) & sequenceMask;
            if (sequence == 0) {
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            sequence = 0L;
        }

        lastTimestamp = timestamp;

        long epoch = 1288834974657L;
        long timestampLeftShift = sequenceBits + workerIdBits + dataCenterIdBits;
        long dataCenterIdShift = sequenceBits + workerIdBits;
        return ((timestamp - epoch) << timestampLeftShift) | (dataCenterId << dataCenterIdShift) | (workerId << sequenceBits) | sequence;
    }

    protected long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    protected long timeGen() {
        return System.currentTimeMillis();
    }

}
