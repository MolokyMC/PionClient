/*
 * Decompiled with CFR 0.152.
 */
package genshin.pion.PionMod.event.impl;

import genshin.pion.PionMod.event.Event;
import net.minecraft.network.Packet;

public class PacketEvent
extends Event {
    public Packet packet;
    public DirectionType dir;

    public PacketEvent(Packet packet, DirectionType dir) {
        this.packet = packet;
        this.dir = dir;
    }

    public boolean isIncoming() {
        return this.dir == DirectionType.Incoming;
    }

    public boolean isOutgoing() {
        return this.dir == DirectionType.Outgoing;
    }

    public Packet getPacket() {
        return this.packet;
    }

    public DirectionType getDir() {
        return this.dir;
    }

    public void setPacket(Packet packet) {
        this.packet = packet;
    }

    public void setDir(DirectionType dir) {
        this.dir = dir;
    }

    public static enum DirectionType {
        Incoming,
        Outgoing;

    }
}

