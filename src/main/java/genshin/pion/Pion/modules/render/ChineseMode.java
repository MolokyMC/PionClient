package genshin.pion.Pion.modules.render;

import genshin.pion.Pion.Client;
import genshin.pion.Pion.ModuleType;
import genshin.pion.Pion.modules.Module;
import org.lwjgl.input.Keyboard;

public class ChineseMode extends Module {
    public ChineseMode() {
        super("中文", Keyboard.KEY_NONE, ModuleType.Combat,"");
        Chinese="ChineseMode";
    }


    @Override
    public void enable(){
        Client.CHINESE = true;
        this.mc.thePlayer.closeScreen();
    }

    @Override
    public void disable(){
        Client.CHINESE = false;
        this.mc.thePlayer.closeScreen();
    }

}
