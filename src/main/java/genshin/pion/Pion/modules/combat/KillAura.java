package genshin.pion.Pion.modules.combat;

import genshin.pion.Pion.ModuleType;
import genshin.pion.Pion.modules.Module;
import genshin.pion.Pion.utils.TimerUtil;
import genshin.pion.Pion.value.Numbers;
import genshin.pion.Pion.value.Option;
import genshin.pion.PionMod.PacketSendEvent;
import genshin.pion.PionMod.event.impl.PacketEvent;
import genshin.pion.eventapi.EventTarget;
import genshin.pion.utils.Rotation;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.realms.RealmsMth;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;
import org.apache.commons.lang3.RandomUtils;
import org.lwjgl.input.Keyboard;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.client.C02PacketUseEntity;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static net.minecraft.realms.RealmsMth.sqrt;
import static net.minecraft.realms.RealmsMth.wrapDegrees;

public class KillAura extends Module {
    private final TimerUtil timer = new TimerUtil();
    public static EntityLivingBase target;
    public static Rotation serverRotation = new Rotation(0F, 0F);

    public boolean hittable = false;
    private Numbers<Double> rangeValue = new Numbers<Double>("Range", "Range",4.2, 1.0, 6.0,1.0);
    private Numbers<Double> cps = new Numbers<Double>("CPS", "CPS",10.0, 1.0, 20.0,1.0);
    private Numbers<Double> maxTurnSpeed = new Numbers<Double>("MaxTurnSpeed", "MaxTurnSpeed",180.0, 0.0, 180.0,1.0);
    private Numbers<Double> minTurnSpeed = new Numbers<Double>("MinTurnSpeed", "MinTurnSpeed",180.0, 0.0, 180.0,1.0);
    private Option<Boolean> autoblock = new Option<Boolean>("AutoBlock","AutoBlock", true);
    private Option<Boolean> alwaysHittable = new Option<Boolean>("AlwaysHittable","AlwaysHittable", false);
    public KillAura() {
        super("KillAura", Keyboard.KEY_NONE, ModuleType.Combat,"Auto Attack the entity near you");
        //TODO Target
        this.addValues(this.rangeValue,this.autoblock,this.cps,this.maxTurnSpeed,this.minTurnSpeed,this.alwaysHittable);
        Chinese="杀戮光环";
    }

    @SubscribeEvent
    public void onTick(TickEvent event) {
        hittable = isFaced(target, 0.05);
        if (alwaysHittable.getValue()) hittable=true;
        this.updateTarget();
        //if (target != null) updateRotation(target);
        assistFaceEntity(target,15.0F,15.0F);
        if (timer.delay(1000 / ThreadLocalRandom.current().nextInt((int) 1, this.cps.getValue().intValue()))) {
            Object[] objects = mc.theWorld.loadedEntityList.stream().filter(entity -> entity instanceof EntityLivingBase && entity != mc.thePlayer && ((EntityLivingBase) entity).getHealth() > 0F && entity.getDistanceToEntity(mc.thePlayer) <= rangeValue.getValue()).sorted(Comparator.comparingDouble(entity -> entity.getDistanceToEntity(mc.thePlayer))).toArray();
            if (objects.length > 0)
                target = (EntityLivingBase) objects[0];
            if(target == null || AntiBot.isServerBot(target))
                return;
            if(target.getHealth()==0)
                return;
//            if(target == mc.objectMouseOver.entityHit)
//                return;
            if (hittable) runAttack();
            target = null;
            timer.reset();
        }
    }
    private void runAttack(){
        // attack
        mc.thePlayer.swingItem();
        mc.getNetHandler().addToSendQueue(new C02PacketUseEntity(target, C02PacketUseEntity.Action.ATTACK));

        // blocking
        if ((Boolean) this.autoblock.getValue() && mc.thePlayer.getCurrentEquippedItem() != null){
            mc.thePlayer.getHeldItem().useItemRightClick(mc.theWorld, mc.thePlayer);
        }
    }

    @SubscribeEvent
    public void onPacketSend(FMLNetworkEvent.ClientCustomPacketEvent event) {
        final Packet packet = event.packet;

        if (packet instanceof C03PacketPlayer) {
            final C03PacketPlayer packet1 = (C03PacketPlayer) packet;
            if (packet1.getRotating())
                serverRotation = new Rotation(packet1.getYaw(), packet1.getPitch());
        }
    }

    public void updateRotation(Entity entity) {
        AxisAlignedBB boundingBox = entity.getEntityBoundingBox();
        boundingBox = boundingBox.offset(
                (entity.posX - entity.prevPosX) * RandomUtils.nextFloat(0.95F, 1.75F),
                (entity.posY - entity.prevPosY) * RandomUtils.nextFloat(0.95F, 1.75F),
                (entity.posZ - entity.prevPosZ) * RandomUtils.nextFloat(0.95F, 1.75F)
        );
        Rotation limitedRotation = limitAngleChange(serverRotation, getNewRotations(getCenter(boundingBox),false),(float) (Math.random() * (maxTurnSpeed.getValue() - minTurnSpeed.getValue()) + minTurnSpeed.getValue()));
        if (mc.thePlayer != null) limitedRotation.toPlayer(mc.thePlayer);
    }

    public static void assistFaceEntity(Entity entity, float yaw, float pitch) {
        double yDifference;
        if (entity == null) {
            return;
        }
        double diffX = entity.posX - mc.thePlayer.posX;
        double diffZ = entity.posZ - mc.thePlayer.posZ;
        if (entity instanceof EntityLivingBase) {
            EntityLivingBase entityLivingBase = (EntityLivingBase) entity;
            yDifference = entityLivingBase.posY + (double) entityLivingBase.getEyeHeight() - (mc.thePlayer.posY + (double) mc.thePlayer.getEyeHeight());
        } else {
            yDifference = (entity.getEntityBoundingBox().minY + entity.getEntityBoundingBox().maxY) / 2.0 - (mc.thePlayer.posY + (double) mc.thePlayer.getEyeHeight());
        }
        double dist = sqrt(diffX * diffX + diffZ * diffZ);
        float rotationYaw = (float) (Math.atan2(diffZ, diffX) * 180.0 / Math.PI) - 90.0f;
        float rotationPitch = (float) (-(Math.atan2(yDifference, dist) * 180.0 / Math.PI));
        if (yaw > 0.0f) {
            mc.thePlayer.rotationYaw = updateRotation1(mc.thePlayer.rotationYaw, rotationYaw, yaw / 4.0f);
        }
        if (pitch > 0.0f) {
            mc.thePlayer.rotationPitch = updateRotation1(mc.thePlayer.rotationPitch, rotationPitch, pitch / 4.0f);
        }
    }

    public static float updateRotation1(float p_70663_1_, float p_70663_2_, float p_70663_3_) {
        float var4 = wrapDegrees(p_70663_2_ - p_70663_1_);
        if (var4 > p_70663_3_) {
            var4 = p_70663_3_;
        }
        if (var4 < -p_70663_3_) {
            var4 = -p_70663_3_;
        }
        return p_70663_1_ + var4;
    }

    void updateTarget() {
        for (Entity object : getEntityList()) {
            EntityLivingBase entity;
            if (!(object instanceof EntityLivingBase) || !this.check(entity = (EntityLivingBase) object)) continue;
            this.target = entity;
        }
    }

    public static List<Entity> getEntityList() {
        if(mc.theWorld != null){return mc.theWorld.getLoadedEntityList();} else {return null;}
    }

    public boolean check(EntityLivingBase entity) {
        if (entity instanceof EntityArmorStand) {
            return false;
        }
        if (entity == mc.thePlayer) {
            return false;
        }
        if (entity.isDead) {
            return false;
        }
        if (entity.getHealth() == 0 ) {
            return false;
        }
        if(AntiBot.isServerBot(entity)){
            return false;
        }
        if(entity.getDistanceToEntity(mc.thePlayer) > 4.2F){
            return false;
        }
        return mc.thePlayer.canEntityBeSeen(entity);
    }

    public static Rotation limitAngleChange(final Rotation currentRotation, final Rotation targetRotation, final float turnSpeed) {
        final float yawDifference = getAngleDifference(targetRotation.yaw, currentRotation.yaw);
        final float pitchDifference = getAngleDifference(targetRotation.pitch, currentRotation.pitch);

        return new Rotation(
                currentRotation.yaw + (yawDifference > turnSpeed ? turnSpeed : Math.max(yawDifference, -turnSpeed)),
                currentRotation.pitch + (pitchDifference > turnSpeed ? turnSpeed : Math.max(pitchDifference, -turnSpeed)
                ));
    }
    public static Rotation getNewRotations(final Vec3 vec, final boolean predict) {
        final Vec3 eyesPos = new Vec3(mc.thePlayer.posX, mc.thePlayer.getEntityBoundingBox().minY +
                mc.thePlayer.getEyeHeight(), mc.thePlayer.posZ);
        final double diffX = vec.xCoord - eyesPos.xCoord;
        final double diffY = vec.yCoord - eyesPos.yCoord;
        final double diffZ = vec.zCoord - eyesPos.zCoord;

        double dist = Math.sqrt(diffX * diffX + diffZ * diffZ);
        float yaw = (float)(Math.atan2(diffZ, diffX) * 180.0 / 3.141592653589793) - 90.0f;
        float pitch = (float)((- Math.atan2(diffY, dist)) * 180.0 / 3.141592653589793);
        return new Rotation(yaw, pitch);
    }
    private static float getAngleDifference(final float a, final float b) {
        return ((((a - b) % 360F) + 540F) % 360F) - 180F;
    }
    public static Vec3 getCenter(final AxisAlignedBB bb) {
        return new Vec3(bb.minX + (bb.maxX - bb.minX) * 0.5, bb.minY + (bb.maxY - bb.minY) * 0.5, bb.minZ + (bb.maxZ - bb.minZ) * 0.5);
    }
    public boolean isFaced(final Entity targetEntity, double blockReachDistance) {
        return raycastEntity(blockReachDistance) != null;
    }
    public static Entity raycastEntity(double range) {
        return raycastEntity(range, serverRotation.yaw,
                serverRotation.pitch);
    }
    private static Entity raycastEntity(double range, float yaw, float pitch) {
        Entity renderViewEntity = mc.getRenderViewEntity();

        if (renderViewEntity != null && mc.theWorld != null) {
            double blockReachDistance = range;
            Vec3 eyePosition = renderViewEntity.getPositionEyes(1f);

            double yawCos = Math.cos(-yaw * 0.017453292f - Math.PI);
            double yawSin = Math.sin(-yaw * 0.017453292f - Math.PI);
            float pitchCos = (float) (-Math.cos(-pitch * 0.017453292f));
            float pitchSin = (float) Math.sin(-pitch * 0.017453292f);

            Vec3 entityLook = new Vec3(yawSin * pitchCos, pitchSin, yawCos * pitchCos);
            Vec3 vector = eyePosition.addVector(
                    entityLook.xCoord * blockReachDistance,
                    entityLook.yCoord * blockReachDistance,
                    entityLook.zCoord * blockReachDistance
            );
            List<Entity> entityList = mc.theWorld.getEntitiesInAABBexcluding(
                    renderViewEntity,
                    renderViewEntity.getEntityBoundingBox().addCoord(
                            entityLook.xCoord * blockReachDistance,
                            entityLook.yCoord * blockReachDistance,
                            entityLook.zCoord * blockReachDistance
                    ).expand(1.0, 1.0, 1.0),
                    it -> it != null && !(it instanceof EntityPlayer && ((EntityPlayer) it).isSpectator()) && it.canBeCollidedWith()
            );

            Entity pointedEntity = null;

            for (Entity entity : entityList) {

                double collisionBorderSize = entity.getCollisionBorderSize();
                AxisAlignedBB axisAlignedBB = entity.getEntityBoundingBox().expand(collisionBorderSize, collisionBorderSize, collisionBorderSize);

                MovingObjectPosition movingObjectPosition = axisAlignedBB.calculateIntercept(eyePosition, vector);

                if (axisAlignedBB.isVecInside(eyePosition)) {
                    if (blockReachDistance >= 0.0) {
                        pointedEntity = entity;
                        blockReachDistance = 0.0;
                    }
                } else if (movingObjectPosition != null) {
                    double eyeDistance = eyePosition.distanceTo(movingObjectPosition.hitVec);

                    if (eyeDistance < blockReachDistance || blockReachDistance == 0.0) {
                        if (entity == renderViewEntity.ridingEntity && !renderViewEntity.canRiderInteract()) {
                            if (blockReachDistance == 0.0)
                                pointedEntity = entity;
                        } else {
                            pointedEntity = entity;
                            blockReachDistance = eyeDistance;
                        }
                    }
                }
            }

            return pointedEntity;
        }

        return null;
    }
    interface EntityFilter {
        boolean canRaycast(Entity entity);
    }
}
