/*
 * Decompiled with CFR 0.152.
 */
package genshin.pion.PionMod.event.impl;

import genshin.pion.PionMod.event.Event;

public class SlowdownEvent
extends Event {
    public Type type;

    public SlowdownEvent(Type type) {
        this.type = type;
    }

    public static enum Type {
        Sprinting;

    }
}

