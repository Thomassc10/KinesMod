package com.kines.kinesmod.utils;

import java.util.Arrays;

public enum Island {

    PRIVATE_ISLAND("Private Island"),
    PRIVATE_ISLAND_GUEST("Private Island Guest"),
    THE_END("The End"),
    KUUDRA("Kuudra"),
    CRIMSON_ISLE("Crimson Isle"),
    DWARVEN_MINES("Dwarven Mines"),
    DUNGEON_HUB("Dungeon Hub"),
    CATACOMBS("Catacombs"),
    HUB("Hub"),
    DARK_AUCTION("Dark Auction"),
    THE_FARMING_ISLANDS("The Farming Islands"),
    CRYSTAL_HOLLOWS("Crystal Hollows"),
    THE_PARK("The Park"),
    DEEP_CAVERNS("Deep Caverns"),
    GOLD_MINES("Gold Mine"),
    GARDEN("Garden"),
    GARDEN_GUEST("Garden Guest"),
    SPIDER_DEN("Spider's Den"),
    WINTER("Jerry's Workshop"),
    THE_RIFT("The Rift"),

    NONE("")
    ;

    private String name;
    private Island(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static Island getByName(String name) {
        return Arrays.stream(values()).filter(e -> e.getName().equals(name)).findFirst().orElse(null);
    }
}
