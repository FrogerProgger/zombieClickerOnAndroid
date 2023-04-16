package com.example.zombieclicker;

public class CourseModel {

    private String price;
    private String effect;
    private String description;
    private int plusIcon;
    private int minusIcon;

    public CourseModel(String price, String effect, int plus, int minus, String description){
        this.price = price;
        this.effect = effect;
        this.plusIcon = plus;
        this.minusIcon = minus;
        this.description = description;
    }

    public String getEffect() {
        return effect;
    }

    public String getPrice() {
        return price;
    }

    public int getMinusIcon() {
        return minusIcon;
    }

    public int getPlusIcon() {
        return plusIcon;
    }

    public void setEffect(String effect) {
        this.effect = effect;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setMinusIcon(int minusIcon) {
        this.minusIcon = minusIcon;
    }

    public void setPlusIcon(int plusIcon) {
        this.plusIcon = plusIcon;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
