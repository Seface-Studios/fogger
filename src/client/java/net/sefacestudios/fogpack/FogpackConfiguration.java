package net.sefacestudios.fogpack;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;

import java.awt.*;

@Setter
@Getter
public class FogpackConfiguration {
    private ColorConfiguration sky = new ColorConfiguration();
    private FogConfiguration fog = new FogConfiguration(-1, 20);
    private ColorConfiguration clouds = new ColorConfiguration();
    private ColorConfiguration water = new ColorConfiguration();

    @SerializedName("water_fog")
    private ColorConfiguration waterFog = new ColorConfiguration(null);

    public FogpackConfiguration() {}

    public static class ColorConfiguration {
        private final String color;

        public ColorConfiguration() {
            this(null);
        }

        public ColorConfiguration(String color) {
            this.color = color;
        }

        public String getHex() {
            return this.color != null ? this.color : "";
        }

        public Integer getColor() {
            return this.color != null ? Color.decode(this.color).getRGB() : null;
        }

        public Integer getColor(int original) {
            return this.color != null ? Color.decode(this.color).getRGB() : original;
        }
    }

    @Setter
    public static class FogConfiguration extends ColorConfiguration {
        private Integer distance;

        @SerializedName("start_multiplier")
        private Integer startMultiplier;

        private static transient int MIN_START_MULTIPLIER = 20;
        private static transient int MAX_START_MULTIPLIER = 100;

        public FogConfiguration(int distance, int startMultiplier) {
            this(distance, startMultiplier, null);
        }

        public FogConfiguration(int distance, int startMultiplier, String color) {
            super(color);
            this.distance = distance;
            this.startMultiplier = startMultiplier;
        }

        public Integer getDistance() {
            return this.distance != null && this.distance <= 0 && this.distance != -1 ? this.distance : -1;
        }

        public Integer getStartMultiplier() {
            return Math.max(
                    MIN_START_MULTIPLIER,
                    Math.min(MAX_START_MULTIPLIER, this.startMultiplier == null ? MIN_START_MULTIPLIER : this.startMultiplier)
            );
        }
    }
}
