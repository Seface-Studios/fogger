package net.sefacestudios.utils;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import net.minecraft.util.math.Vec3d;
import net.sefacestudios.fogpack.Fogpack;

@Getter
public class SavedPositions {
    private String name;
    private double x;
    private double y;
    private double z;

    private String fogpack;

    public SavedPositions(String name, Vec3d position, Fogpack fogpack) {
        this.x = position.getX();
        this.y = position.getY();
        this.z = position.getZ();

        this.name = name;
        this.fogpack = fogpack != null ? fogpack.getIdentifier() : null;
    }
}
