package genshin.pion.Pion.modules.Config;

import genshin.pion.Pion.ModuleType;
import genshin.pion.Pion.modules.Module;

import java.awt.*;
import java.awt.datatransfer.StringSelection;

import static org.lwjgl.input.Keyboard.KEY_NONE;

public class IGN extends Module {
    public IGN() {
        super("CopyName", KEY_NONE, ModuleType.Config,"copy your name for party");
        Chinese="复制名字";
        NoToggle=true;
    }

    public void enable() {
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(mc.thePlayer.getName()), null);
        state=false;
    }
}
