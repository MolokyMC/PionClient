/*
 * Decompiled with CFR 0.152.
 */
package genshin.pion.PionMod.event.impl;

import genshin.pion.PionMod.event.Event;

public class LightningEvent
extends Event {
    public double y;
    public double x;
    public double z;

    public LightningEvent(double y, double x, double z) {
        this.y = y;
        this.z = z;
        this.x = x;
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public double getZ() {
        return this.z;
    }
}

