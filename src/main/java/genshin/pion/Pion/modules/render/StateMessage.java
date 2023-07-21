package genshin.pion.Pion.modules.render;

import genshin.pion.Pion.Client;
import genshin.pion.Pion.ModuleType;
import org.lwjgl.input.Keyboard;
import genshin.pion.Pion.modules.Module;

public class StateMessage extends Module {
    public StateMessage() {
        super("NoStateMessage", Keyboard.KEY_NONE, ModuleType.Render,"Not Show Modules State info");
        Chinese="无开关信息";
    }

    public void enable() {
        Client.MessageON = true;
    }

    public void disable(){
        Client.MessageON = false;
    }
}
