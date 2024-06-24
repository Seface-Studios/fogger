package net.sefacestudios.fogpack;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

@Getter
@Setter
public class FogpackConfiguration {
    private ColorConfiguration sky = new ColorConfiguration("", "any");
    private FogConfiguration fog = new FogConfiguration(16, "", "any");
    private ColorConfiguration water = new ColorConfiguration("", "any");

    @SerializedName("water_fog")
    private ColorConfiguration waterFog = new ColorConfiguration("", "any");

    public static class ColorConfiguration {
        private final String color;
        @Getter private final List<String> biomes;

        protected ColorConfiguration(String color, String ...biomes) {
            this.color = color;
            this.biomes = Arrays.asList(biomes);
        }

        public int getColor() {
            return Color.decode(this.color).getRGB();
        }
    }

    @Getter
    public static class FogConfiguration extends ColorConfiguration {
        private final int distance;

        protected FogConfiguration(int distance, String color, String... biomes) {
            super(color, biomes);
            this.distance = distance;

        }
    }
}
