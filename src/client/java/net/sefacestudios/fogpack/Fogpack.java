package net.sefacestudios.fogpack;

import lombok.Getter;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
public class Fogpack {
    private String $schema = "https://sefacestudios.net/fogpack.schema.v1.json";
    private String version = "0.0.1-SNAPSHOT";
    private String identifier;
    private String name;
    private String description = "";
    private List<String> authors = new ArrayList<>(List.of(MinecraftClient.getInstance().player.getName().getString()));

    private transient String path;

    private FogpackConfiguration config = new FogpackConfiguration();

    public Fogpack(Identifier identifier, String name) {
        this.identifier = identifier.toString();
        this.name = name;
    }

    public Fogpack setPath(String path) {
        this.path = path;
        return this;
    }
}
