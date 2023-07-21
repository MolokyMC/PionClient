package genshin.pion;

import genshin.pion.Pion.Client;
import net.minecraft.launchwrapper.LaunchClassLoader;

import java.lang.instrument.Instrumentation;

public class AttachPoint {
    public static void agentmain(String args, Instrumentation instrumentation) throws Exception {
        for (Class<?> classes : instrumentation.getAllLoadedClasses()) {
            if (classes.getName().startsWith("net.minecraft.client.Minecraft")) {
                LaunchClassLoader classLoader = (LaunchClassLoader)classes.getClassLoader();
                classLoader.addURL(AttachPoint.class.getProtectionDomain().getCodeSource().getLocation());
                Class<?> client = classLoader.loadClass(Client.class.getName());
                client.newInstance();
            }
        }
    }
}