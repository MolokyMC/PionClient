package genshin.pion.Pion.modules.player;

import genshin.pion.Pion.ModuleType;
import genshin.pion.Pion.utils.BlockUtils;
import net.minecraft.util.BlockPos;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;
import genshin.pion.Pion.modules.Module;

public class AutoTools extends Module {
    public AutoTools() {
        super("AutoTools", Keyboard.KEY_I, ModuleType.Player,"Switch correct tools when you destroy blocks");
        Chinese="自动工具";
    }

    @SubscribeEvent
    public void onTick(TickEvent event) {
        if (!mc.gameSettings.keyBindAttack.isKeyDown()) {
            return;
        }
        if (mc.objectMouseOver == null) {
            return;
        }
        BlockPos pos = mc.objectMouseOver.getBlockPos();
        if (pos == null) {
            return;
        }
        BlockUtils.updateTool(pos);
    }
}
