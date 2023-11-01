package org.bukkit.craftbukkit.v1_20_R2.scheduler;

import com.google.common.base.Preconditions;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import java.util.function.IntUnaryOperator;
import java.util.logging.Level;
import org.bukkit.plugin.IllegalPluginAccessException;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;

public class CraftScheduler implements BukkitScheduler {

    private static final int START_ID = 1;
    private static final IntUnaryOperator INCREMENT_IDS = (previousx) -> {
        return previousx == Integer.MAX_VALUE ? 1 : previousx + 1;
    };
    private final AtomicInteger ids = new AtomicInteger(1);
    private volatile CraftTask head = new CraftTask();
    private final AtomicReference tail;
    private final PriorityQueue pending;
    private final List temp;
    private final ConcurrentHashMap runners;
    private volatile CraftTask currentTask;
    private volatile int currentTick;
    private final Executor executor;
    private CraftAsyncDebugger debugHead;
    private CraftAsyncDebugger debugTail;
    private static final int RECENT_TICKS = 30;

    public CraftScheduler() {
        this.tail = new AtomicReference(this.head);
        this.pending = new PriorityQueue(10, new Comparator() {
            public int compare(CraftTask o1, CraftTask o2) {
                int value = Long.compare(o1.getNextRun(), o2.getNextRun());

                return value != 0 ? value : Long.compare(o1.getCreatedAt(), o2.getCreatedAt());
            }
        });
        this.temp = new ArrayList();
        this.runners = new ConcurrentHashMap();
        this.currentTask = null;
        this.currentTick = -1;
        this.executor = Executors.newCachedThreadPool((new ThreadFactoryBuilder()).setNameFormat("Craft Scheduler Thread - %d").build());
        this.debugHead = new CraftAsyncDebugger(-1, (Plugin) null, (Class) null) {
            StringBuilder debugTo(StringBuilder string) {
                return string;
            }
        };
        this.debugTail = this.debugHead;
    }

    public int scheduleSyncDelayedTask(Plugin plugin, Runnable task) {
        return this.scheduleSyncDelayedTask(plugin, task, 0L);
    }

    public BukkitTask runTask(Plugin plugin, Runnable runnable) {
        return this.runTaskLater(plugin, runnable, 0L);
    }

    public void runTask(Plugin plugin, Consumer task) throws IllegalArgumentException {
        this.runTaskLater(plugin, task, 0L);
    }

    /** @deprecated */
    @Deprecated
    public int scheduleAsyncDelayedTask(Plugin plugin, Runnable task) {
        return this.scheduleAsyncDelayedTask(plugin, task, 0L);
    }

    public BukkitTask runTaskAsynchronously(Plugin plugin, Runnable runnable) {
        return this.runTaskLaterAsynchronously(plugin, runnable, 0L);
    }

    public void runTaskAsynchronously(Plugin plugin, Consumer task) throws IllegalArgumentException {
        this.runTaskLaterAsynchronously(plugin, task, 0L);
    }

    public int scheduleSyncDelayedTask(Plugin plugin, Runnable task, long delay) {
        return this.scheduleSyncRepeatingTask(plugin, task, delay, -1L);
    }

    public BukkitTask runTaskLater(Plugin plugin, Runnable runnable, long delay) {
        return this.runTaskTimer(plugin, runnable, delay, -1L);
    }

    public void runTaskLater(Plugin plugin, Consumer task, long delay) throws IllegalArgumentException {
        this.runTaskTimer(plugin, task, delay, -1L);
    }

    /** @deprecated */
    @Deprecated
    public int scheduleAsyncDelayedTask(Plugin plugin, Runnable task, long delay) {
        return this.scheduleAsyncRepeatingTask(plugin, task, delay, -1L);
    }

    public BukkitTask runTaskLaterAsynchronously(Plugin plugin, Runnable runnable, long delay) {
        return this.runTaskTimerAsynchronously(plugin, runnable, delay, -1L);
    }

    public void runTaskLaterAsynchronously(Plugin plugin, Consumer task, long delay) throws IllegalArgumentException {
        this.runTaskTimerAsynchronously(plugin, task, delay, -1L);
    }

    public void runTaskTimerAsynchronously(Plugin plugin, Consumer task, long delay, long period) throws IllegalArgumentException {
        this.runTaskTimerAsynchronously(plugin, (Object) task, delay, -1L);
    }

    public int scheduleSyncRepeatingTask(Plugin plugin, Runnable runnable, long delay, long period) {
        return this.runTaskTimer(plugin, runnable, delay, period).getTaskId();
    }

    public BukkitTask runTaskTimer(Plugin plugin, Runnable runnable, long delay, long period) {
        return this.runTaskTimer(plugin, (Object) runnable, delay, period);
    }

    public void runTaskTimer(Plugin plugin, Consumer task, long delay, long period) throws IllegalArgumentException {
        this.runTaskTimer(plugin, (Object) task, delay, period);
    }

    public BukkitTask runTaskTimer(Plugin plugin, Object runnable, long delay, long period) {
        validate(plugin, runnable);
        if (delay < 0L) {
            delay = 0L;
        }

        if (period == 0L) {
            period = 1L;
        } else if (period < -1L) {
            period = -1L;
        }

        return this.handle(new CraftTask(plugin, runnable, this.nextId(), period), delay);
    }

    /** @deprecated */
    @Deprecated
    public int scheduleAsyncRepeatingTask(Plugin plugin, Runnable runnable, long delay, long period) {
        return this.runTaskTimerAsynchronously(plugin, runnable, delay, period).getTaskId();
    }

    public BukkitTask runTaskTimerAsynchronously(Plugin plugin, Runnable runnable, long delay, long period) {
        return this.runTaskTimerAsynchronously(plugin, (Object) runnable, delay, period);
    }

    public BukkitTask runTaskTimerAsynchronously(Plugin plugin, Object runnable, long delay, long period) {
        validate(plugin, runnable);
        if (delay < 0L) {
            delay = 0L;
        }

        if (period == 0L) {
            period = 1L;
        } else if (period < -1L) {
            period = -1L;
        }

        return this.handle(new CraftAsyncTask(this.runners, plugin, runnable, this.nextId(), period), delay);
    }

    public Future callSyncMethod(Plugin plugin, Callable task) {
        validate(plugin, task);
        CraftFuture future = new CraftFuture(task, plugin, this.nextId());

        this.handle(future, 0L);
        return future;
    }

    public void cancelTask(final int taskId) {
        if (taskId > 0) {
            CraftTask task = (CraftTask) this.runners.get(taskId);

            if (task != null) {
                task.cancel0();
            }

            task = new CraftTask(new Runnable() {
                public void run() {
                    if (!this.check(CraftScheduler.this.temp)) {
                        this.check(CraftScheduler.this.pending);
                    }

                }

                private boolean check(Iterable collection) {
                    Iterator tasks = collection.iterator();

                    while (tasks.hasNext()) {
                        CraftTask task = (CraftTask) tasks.next();

                        if (task.getTaskId() == taskId) {
                            task.cancel0();
                            tasks.remove();
                            if (task.isSync()) {
                                CraftScheduler.this.runners.remove(taskId);
                            }

                            return true;
                        }
                    }

                    return false;
                }
            });
            this.handle(task, 0L);

            for (CraftTask taskPending = this.head.getNext(); taskPending != null; taskPending = taskPending.getNext()) {
                if (taskPending == task) {
                    return;
                }

                if (taskPending.getTaskId() == taskId) {
                    taskPending.cancel0();
                }
            }

        }
    }

    public void cancelTasks(final Plugin plugin) {
        Preconditions.checkArgument(plugin != null, "Cannot cancel tasks of null plugin");
        CraftTask task = new CraftTask(new Runnable() {
            public void run() {
                this.check(CraftScheduler.this.pending);
                this.check(CraftScheduler.this.temp);
            }

            void check(Iterable collection) {
                Iterator tasks = collection.iterator();

                while (tasks.hasNext()) {
                    CraftTask task = (CraftTask) tasks.next();

                    if (task.getOwner().equals(plugin)) {
                        task.cancel0();
                        tasks.remove();
                        if (task.isSync()) {
                            CraftScheduler.this.runners.remove(task.getTaskId());
                        }
                    }
                }

            }
        });

        this.handle(task, 0L);

        CraftTask runner;

        for (runner = this.head.getNext(); runner != null && runner != task; runner = runner.getNext()) {
            if (runner.getTaskId() != -1 && runner.getOwner().equals(plugin)) {
                runner.cancel0();
            }
        }

        Iterator iterator = this.runners.values().iterator();

        while (iterator.hasNext()) {
            runner = (CraftTask) iterator.next();
            if (runner.getOwner().equals(plugin)) {
                runner.cancel0();
            }
        }

    }

    public boolean isCurrentlyRunning(int taskId) {
        CraftTask task = (CraftTask) this.runners.get(taskId);

        if (task == null) {
            return false;
        } else if (task.isSync()) {
            return task == this.currentTask;
        } else {
            CraftAsyncTask asyncTask = (CraftAsyncTask) task;

            synchronized (asyncTask.getWorkers()) {
                return !asyncTask.getWorkers().isEmpty();
            }
        }
    }

    public boolean isQueued(int taskId) {
        if (taskId <= 0) {
            return false;
        } else {
            CraftTask task;

            for (task = this.head.getNext(); task != null; task = task.getNext()) {
                if (task.getTaskId() == taskId) {
                    if (task.getPeriod() >= -1L) {
                        return true;
                    }

                    return false;
                }
            }

            task = (CraftTask) this.runners.get(taskId);
            return task != null && task.getPeriod() >= -1L;
        }
    }

    public List getActiveWorkers() {
        ArrayList workers = new ArrayList();
        Iterator iterator = this.runners.values().iterator();

        while (iterator.hasNext()) {
            CraftTask taskObj = (CraftTask) iterator.next();

            if (!taskObj.isSync()) {
                CraftAsyncTask task = (CraftAsyncTask) taskObj;

                synchronized (task.getWorkers()) {
                    workers.addAll(task.getWorkers());
                }
            }
        }

        return workers;
    }

    public List getPendingTasks() {
        ArrayList truePending = new ArrayList();

        for (CraftTask task = this.head.getNext(); task != null; task = task.getNext()) {
            if (task.getTaskId() != -1) {
                truePending.add(task);
            }
        }

        ArrayList pending = new ArrayList();
        Iterator iterator = this.runners.values().iterator();

        CraftTask task;

        while (iterator.hasNext()) {
            task = (CraftTask) iterator.next();
            if (task.getPeriod() >= -1L) {
                pending.add(task);
            }
        }

        iterator = truePending.iterator();

        while (iterator.hasNext()) {
            task = (CraftTask) iterator.next();
            if (task.getPeriod() >= -1L && !pending.contains(task)) {
                pending.add(task);
            }
        }

        return pending;
    }

    public void mainThreadHeartbeat(int currentTick) {
        this.currentTick = currentTick;
        List temp = this.temp;

        this.parsePending();

        while (this.isReady(currentTick)) {
            CraftTask task = (CraftTask) this.pending.remove();

            if (task.getPeriod() < -1L) {
                if (task.isSync()) {
                    this.runners.remove(task.getTaskId(), task);
                }

                this.parsePending();
            } else {
                if (task.isSync()) {
                    this.currentTask = task;

                    try {
                        task.timings.startTiming();
                        task.run();
                        task.timings.stopTiming();
                    } catch (Throwable throwable) {
                        task.getOwner().getLogger().log(Level.WARNING, String.format("Task #%s for %s generated an exception", task.getTaskId(), task.getOwner().getDescription().getFullName()), throwable);
                    } finally {
                        this.currentTask = null;
                    }

                    this.parsePending();
                } else {
                    this.debugTail = this.debugTail.setNext(new CraftAsyncDebugger(currentTick + CraftScheduler.RECENT_TICKS, task.getOwner(), task.getTaskClass()));
                    this.executor.execute(task);
                }

                long period = task.getPeriod();

                if (period > 0L) {
                    task.setNextRun((long) currentTick + period);
                    temp.add(task);
                } else if (task.isSync()) {
                    this.runners.remove(task.getTaskId());
                }
            }
        }

        this.pending.addAll(temp);
        temp.clear();
        this.debugHead = this.debugHead.getNextHead(currentTick);
    }

    private void addTask(CraftTask task) {
        AtomicReference tail = this.tail;

        CraftTask tailTask;

        for (tailTask = (CraftTask) tail.get(); !tail.compareAndSet(tailTask, task); tailTask = (CraftTask) tail.get()) {
            ;
        }

        tailTask.setNext(task);
    }

    private CraftTask handle(CraftTask task, long delay) {
        task.setNextRun((long) this.currentTick + delay);
        this.addTask(task);
        return task;
    }

    private static void validate(Plugin plugin, Object task) {
        Preconditions.checkArgument(plugin != null, "Plugin cannot be null");
        Preconditions.checkArgument(task instanceof Runnable || task instanceof Consumer || task instanceof Callable, "Task must be Runnable, Consumer, or Callable");
        if (!plugin.isEnabled()) {
            throw new IllegalPluginAccessException("Plugin attempted to register task while disabled");
        }
    }

    private int nextId() {
        Preconditions.checkArgument(this.runners.size() < Integer.MAX_VALUE, "There are already %s tasks scheduled! Cannot schedule more", Integer.MAX_VALUE);

        int id;

        do {
            id = this.ids.updateAndGet(CraftScheduler.INCREMENT_IDS);
        } while (this.runners.containsKey(id));

        return id;
    }

    private void parsePending() {
        CraftTask head = this.head;
        CraftTask task = head.getNext();

        CraftTask lastTask;

        for (lastTask = head; task != null; task = task.getNext()) {
            if (task.getTaskId() == -1) {
                task.run();
            } else if (task.getPeriod() >= -1L) {
                this.pending.add(task);
                this.runners.put(task.getTaskId(), task);
            }

            lastTask = task;
        }

        for (task = head; task != lastTask; task = head) {
            head = task.getNext();
            task.setNext((CraftTask) null);
        }

        this.head = lastTask;
    }

    private boolean isReady(int currentTick) {
        return !this.pending.isEmpty() && ((CraftTask) this.pending.peek()).getNextRun() <= (long) currentTick;
    }

    public String toString() {
        int debugTick = this.currentTick;
        StringBuilder string = (new StringBuilder("Recent tasks from ")).append(debugTick - CraftScheduler.RECENT_TICKS).append('-').append(debugTick).append('{');

        this.debugHead.debugTo(string);
        return string.append('}').toString();
    }

    /** @deprecated */
    @Deprecated
    public int scheduleSyncDelayedTask(Plugin plugin, BukkitRunnable task, long delay) {
        throw new UnsupportedOperationException("Use BukkitRunnable#runTaskLater(Plugin, long)");
    }

    /** @deprecated */
    @Deprecated
    public int scheduleSyncDelayedTask(Plugin plugin, BukkitRunnable task) {
        throw new UnsupportedOperationException("Use BukkitRunnable#runTask(Plugin)");
    }

    /** @deprecated */
    @Deprecated
    public int scheduleSyncRepeatingTask(Plugin plugin, BukkitRunnable task, long delay, long period) {
        throw new UnsupportedOperationException("Use BukkitRunnable#runTaskTimer(Plugin, long, long)");
    }

    /** @deprecated */
    @Deprecated
    public BukkitTask runTask(Plugin plugin, BukkitRunnable task) throws IllegalArgumentException {
        throw new UnsupportedOperationException("Use BukkitRunnable#runTask(Plugin)");
    }

    /** @deprecated */
    @Deprecated
    public BukkitTask runTaskAsynchronously(Plugin plugin, BukkitRunnable task) throws IllegalArgumentException {
        throw new UnsupportedOperationException("Use BukkitRunnable#runTaskAsynchronously(Plugin)");
    }

    /** @deprecated */
    @Deprecated
    public BukkitTask runTaskLater(Plugin plugin, BukkitRunnable task, long delay) throws IllegalArgumentException {
        throw new UnsupportedOperationException("Use BukkitRunnable#runTaskLater(Plugin, long)");
    }

    /** @deprecated */
    @Deprecated
    public BukkitTask runTaskLaterAsynchronously(Plugin plugin, BukkitRunnable task, long delay) throws IllegalArgumentException {
        throw new UnsupportedOperationException("Use BukkitRunnable#runTaskLaterAsynchronously(Plugin, long)");
    }

    /** @deprecated */
    @Deprecated
    public BukkitTask runTaskTimer(Plugin plugin, BukkitRunnable task, long delay, long period) throws IllegalArgumentException {
        throw new UnsupportedOperationException("Use BukkitRunnable#runTaskTimer(Plugin, long, long)");
    }

    /** @deprecated */
    @Deprecated
    public BukkitTask runTaskTimerAsynchronously(Plugin plugin, BukkitRunnable task, long delay, long period) throws IllegalArgumentException {
        throw new UnsupportedOperationException("Use BukkitRunnable#runTaskTimerAsynchronously(Plugin, long, long)");
    }
}
