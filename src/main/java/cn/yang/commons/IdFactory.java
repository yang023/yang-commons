package cn.yang.commons;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author yang 2019/6/17
 */
public class IdFactory {

    private long workerId;
    private long dataCenterId;

    public IdFactory(long workerId, long dataCenterId) {
        this.workerId = workerId;
        this.dataCenterId = dataCenterId;
    }

    private static Map<Class<?>, IdWorker> WORKER_CENTER = new ConcurrentHashMap<>();

    public IdWorker getWorker(Class<?> type) {
        IdWorker idWorker = WORKER_CENTER.get(type);
        if (Objects.isNull(idWorker)) {
            long dataCenterId = ~(-1L << (long) type.toString().hashCode());
            idWorker = new IdWorker(workerId & this.dataCenterId, dataCenterId % IdWorker.getMaxDataCenterId());
            WORKER_CENTER.put(type, idWorker);
        }

        return idWorker;
    }

}
