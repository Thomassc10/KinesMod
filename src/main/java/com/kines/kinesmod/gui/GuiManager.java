package com.kines.kinesmod.gui;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.kines.kinesmod.gui.elements.Point;
import com.kines.kinesmod.gui.elements.UIElement;

import java.io.*;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class GuiManager {

    private static Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private static Map<String, Point> positions = new HashMap<>();
    private static Map<Integer, UIElement> elements = new HashMap<>();
    private static Map<String, UIElement> names = new HashMap<>();
    public static File positionsFile = new File("./config/kinesmod/guilocations.json");

    public static Map<String, Point> getPositions() {
        return positions;
    }

    public static Map<Integer, UIElement> getElements() {
        return elements;
    }

    private static int counter = 0;
    public static void addElement(UIElement element) {
        counter++;
        elements.put(counter, element);
        names.put(element.getName(), element);
    }

    public static void loadConfig() throws FileNotFoundException {
        FileReader reader = new FileReader(positionsFile);
        Gson gson = new Gson();
        Type mapType = new TypeToken<Map<String, Point>>() {}.getType();
        Map<String, Point> map = gson.fromJson(reader, mapType);
        if (map == null) return;

        for (String name : map.keySet()) {
            positions.put(name, map.get(name));
        }

        for (String name : names.keySet()) {
            UIElement uiElement = names.get(name);
            Point position = positions.get(name);

            if (uiElement != null && position != null) {
                uiElement.setPos(position);
            }
        }
    }

    public static void saveConfig() throws IOException {
        for (UIElement element : elements.values()) {
            positions.put(element.getName(), element.getPos());
        }
        FileWriter writer = new FileWriter(positionsFile);
        gson.toJson(positions, writer);
        writer.close();
    }
}
