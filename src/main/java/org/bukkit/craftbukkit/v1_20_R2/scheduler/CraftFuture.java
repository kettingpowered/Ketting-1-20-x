package org.bukkit.craftbukkit.v1_20_R2.scheduler;

import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.bukkit.plugin.Plugin;

class CraftFuture extends CraftTask implements Future {

    private final Callable callable;
    private Object value;
    private Exception exception = null;

    CraftFuture(Callable callable, Plugin plugin, int id) {
        super(plugin, (Object) null, id, -1L);
        this.callable = callable;
    }

    public synchronized boolean cancel(boolean mayInterruptIfRunning) {
        if (this.getPeriod() != -1L) {
            return false;
        } else {
            this.setPeriod(-2L);
            return true;
        }
    }

    public boolean isDone() {
        long period = this.getPeriod();

        return period != -1L && period != -3L;
    }

    public Object get() throws CancellationException, InterruptedException, ExecutionException {
        try {
            return this.get(0L, TimeUnit.MILLISECONDS);
        } catch (TimeoutException timeoutexception) {
            throw new Error(timeoutexception);
        }
    }

    public synchronized Object get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        timeout = unit.toMillis(timeout);
        long period = this.getPeriod();
        long timestamp = timeout > 0L ? System.currentTimeMillis() : 0L;

        while (period == -1L || period == -3L) {
            this.wait(timeout);
            period = this.getPeriod();
            if (period != -1L && period != -3L) {
                break;
            }

            if (timeout != 0L) {
                timeout += timestamp - (timestamp = System.currentTimeMillis());
                if (timeout <= 0L) {
                    throw new TimeoutException();
                }
            }
        }

        if (period == -2L) {
            throw new CancellationException();
        } else if (period == -4L) {
            if (this.exception == null) {
                return this.value;
            } else {
                throw new ExecutionException(this.exception);
            }
        } else {
            throw new IllegalStateException("Expected -1 to -4, got " + period);
        }
    }

    public void run() {
        synchronized (this) {
            if (this.getPeriod() == -2L) {
                return;
            }

            this.setPeriod(-3L);
        }

        try {
            this.value = this.callable.call();
        } catch (Exception exception) {
            this.exception = exception;
        } finally {
            synchronized (this) {
                this.setPeriod(-4L);
                this.notifyAll();
            }
        }

    }

    synchronized boolean cancel0() {
        if (this.getPeriod() != -1L) {
            return false;
        } else {
            this.setPeriod(-2L);
            this.notifyAll();
            return true;
        }
    }
}
