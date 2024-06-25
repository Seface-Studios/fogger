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
    private ColorConfiguration sky = new ColorConfiguration(null);
    private FogConfiguration fog = new FogConfiguration(16, null);
    private ColorConfiguration water = new ColorConfiguration(null);

    @SerializedName("water_fog")
    private ColorConfiguration waterFog = new ColorConfiguration(null);

    public static class ColorConfiguration {
        private final String color;

        protected ColorConfiguration(String color) {
            this.color = color;
        }

        public int getColor() {
            return this.color != null ? Color.decode(this.color).getRGB() : 0;
        }

        public int getColor(int original) {
            return this.color != null ? Color.decode(this.color).getRGB() : original;
        }
    }

    @Getter
    public static class FogConfiguration extends ColorConfiguration {
        private final int distance;

        protected FogConfiguration(int distance, String color) {
            super(color);
            this.distance = distance;

        }
    }
}
