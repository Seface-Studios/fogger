package net.sefacestudios.fogpack;

import lombok.Getter;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
public class Fogpack {
    private String $schema = "../../fogpack.schema.json";
    private String version = "0.0.1-SNAPSHOT";
    private String identifier = new Identifier("fogger", UUID.randomUUID().toString()).toString();
    private String name = identifier.replace("fogger:", "");
    private String description = "";
    private List<String> authors = new ArrayList<>();

    private String path;

    private FogpackConfiguration config = new FogpackConfiguration();

    public Fogpack setPath(String path) {
        this.path = path;
        return this;
    }
}
