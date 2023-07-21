/*
 * Decompiled with CFR 0.152.
 */
package genshin.pion.PionMod.event.impl;

import genshin.pion.PionMod.event.Event;

public class SafeWalkEvent
extends Event {
    public boolean walkSafely;

    public SafeWalkEvent(boolean walkSafely) {
        this.walkSafely = walkSafely;
    }

    public boolean isWalkSafely() {
        return this.walkSafely;
    }

    public void setWalkSafely(boolean walkSafely) {
        this.walkSafely = walkSafely;
    }
}

