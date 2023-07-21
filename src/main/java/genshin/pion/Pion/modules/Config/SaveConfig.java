package genshin.pion.Pion.modules.Config;

import genshin.pion.Pion.Client;
import genshin.pion.Pion.ModuleType;
import genshin.pion.Pion.modules.Module;
import genshin.pion.Pion.utils.Helper;

import java.io.IOException;

import static org.lwjgl.input.Keyboard.KEY_N;

public class SaveConfig extends Module {
    public SaveConfig() {
        super("SaveConfig", KEY_N, ModuleType.Config,"Save your module setting(config)");
        Chinese="保存配置";
        NoToggle=true;
    }

    public void enable() {
        try {
            Client.SaveConfig();
        } catch (IOException e) {
            e.printStackTrace();
            state=false;
        }
        Helper.sendMessage("Configs Saved.");
        state=false;
    }
}
