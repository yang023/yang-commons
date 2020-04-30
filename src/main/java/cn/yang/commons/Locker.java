package cn.yang.commons;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * <p> {@link ReentrantLock} 的包装实现</p>
 * 
 * <pre>
 * try (Locker.Lock lock = locker.lock())
 * {
 *   // something 
 * }
 * </pre>
 */
public class Locker
{
    private final ReentrantLock lock = new ReentrantLock();
    private final Lock unlock = new Lock();

    /**
     * <p>Acquires the lock.</p>
     *
     * @return the lock to unlock
     */
    public Lock lock()
    {
        lock.lock();
        return unlock;
    }


    /**
     * @return whether this lock has been acquired
     */
    public boolean isLocked()
    {
        return lock.isLocked();
    }

    /**
     * @return a {@link Condition} associated with this lock
     */
    public Condition newCondition()
    {
        return lock.newCondition();
    }

    /**
     * <p>The unlocker object that unlocks when it is closed.</p>
     */
    public class Lock implements AutoCloseable
    {
        @Override
        public void close()
        {
            lock.unlock();
        }
    }

}