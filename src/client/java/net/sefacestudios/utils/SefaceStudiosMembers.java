package net.sefacestudios.utils;

import lombok.Getter;

import java.util.UUID;

@Getter
public enum SefaceStudiosMembers {
  SEFACE(UUID.fromString("d0b69798-fe0f-4674-b401-0257c827e617")),
  SPRUCEVMC(UUID.fromString("96878683-2383-4255-90a3-924909b33673")),
  _FOXSILVER(UUID.fromString("8d78f6a0-8751-44c8-b11c-c99f029e161e")),
  HAARDSAMURAI(UUID.fromString("72739af2-9d6c-4162-9941-ffd10c217b43")),
  HEEYLOST(UUID.fromString("0e946d71-0cbc-4c6a-b481-151d4755c4b5")),
  _FYAT(UUID.fromString("98b35bed-9d73-47fe-a811-9436aa32335b")),
  ISNTDEE(UUID.fromString("5c8054c7-67f6-43a4-8e7f-6329a91915df")),
  SUFFLOWERS(UUID.fromString("9a253f0d-5c08-4599-9f02-3aac0acef358")),
  TONYBUILDS_(UUID.fromString("643c16cd-0adc-4ce4-83c3-2f88f24b4d97")),
  _CARNAGEE(UUID.fromString("8aaf2df4-8105-42bd-9d74-22815bdd1975"));

  private final UUID uuid;

  SefaceStudiosMembers(UUID uuid) {
    this.uuid = uuid;
  }

  public static boolean isSefaceMember(UUID uuid) {
    for (SefaceStudiosMembers members : SefaceStudiosMembers.values()) {
      if (members.getUuid().equals(uuid)) {
        return true;
      }
    }

    return false;
  }
}
