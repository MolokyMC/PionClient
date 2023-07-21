package genshin.pion.Pion.modules.render;

import genshin.pion.Pion.ModuleType;
import genshin.pion.Pion.VapeClickGui.VapeClickGui;
import org.lwjgl.input.Keyboard;

import genshin.pion.Pion.modules.Module;

public class ClickGUI extends Module {

	public ClickGUI() {
		super("ClickGUI", Keyboard.KEY_RSHIFT, ModuleType.Render,"Open ClickGui");
		Chinese="点击GUI";
		// TODO Auto-generated constructor stub
	}
	
	public void toggle() {
		mc.displayGuiScreen(new VapeClickGui());
		this.setState(false);
	}

}
