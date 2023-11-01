package org.bukkit.craftbukkit.v1_20_R2.util;

import java.util.Random;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.PositionalRandomFactory;

public final class RandomSourceWrapper implements RandomSource {

    private final Random random;

    public RandomSourceWrapper(Random random) {
        this.random = random;
    }

    public RandomSource fork() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public PositionalRandomFactory forkPositional() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public synchronized void setSeed(long seed) {
        this.random.setSeed(seed);
    }

    public int nextInt() {
        return this.random.nextInt();
    }

    public int nextInt(int bound) {
        return this.random.nextInt(bound);
    }

    public long nextLong() {
        return this.random.nextLong();
    }

    public boolean nextBoolean() {
        return this.random.nextBoolean();
    }

    public float nextFloat() {
        return this.random.nextFloat();
    }

    public double nextDouble() {
        return this.random.nextDouble();
    }

    public synchronized double nextGaussian() {
        return this.random.nextGaussian();
    }

    public static final class RandomWrapper extends Random {

        private final RandomSource random;

        public RandomWrapper(RandomSource random) {
            this.random = random;
        }

        public void setSeed(long l) {
            if (this.random != null) {
                this.random.setSeed(l);
            }

        }

        public int nextInt() {
            return this.random.nextInt();
        }

        public int nextInt(int i) {
            return this.random.nextInt(i);
        }

        public long nextLong() {
            return this.random.nextLong();
        }

        public boolean nextBoolean() {
            return this.random.nextBoolean();
        }

        public float nextFloat() {
            return this.random.nextFloat();
        }

        public double nextDouble() {
            return this.random.nextDouble();
        }

        public double nextGaussian() {
            return this.random.nextGaussian();
        }

        public int nextInt(int i, int j) {
            return this.random.nextInt(i, j);
        }
    }
}
