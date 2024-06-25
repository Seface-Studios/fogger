package net.sefacestudios.fogpack;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.util.Random;

@Getter
@Setter
public class FogpackConfiguration {
    private ColorConfiguration sky = new ColorConfiguration();
    private FogConfiguration fog = new FogConfiguration(-1, 0);
    private ColorConfiguration water = new ColorConfiguration();

    @SerializedName("water_fog")
    private ColorConfiguration waterFog = new ColorConfiguration();

    public static class ColorConfiguration {
        private final String color;

        protected ColorConfiguration() {
            this.color = randomColor();
        }

        public int getColor() {
            return this.color != null ? Color.decode(this.color).getRGB() : 0;
        }

        public int getColor(int original) {
            return this.color != null ? Color.decode(this.color).getRGB() : original;
        }

        public static String randomColor() {
            Random random = new Random();
            int r = random.nextInt(256);
            int g = random.nextInt(256);
            int b = random.nextInt(256);

            return String.format("#%02x%02x%02x", r, g, b);
        }
    }

    @Getter
    public static class FogConfiguration extends ColorConfiguration {
        private final int distance;

        @SerializedName("start_multiplier")
        private final int startMultiplier;

        protected FogConfiguration(int distance, int start_multiplier) {
            super();
            this.distance = distance;
            this.startMultiplier = start_multiplier;
        }
    }
}
