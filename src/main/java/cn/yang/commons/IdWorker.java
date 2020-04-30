package cn.yang.commons;

/**
 * @author yang
 */
public final class IdWorker {

    private long workerId;
    private long dataCenterId;
    private long sequence = 0L;

    private static long workerIdBits = 5L;
    private static long dataCenterIdBits = 5L;
    private static long sequenceBits = 12L;

    private long workerIdShift = sequenceBits;
    private long dataCenterIdShift = sequenceBits + workerIdBits;
    private long timestampLeftShift = sequenceBits + workerIdBits + dataCenterIdBits;
    private long sequenceMask = ~(-1L << sequenceBits);

    private long lastTimestamp = -1L;

    public static long getMaxWorkerIdBits() {
        return ~(-1L << workerIdBits);
    }

    public static long getMaxDataCenterId() {
        return ~(-1L << dataCenterIdBits);
    }

    /**
     * id 生成器
     *
     * @param workerId     主机id
     * @param dataCenterId 数据中心id
     */
    public IdWorker(long workerId, long dataCenterId) {
        // sanity check for workerId
        long maxWorkerId = getMaxWorkerIdBits();
        if (workerId > maxWorkerId || workerId < 0) {
            throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0", maxWorkerId));
        }
        long maxDataCenterId = getMaxDataCenterId();
        if (dataCenterId > maxDataCenterId || dataCenterId < 0) {
            throw new IllegalArgumentException(String.format("dataCenter Id can't be greater than %d or less than 0", maxDataCenterId));
        }
        this.workerId = workerId;
        this.dataCenterId = dataCenterId;
    }

    /**
     * 获取 id
     *
     * @return long型分布式id
     */
    public synchronized long nextId() {
        long timestamp = timeGen();

        if (timestamp < lastTimestamp) {
            throw new RuntimeException(String.format("Clock moved backwards. Refusing to generate id for %d milliseconds", lastTimestamp - timestamp));
        }

        if (lastTimestamp == timestamp) {
            sequence = (sequence + 1) & sequenceMask;
            if (sequence == 0) {
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            sequence = 0L;
        }

        lastTimestamp = timestamp;

        long twEpoch = 1288834974657L;
        return ((timestamp - twEpoch) << timestampLeftShift) | (dataCenterId << dataCenterIdShift) | (workerId << workerIdShift) | sequence;
    }

    private long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    private long timeGen() {
        return System.currentTimeMillis();
    }

}