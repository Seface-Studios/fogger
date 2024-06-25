package net.sefacestudios.fogpack;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;

@Getter
@Setter
public class FogpackConfiguration {
    private ColorConfiguration sky = new ColorConfiguration(null);
    private FogConfiguration fog = new FogConfiguration(-1, 0, null);
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

        @SerializedName("start_multiplier")
        private final int startMultiplier;

        protected FogConfiguration(int distance, int start_multiplier, String color) {
            super(color);
            this.distance = distance;
            this.startMultiplier = start_multiplier;
        }
    }
}
