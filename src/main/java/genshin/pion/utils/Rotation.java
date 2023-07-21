package genshin.pion.utils;

import net.minecraft.entity.player.EntityPlayer;

/**
 * Skid or Made By WaWa
 *
 * @author WaWa
 * @date 2023/7/20 22:04
 */
public class Rotation {
    public Float yaw;
    public Float pitch;

    public Rotation(Float yaw, Float pitch) {
        this.yaw = yaw;
        this.pitch = pitch;
    }

    public void toPlayer(EntityPlayer player) {
        if (yaw.isNaN() || pitch.isNaN())
            return;
        player.rotationYaw = yaw;
        player.rotationPitch = pitch;
    }
}
