package genshin.pion.Pion;

import genshin.pion.Manager.FileManager;
import genshin.pion.Pion.modules.Module;
import genshin.pion.Manager.ModuleManager;
import genshin.pion.Pion.utils.Helper;
import genshin.pion.command.Bind;
//import gq.vapulite.command.Report;
import genshin.pion.command.WaterMark;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static genshin.pion.Pion.utils.Helper.mc;

public class Client {
    public boolean DebugMode = false;
    // 调试时可以启用，注入成功会修改标题

    public static String name = "Pion";
    public static String real_name = "PION";
    public static String version = "0.1";
    public static String config = "module";

    public static int Theme = new Color(0, 156, 161, 255).getRGB();
    public static int ThemeR = 0;
    public static int ThemeG = 156;
    public static int ThemeB = 161;

    public static boolean MessageON = true;
    public static boolean StringBigSnakeDetection = false;
    public static boolean AutoBlock = false;
    public static boolean ChatBypass = false;
    public static boolean FluxTheme = false;
    public static boolean CHINESE = false;
    public static Client instance;
    public static boolean state = false;
    public static Random rand=new Random();
    public final FileManager fileManager = new FileManager();
    public static ModuleManager moduleManager = new ModuleManager();
    public List<String> faList = new ArrayList<String>();
    public void updateFA() {
        if(Module.mc.theWorld==null || Module.mc.thePlayer==null) {
            faList.clear();
            return;
        }
        if (faList.size()>0) {
            String msg = faList.remove(0);
            Module.mc.thePlayer.sendChatMessage(msg);
        }
    }
    public Client() throws IOException {
        if (state) return;
        state = true;
        MinecraftForge.EVENT_BUS.register(this);
        FMLCommonHandler.instance().bus().register(this);
        instance = this;
        CommandInit();
//        FontLoaders.C20.drawStringWithShadow(Client.name,114514,114514, -1);
//        FontLoaders.F14.drawStringWithShadow(Client.name,114514,114514, -1);
//        FontLoaders.Logo.drawStringWithShadow(Client.name,114514,114514, -1);
        if(!instance.DebugMode){
            if(mc.isIntegratedServerRunning() || mc.isSingleplayer()){
                Helper.sendMessageWithoutPrefix("Pion Load done! Press RSHIFT open ClickGui, Press H Open HUD");
            }
        } else {
            Display.setTitle(Display.getTitle()+" | Client Load Succeeded");
        }
    }


    private void CommandInit() {
//        ClientCommandHandler.instance.registerCommand(new ChatBypassCommand(Client.instance));
//        ClientCommandHandler.instance.registerCommand(new Report(Client.instance));
        ClientCommandHandler.instance.registerCommand(new Bind(Client.instance));
        ClientCommandHandler.instance.registerCommand(new WaterMark(Client.instance));
    }

    @SubscribeEvent
    public void keyInput(InputEvent.KeyInputEvent event) {
        for(Module m : moduleManager.getModules()) {
            if(Keyboard.isKeyDown(m.key) && m.getKey() != Keyboard.KEY_NONE) {
                m.toggle();
            }
//            if(Keyboard.isKeyDown(m.key) && Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)) {
//                mc.displayGuiScreen(new BindScreen(m));
//            }
        }
    }

    public static void SaveConfig() throws IOException {
        Client.instance.fileManager.saveModules();
    }

    public static void LoadConfig() throws IOException {
        Client.instance.fileManager.loadModules();
    }

    public static void unInject() {
        state = false;
        if (instance != null) {
            MinecraftForge.EVENT_BUS.unregister(instance);
            instance=null;
        }
    }

}
