package org.bukkit.craftbukkit.v1_20_R2.generator;

import net.minecraft.world.level.biome.Climate;
import org.bukkit.generator.BiomeParameterPoint;

public class CraftBiomeParameterPoint implements BiomeParameterPoint {

    private final double temperature;
    private final double humidity;
    private final double continentalness;
    private final double erosion;
    private final double depth;
    private final double weirdness;
    private final Climate.Sampler sampler;

    public static BiomeParameterPoint createBiomeParameterPoint(Climate.Sampler sampler, Climate.TargetPoint targetPoint) {
        return new CraftBiomeParameterPoint(sampler, (double) Climate.unquantizeCoord(targetPoint.temperature()), (double) Climate.unquantizeCoord(targetPoint.humidity()), (double) Climate.unquantizeCoord(targetPoint.continentalness()), (double) Climate.unquantizeCoord(targetPoint.erosion()), (double) Climate.unquantizeCoord(targetPoint.depth()), (double) Climate.unquantizeCoord(targetPoint.weirdness()));
    }

    private CraftBiomeParameterPoint(Climate.Sampler sampler, double temperature, double humidity, double continentalness, double erosion, double depth, double weirdness) {
        this.sampler = sampler;
        this.temperature = temperature;
        this.humidity = humidity;
        this.continentalness = continentalness;
        this.erosion = erosion;
        this.depth = depth;
        this.weirdness = weirdness;
    }

    public double getTemperature() {
        return this.temperature;
    }

    public double getMaxTemperature() {
        return this.sampler.temperature().maxValue();
    }

    public double getMinTemperature() {
        return this.sampler.temperature().minValue();
    }

    public double getHumidity() {
        return this.humidity;
    }

    public double getMaxHumidity() {
        return this.sampler.humidity().maxValue();
    }

    public double getMinHumidity() {
        return this.sampler.humidity().minValue();
    }

    public double getContinentalness() {
        return this.continentalness;
    }

    public double getMaxContinentalness() {
        return this.sampler.continentalness().maxValue();
    }

    public double getMinContinentalness() {
        return this.sampler.continentalness().minValue();
    }

    public double getErosion() {
        return this.erosion;
    }

    public double getMaxErosion() {
        return this.sampler.erosion().maxValue();
    }

    public double getMinErosion() {
        return this.sampler.erosion().minValue();
    }

    public double getDepth() {
        return this.depth;
    }

    public double getMaxDepth() {
        return this.sampler.depth().maxValue();
    }

    public double getMinDepth() {
        return this.sampler.depth().minValue();
    }

    public double getWeirdness() {
        return this.weirdness;
    }

    public double getMaxWeirdness() {
        return this.sampler.weirdness().maxValue();
    }

    public double getMinWeirdness() {
        return this.sampler.weirdness().minValue();
    }
}
