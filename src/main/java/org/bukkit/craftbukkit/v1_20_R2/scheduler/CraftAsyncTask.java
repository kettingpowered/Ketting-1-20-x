package org.bukkit.craftbukkit.v1_20_R2.scheduler;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.logging.Level;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitWorker;

class CraftAsyncTask extends CraftTask {

    private final LinkedList workers = new LinkedList();
    private final Map runners;

    CraftAsyncTask(Map runners, Plugin plugin, Object task, int id, long delay) {
        super(plugin, task, id, delay);
        this.runners = runners;
    }

    public boolean isSync() {
        return false;
    }

    public void run() {
        final Thread thread = Thread.currentThread();
        LinkedList linkedlist = this.workers;

        synchronized (this.workers) {
            if (this.getPeriod() == -2L) {
                return;
            }

            this.workers.add(new BukkitWorker() {
                public Thread getThread() {
                    return thread;
                }

                public int getTaskId() {
                    return CraftAsyncTask.this.getTaskId();
                }

                public Plugin getOwner() {
                    return CraftAsyncTask.this.getOwner();
                }
            });
        }

        Throwable thrown = null;

        try {
            super.run();
        } catch (Throwable throwable) {
            thrown = throwable;
            this.getOwner().getLogger().log(Level.WARNING, String.format("Plugin %s generated an exception while executing task %s", this.getOwner().getDescription().getFullName(), this.getTaskId()), throwable);
        } finally {
            LinkedList linkedlist1 = this.workers;

            synchronized (this.workers) {
                try {
                    Iterator workers = this.workers.iterator();
                    boolean removed = false;

                    while (workers.hasNext()) {
                        if (((BukkitWorker) workers.next()).getThread() == thread) {
                            workers.remove();
                            removed = true;
                            break;
                        }
                    }

                    if (!removed) {
                        throw new IllegalStateException(String.format("Unable to remove worker %s on task %s for %s", thread.getName(), this.getTaskId(), this.getOwner().getDescription().getFullName()), thrown);
                    }
                } finally {
                    if (this.getPeriod() < 0L && this.workers.isEmpty()) {
                        this.runners.remove(this.getTaskId());
                    }

                }

            }
        }

    }

    LinkedList getWorkers() {
        return this.workers;
    }

    boolean cancel0() {
        LinkedList linkedlist = this.workers;

        synchronized (this.workers) {
            this.setPeriod(-2L);
            if (this.workers.isEmpty()) {
                this.runners.remove(this.getTaskId());
            }

            return true;
        }
    }
}
