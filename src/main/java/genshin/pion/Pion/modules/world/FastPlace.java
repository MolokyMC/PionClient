package genshin.pion.Pion.modules.world;

import genshin.pion.Pion.ModuleType;
import genshin.pion.Pion.modules.Module;
import genshin.pion.Pion.utils.TimerUtil;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

import java.util.concurrent.ThreadLocalRandom;

public class FastPlace extends Module {
    private final TimerUtil timer = new TimerUtil();
    public FastPlace() {
        super("FastPlace", Keyboard.KEY_NONE, ModuleType.World,"Make you place the blocks faster");
        Chinese="快速放置";
    }

    @SubscribeEvent
    public void onTick(TickEvent event) {
        int key = mc.gameSettings.keyBindUseItem.getKeyCode();

        if (mc.gameSettings.keyBindUseItem.isKeyDown()) {
                if (timer.delay(1000 / ThreadLocalRandom.current().nextInt((int) 19, (int) 20))) {
                    mc.thePlayer.swingItem();
                    KeyBinding.onTick(key);
                    timer.reset();
                }
            }
        }
    }
