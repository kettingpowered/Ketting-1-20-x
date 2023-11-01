package org.bukkit.craftbukkit.v1_20_R2.entity;

import com.google.common.base.Preconditions;
import org.bukkit.Color;
import org.bukkit.craftbukkit.v1_20_R2.CraftServer;
import org.bukkit.entity.Display;
import org.bukkit.entity.Display.Billboard;
import org.bukkit.entity.Display.Brightness;
import org.bukkit.util.Transformation;
import org.joml.Matrix4f;

public class CraftDisplay extends CraftEntity implements Display {

    public CraftDisplay(CraftServer server, net.minecraft.world.entity.Display entity) {
        super(server, entity);
    }

    public net.minecraft.world.entity.Display getHandle() {
        return (net.minecraft.world.entity.Display) super.getHandle();
    }

    public String toString() {
        return "CraftDisplay";
    }

    public Transformation getTransformation() {
        com.mojang.math.Transformation nms = net.minecraft.world.entity.Display.createTransformation(this.getHandle().getEntityData());

        return new Transformation(nms.getTranslation(), nms.getLeftRotation(), nms.getScale(), nms.getRightRotation());
    }

    public void setTransformation(Transformation transformation) {
        Preconditions.checkArgument(transformation != null, "Transformation cannot be null");
        this.getHandle().setTransformation(new com.mojang.math.Transformation(transformation.getTranslation(), transformation.getLeftRotation(), transformation.getScale(), transformation.getRightRotation()));
    }

    public void setTransformationMatrix(Matrix4f transformationMatrix) {
        Preconditions.checkArgument(transformationMatrix != null, "Transformation matrix cannot be null");
        this.getHandle().setTransformation(new com.mojang.math.Transformation(transformationMatrix));
    }

    public int getInterpolationDuration() {
        return this.getHandle().getTransformationInterpolationDuration();
    }

    public void setInterpolationDuration(int duration) {
        this.getHandle().setTransformationInterpolationDuration(duration);
    }

    public int getTeleportDuration() {
        return (Integer) this.getHandle().getEntityData().get(net.minecraft.world.entity.Display.DATA_POS_ROT_INTERPOLATION_DURATION_ID);
    }

    public void setTeleportDuration(int duration) {
        Preconditions.checkArgument(duration >= 0 && duration <= 59, "duration (%s) cannot be lower than 0 or higher than 59", duration);
        this.getHandle().getEntityData().set(net.minecraft.world.entity.Display.DATA_POS_ROT_INTERPOLATION_DURATION_ID, duration);
    }

    public float getViewRange() {
        return this.getHandle().getViewRange();
    }

    public void setViewRange(float range) {
        this.getHandle().setViewRange(range);
    }

    public float getShadowRadius() {
        return this.getHandle().getShadowRadius();
    }

    public void setShadowRadius(float radius) {
        this.getHandle().setShadowRadius(radius);
    }

    public float getShadowStrength() {
        return this.getHandle().getShadowStrength();
    }

    public void setShadowStrength(float strength) {
        this.getHandle().setShadowStrength(strength);
    }

    public float getDisplayWidth() {
        return this.getHandle().getWidth();
    }

    public void setDisplayWidth(float width) {
        this.getHandle().setWidth(width);
    }

    public float getDisplayHeight() {
        return this.getHandle().getHeight();
    }

    public void setDisplayHeight(float height) {
        this.getHandle().setHeight(height);
    }

    public int getInterpolationDelay() {
        return this.getHandle().getTransformationInterpolationDelay();
    }

    public void setInterpolationDelay(int ticks) {
        this.getHandle().setTransformationInterpolationDelay(ticks);
    }

    public Billboard getBillboard() {
        return Billboard.valueOf(this.getHandle().getBillboardConstraints().name());
    }

    public void setBillboard(Billboard billboard) {
        Preconditions.checkArgument(billboard != null, "Billboard cannot be null");
        this.getHandle().setBillboardConstraints(net.minecraft.world.entity.Display.BillboardConstraints.valueOf(billboard.name()));
    }

    public Color getGlowColorOverride() {
        int color = this.getHandle().getGlowColorOverride();

        return color == -1 ? null : Color.fromARGB(color);
    }

    public void setGlowColorOverride(Color color) {
        if (color == null) {
            this.getHandle().setGlowColorOverride(-1);
        } else {
            this.getHandle().setGlowColorOverride(color.asARGB());
        }

    }

    public Brightness getBrightness() {
        net.minecraft.util.Brightness nms = this.getHandle().getBrightnessOverride();

        return nms != null ? new Brightness(nms.block(), nms.sky()) : null;
    }

    public void setBrightness(Brightness brightness) {
        if (brightness != null) {
            this.getHandle().setBrightnessOverride(new net.minecraft.util.Brightness(brightness.getBlockLight(), brightness.getSkyLight()));
        } else {
            this.getHandle().setBrightnessOverride((net.minecraft.util.Brightness) null);
        }

    }
}
