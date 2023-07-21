package genshin.pion.Pion.modules.player;

import genshin.pion.Pion.Client;
import genshin.pion.Pion.ModuleType;
import genshin.pion.Pion.modules.Module;
import genshin.pion.Pion.utils.Helper;
import org.lwjgl.input.Keyboard;

public class ChatBypass extends Module {
    public ChatBypass() {
        super("ChatBypass", Keyboard.KEY_NONE, ModuleType.Player,"");
        Chinese="聊天绕过";
    }

    @Override
    public void enable() {
        Helper.sendMessage("使用/cp <Message>发送ChatBypassed消息");
        Client.ChatBypass = true;
    }

    @Override
    public void disable() {
        Client.ChatBypass = false;
    }



}
