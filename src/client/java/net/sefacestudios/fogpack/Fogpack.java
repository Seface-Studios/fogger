package net.sefacestudios.fogpack;

import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Getter
public class Fogpack {
    private String $schema = "https://www.sefacestudios.net/fogpack.schema.v1.json";
    private String version = "1.0.0";
    private String identifier;
    private String name;
    private String description = "";
    private List<String> authors = new ArrayList<>(List.of(MinecraftClient.getInstance().player.getName().getString()));

    @Setter
    @Getter
    private transient String path;

    private final transient Random random = new Random();

    private final FogpackConfiguration config = new FogpackConfiguration();

    public Fogpack(Identifier identifier, String name) {
        this.identifier = identifier.toString();
        this.name = name;
    }

    public Fogpack withRandomColors() {
        this.config.setSky(new FogpackConfiguration.ColorConfiguration(randomColor()));
        this.config.setFog(new FogpackConfiguration.FogConfiguration(-1, 20, randomColor()));
        this.config.setClouds(new FogpackConfiguration.ColorConfiguration(randomColor()));
        this.config.setWater(new FogpackConfiguration.ColorConfiguration(randomColor()));
        this.config.setWaterFog(new FogpackConfiguration.ColorConfiguration(randomColor()));

        return this;
    }

    public static String randomColor() {
        Random random = new Random();
        int r = random.nextInt(256);
        int g = random.nextInt(256);
        int b = random.nextInt(256);

        return String.format("#%02x%02x%02x", r, g, b).toUpperCase();
    }
}
