package genshin.pion.PionMod;

import genshin.pion.eventapi.events.callables.EventCancellable;
import net.minecraft.network.Packet;

public class PacketReceiveEvent extends EventCancellable {
	public Packet packet;
	public PacketReceiveEvent(Packet packet) {
		this.packet = packet;
	}

	public Packet getPacket() {
		return packet;
	}

	public void setPacket(Packet packet) {
		this.packet = packet;
	}
}
