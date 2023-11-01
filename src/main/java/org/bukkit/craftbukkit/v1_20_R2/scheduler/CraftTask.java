package org.bukkit.craftbukkit.v1_20_R2.scheduler;

import java.util.function.Consumer;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_20_R2.SpigotTimings;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;
import org.spigotmc.CustomTimingsHandler;

public class CraftTask implements BukkitTask, Runnable {

    private volatile CraftTask next;
    public static final int ERROR = 0;
    public static final int NO_REPEATING = -1;
    public static final int CANCEL = -2;
    public static final int PROCESS_FOR_FUTURE = -3;
    public static final int DONE_FOR_FUTURE = -4;
    private volatile long period;
    private long nextRun;
    private final Runnable rTask;
    private final Consumer cTask;
    private final Plugin plugin;
    private final int id;
    private final long createdAt;
    final CustomTimingsHandler timings;

    CraftTask() {
        this((Plugin) null, (Object) null, -1, -1L);
    }

    CraftTask(Object task) {
        this((Plugin) null, task, -1, -1L);
    }

    CraftTask(Plugin plugin, Object task, int id, long period) {
        this.next = null;
        this.createdAt = System.nanoTime();
        this.plugin = plugin;
        if (task instanceof Runnable) {
            this.rTask = (Runnable) task;
            this.cTask = null;
        } else if (task instanceof Consumer) {
            this.cTask = (Consumer) task;
            this.rTask = null;
        } else {
            if (task != null) {
                throw new AssertionError("Illegal task class " + task);
            }

            this.rTask = null;
            this.cTask = null;
        }

        this.id = id;
        this.period = period;
        this.timings = this.isSync() ? SpigotTimings.getPluginTaskTimings(this, period) : null;
    }

    public final int getTaskId() {
        return this.id;
    }

    public final Plugin getOwner() {
        return this.plugin;
    }

    public boolean isSync() {
        return true;
    }

    public void run() {
        if (this.rTask != null) {
            this.rTask.run();
        } else {
            this.cTask.accept(this);
        }

    }

    long getCreatedAt() {
        return this.createdAt;
    }

    long getPeriod() {
        return this.period;
    }

    void setPeriod(long period) {
        this.period = period;
    }

    long getNextRun() {
        return this.nextRun;
    }

    void setNextRun(long nextRun) {
        this.nextRun = nextRun;
    }

    CraftTask getNext() {
        return this.next;
    }

    void setNext(CraftTask next) {
        this.next = next;
    }

    Class getTaskClass() {
        return this.rTask != null ? this.rTask.getClass() : (this.cTask != null ? this.cTask.getClass() : null);
    }

    public boolean isCancelled() {
        return this.period == -2L;
    }

    public void cancel() {
        Bukkit.getScheduler().cancelTask(this.id);
    }

    boolean cancel0() {
        this.setPeriod(-2L);
        return true;
    }

    public String getTaskName() {
        return this.getTaskClass() == null ? "Unknown" : this.getTaskClass().getName();
    }
}
