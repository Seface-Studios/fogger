package net.sefacestudios.utils;

import lombok.Getter;

@Getter
public enum FogpacksSource {
  GAME_DIRECTORY(0, "game_directory"),
  BUILT_IN(1, "built_in"),
  URL(2, "url"),
  OTHER(3, "other");

  private final int id;
  private final String name;

  FogpacksSource(int id, String name) {
    this.id = id;
    this.name = name;
  }
}
