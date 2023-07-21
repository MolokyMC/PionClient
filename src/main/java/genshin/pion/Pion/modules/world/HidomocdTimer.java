package genshin.pion.Pion.modules.world;

import genshin.pion.Pion.ModuleType;
import genshin.pion.PionMod.event.impl.TickEvent;
import genshin.pion.Pion.modules.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Timer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import org.lwjgl.input.Keyboard;

import java.lang.reflect.Field;

public class HidomocdTimer extends Module {
    public HidomocdTimer() {
        super("HidomocdTimer", Keyboard.KEY_NONE, ModuleType.World);
        Chinese="CE修改器特色Timer";
    }

    @SubscribeEvent
    public void onTick(TickEvent e){
        try {
            Field field = ReflectionHelper.findField(Minecraft.class, "timer", "field_71428_T");
            field.setAccessible(true);
            Field field2 = ReflectionHelper.findField(Timer.class, "tickLength", "field_194149_e");
            field2.setAccessible(true);
            field2.setFloat(field.get(Minecraft.getMinecraft()), 50 / 2.0F);
        } catch (Exception Exception) {
            Exception.printStackTrace();
        }
    }

    @Override
    public void disable(){
        try {
            Field field = ReflectionHelper.findField(Minecraft.class, "timer", "field_71428_T");
            field.setAccessible(true);
            Field field2 = ReflectionHelper.findField(Timer.class, "tickLength", "field_194149_e");
            field2.setAccessible(true);
            field2.setFloat(field.get(Minecraft.getMinecraft()), 50 / 1.0F);
        } catch (Exception Exception) {
            Exception.printStackTrace();
        }
        super.disable();
    }

}
