package org.bukkit.craftbukkit.v1_20_R2.util;

import com.google.common.base.Preconditions;
import java.util.concurrent.ExecutionException;

public abstract class Waitable implements Runnable {

    Throwable t = null;
    Object value = null;
    Waitable.Status status;

    public Waitable() {
        this.status = Waitable.Status.WAITING;
    }

    public final void run() {
        synchronized (this) {
            Preconditions.checkState(this.status == Waitable.Status.WAITING, "Invalid state %s", this.status);
            this.status = Waitable.Status.RUNNING;
        }

        try {
            this.value = this.evaluate();
        } catch (Throwable throwable) {
            this.t = throwable;
        } finally {
            synchronized (this) {
                this.status = Waitable.Status.FINISHED;
                this.notifyAll();
            }
        }

    }

    protected abstract Object evaluate();

    public synchronized Object get() throws InterruptedException, ExecutionException {
        while (this.status != Waitable.Status.FINISHED) {
            this.wait();
        }

        if (this.t != null) {
            throw new ExecutionException(this.t);
        } else {
            return this.value;
        }
    }

    private static enum Status {

        WAITING, RUNNING, FINISHED;
    }
}
