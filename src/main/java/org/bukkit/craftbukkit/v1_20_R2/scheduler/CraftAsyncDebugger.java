package org.bukkit.craftbukkit.v1_20_R2.scheduler;

import org.bukkit.plugin.Plugin;

class CraftAsyncDebugger {

    private CraftAsyncDebugger next = null;
    private final int expiry;
    private final Plugin plugin;
    private final Class clazz;

    CraftAsyncDebugger(int expiry, Plugin plugin, Class clazz) {
        this.expiry = expiry;
        this.plugin = plugin;
        this.clazz = clazz;
    }

    final CraftAsyncDebugger getNextHead(int time) {
        CraftAsyncDebugger next;
        CraftAsyncDebugger current;

        for (current = this; time > current.expiry; current = next) {
            next = current.next;
            if (current.next == null) {
                break;
            }
        }

        return current;
    }

    final CraftAsyncDebugger setNext(CraftAsyncDebugger next) {
        return this.next = next;
    }

    StringBuilder debugTo(StringBuilder string) {
        for (CraftAsyncDebugger next = this; next != null; next = next.next) {
            string.append(next.plugin.getDescription().getName()).append(':').append(next.clazz.getName()).append('@').append(next.expiry).append(',');
        }

        return string;
    }
}
