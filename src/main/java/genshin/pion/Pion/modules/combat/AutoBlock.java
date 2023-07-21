package genshin.pion.Pion.modules.combat;

import genshin.pion.Pion.Client;
import genshin.pion.Pion.ModuleType;
import genshin.pion.Pion.modules.Module;
import org.lwjgl.input.Keyboard;

import static net.minecraft.realms.RealmsMth.sqrt;
import static net.minecraft.realms.RealmsMth.wrapDegrees;

public class AutoBlock extends Module {
    public AutoBlock() {
        super("AutoBlock", Keyboard.KEY_NONE, ModuleType.Combat);
        Chinese="自动格挡";
    }


    @Override
    public void enable(){
        Client.AutoBlock = true;
    }

    @Override
    public void disable(){
        Client.AutoBlock = false;
    }

}
