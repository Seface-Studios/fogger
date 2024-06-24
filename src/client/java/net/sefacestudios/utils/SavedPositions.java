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

    @SerializedName("fogpack_identifier")
    private String identifier;

    private Fogpack fogpack;

    public SavedPositions(String name, Vec3d position, Fogpack fogpack) {
        this.x = position.getX();
        this.y = position.getY();
        this.z = position.getZ();

        this.name = name;
        this.identifier = fogpack != null ? fogpack.getIdentifier() : null;
        this.fogpack = fogpack != null ? fogpack : null;
    }
}
