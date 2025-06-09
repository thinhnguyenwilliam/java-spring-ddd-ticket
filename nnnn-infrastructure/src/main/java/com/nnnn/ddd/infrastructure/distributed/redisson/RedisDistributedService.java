package com.nnnn.ddd.infrastructure.distributed.redisson;

/**
 * Service interface to get Redis-based distributed locks.
 * Each lock is identified by a unique key.
 */
public interface RedisDistributedService {

    /**
     * Get a distributed lock object for the given key.
     *
     * @param lockKey the unique key identifying the distributed lock
     * @return a RedisDistributedLocker instance to manage the lock operations on the given key
     */
    RedisDistributedLocker getDistributedLock(String lockKey);
}
