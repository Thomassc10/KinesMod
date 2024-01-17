package com.kines.kinesmod.gui.elements;

import net.minecraft.client.Minecraft;

public abstract class UIElement {

    private String name;
    private Point pos;
    public double scale;
    public boolean toggled;
    private int width = Minecraft.getMinecraft().fontRendererObj.getStringWidth(display());
    private int height = Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT;

    public UIElement(String name, Point pos) {
        this.name = name;
        this.pos = pos;
        this.scale = scale();
        toggled = toggled();
    }

    public UIElement(){}

    public String getName() {
        return name;
    }

    public Point getPos() {
        return pos;
    }

    public void setPos(float x, float y) {
        this.pos = new Point(x, y);
    }

    public void setPos(Point pos) {
        this.pos = pos;
    }

    public float getX() {
        return pos.getX();
    }

    public float getY() {
        return pos.getY();
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public abstract String display();

    public abstract double scale();

    public abstract boolean toggled();
}
