package genshin.pion.Pion.modules.combat;

import genshin.pion.Pion.ModuleType;
import genshin.pion.Pion.modules.Module;
import genshin.pion.Pion.value.Option;
import genshin.pion.eventapi.EventTarget;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Velocity extends Module {
    private Option<Boolean> onlyGround = new Option<Boolean>("OnlyGround","OnlyGround", false);
    public Velocity() {
        super("Velocity", Keyboard.KEY_NONE, ModuleType.Movement,"Less KB");
        this.addValues(this.onlyGround);
        Chinese="反击退";

    }

    @SubscribeEvent
    public void onTick(TickEvent event){
        final EntityPlayerSP thePlayer = Velocity.mc.thePlayer;
        if (thePlayer.onGround && onlyGround.getValue()) return;
        if (mc.thePlayer.maxHurtTime > 0) {
            final Random ch = new Random();
            thePlayer.motionX -= ch.nextDouble() * (0.10-0.05)+0.05;
            thePlayer.motionY += -1.0E-7;
            thePlayer.motionZ -= ch.nextDouble() * (0.10-0.05)+0.05;
            thePlayer.isAirBorne = true;
        }
    }
}
