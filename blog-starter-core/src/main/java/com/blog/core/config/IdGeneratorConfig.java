package com.blog.core.config;


import com.blog.core.system.common.util.IdWorker;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class IdGeneratorConfig {
    @Value("${IdWorker.workerId}")
    private Long workerId;

    @Value("${IdWorker.datacenterId}")
    private Long datacenterId;

    public Long getWorkerId() {
        return workerId;
    }

    public void setWorkerId(Long workerId) {
        this.workerId = workerId;
    }

    public Long getDatacenterId() {
        return datacenterId;
    }

    public void setDatacenterId(Long datacenterId) {
        this.datacenterId = datacenterId;
    }

    public long getId(){
        IdWorker idWorker = new IdWorker(this.workerId,this.datacenterId);
        return idWorker.nextId();
    }
}
