package com.nnnn.ddd.infrastructure.distributed.redisson;
import java.util.concurrent.TimeUnit;

/**
 * Interface for Redis-based distributed lock.
 * This interface provides methods to acquire, release,
 * and query the status of a distributed lock.
 */
public interface RedisDistributedLocker {

    /**
     * Tries to acquire the lock within the given waiting time.
     * If acquired, the lock will be held for the specified lease time before automatic release.
     *
     * @param waitTime the maximum time to wait to acquire the lock
     * @param leaseTime the time to hold the lock after granting it (auto-release after this)
     * @param unit the time unit for waitTime and leaseTime
     * @return true if the lock was acquired within the wait time, false otherwise
     * @throws InterruptedException if the current thread is interrupted while waiting
     */
    boolean tryLock(long waitTime, long leaseTime, TimeUnit unit) throws InterruptedException;

    /**
     * Acquires the lock and holds it for the specified lease time.
     * This method will block indefinitely until the lock is acquired.
     * The lock will be automatically released after the lease time expires.
     *
     * @param leaseTime the time to hold the lock after granting it
     * @param unit the time unit for leaseTime
     */
    void lock(long leaseTime, TimeUnit unit);

    /**
     * Releases the lock.
     * If the lock is not held by the current thread, this may throw an exception or be a no-op,
     * depending on implementation.
     */
    void unlock();

    /**
     * Checks if the lock is currently acquired by any thread or process.
     *
     * @return true if the lock is currently held, false otherwise
     */
    boolean isLocked();

    /**
     * Checks if the lock is held by the thread with the given thread ID.
     *
     * @param threadId the ID of the thread to check
     * @return true if the lock is held by the thread with threadId, false otherwise
     */
    boolean isHeldByThread(long threadId);

    /**
     * Checks if the lock is held by the current thread.
     *
     * @return true if the current thread holds the lock, false otherwise
     */
    boolean isHeldByCurrentThread();
}

