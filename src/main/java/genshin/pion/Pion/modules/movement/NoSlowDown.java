package genshin.pion.Pion.modules.movement;

import genshin.pion.Pion.ModuleType;
import genshin.pion.Pion.Value;
import genshin.pion.Pion.modules.Module;
import genshin.pion.PionMod.event.Event;
import genshin.pion.PionMod.event.impl.MotionEvent;
import genshin.pion.eventapi.EventTarget;
import genshin.pion.utils.MovementUtils;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

import java.util.Random;

public class NoSlowDown extends Module {
    private Value<Float> speedValue = new Value<>("Speed", 1.0F);
    public NoSlowDown() {
        super("NoSlowDown", Keyboard.KEY_R, ModuleType.Movement);
        Chinese="没有减速";
    }

    @SubscribeEvent
    public void onPacketSend(TickEvent.ClientTickEvent event){

    }

    @SubscribeEvent
    public void onTick(TickEvent event) {
        if (mc.thePlayer == null) return;
        if (mc.thePlayer.getHeldItem() != null && isItemFood(mc.thePlayer.inventory.getCurrentItem()) && MovementUtils.isMoving() && mc.gameSettings.keyBindUseItem.isKeyDown()) {
            mc.getNetHandler().addToSendQueue(new C09PacketHeldItemChange((mc.thePlayer.inventory.currentItem + 1) % 9));
            mc.getNetHandler().addToSendQueue(new C09PacketHeldItemChange((mc.thePlayer.inventory.currentItem) % 9));
        }
        if (mc.thePlayer.getItemInUse() != null && mc.thePlayer.getItemInUse().getItem() != null) {
            if (isItemSword(mc.thePlayer.inventory.getCurrentItem())) {
                if (mc.thePlayer.isUsingItem() && mc.thePlayer.getItemInUseCount() >= 1){
                    mc.getNetHandler().addToSendQueue(new C09PacketHeldItemChange((mc.thePlayer.inventory.currentItem + 1) % 9));
                    mc.getNetHandler().addToSendQueue(new C09PacketHeldItemChange(mc.thePlayer.inventory.currentItem));

                }
                if (mc.thePlayer.isUsingItem() || mc.thePlayer.isBlocking()) {
                    mc.getNetHandler().addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
                    mc.getNetHandler().addToSendQueue(
                            new C08PacketPlayerBlockPlacement(
                                    getHytBlockpos(), 255,mc.thePlayer.inventory.getCurrentItem(),
                                    0f, 0f, 0f
                            )
                    );
                }
            }
        }
        mc.thePlayer.movementInput.moveStrafe = speedValue.getObject();
        mc.thePlayer.movementInput.moveForward = speedValue.getObject();
    }
    public BlockPos getHytBlockpos() {
        Random random = new Random();
        int dx = (int) Math.floor(random.nextDouble() / 1000 + 2820);
        int jy = (int) Math.floor(random.nextDouble() / 100 * 0.20000000298023224);
        int kz = (int) Math.floor(random.nextDouble() / 1000 + 2820);
        return new BlockPos(dx, -jy % 255, kz);
    }
    public boolean isItemFood(Object obj){
        return obj instanceof ItemFood;
    }
    public boolean isItemSword(Object obj){
        return obj instanceof ItemSword;
    }
}
