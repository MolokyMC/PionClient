//TODO: NoCommand
package genshin.pion.Pion.modules.other;

import genshin.pion.Pion.ModuleType;
import genshin.pion.Pion.modules.Module;
import org.lwjgl.input.Keyboard;

public class NoCommand extends Module {
    public NoCommand() {
        super("NoCommand", Keyboard.KEY_NONE, ModuleType.Other, "No command.");
        Chinese=("没有指令");
    }

}
