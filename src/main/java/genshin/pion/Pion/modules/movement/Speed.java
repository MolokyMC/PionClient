package genshin.pion.Pion.modules.movement;

import genshin.pion.Pion.ModuleType;
import genshin.pion.Pion.modules.Module;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

public class Speed extends Module {
    public Speed() {
        super("Speed", Keyboard.KEY_G, ModuleType.Movement,"moved quickly");
        Chinese="加速";
    }

    @SubscribeEvent
    public void onUpdate(TickEvent.PlayerTickEvent event) {
        if(!mc.thePlayer.isCollidedHorizontally && mc.thePlayer.moveForward > 0 && mc.thePlayer.onGround){
            mc.thePlayer.setSprinting(true);
            mc.thePlayer.jump();
        }
    }
}
