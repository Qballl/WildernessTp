package me.Qball.Wild.Utils;

import org.bukkit.Location;
import org.bukkit.util.Vector;

public class Region {
    private Vector vec1;
    private Vector vec2;
    public Region(Vector vec1,Vector vec2){
     this.vec1 = vec1;
     this.vec2 = vec2;
    }
    public Region(){
        this.vec1 = null;
        this.vec2 = null;
    }
    public void setVec1(Vector vec1){
        this.vec1 = vec1;
    }
    public void setVec2(Vector vec2){
        this.vec2 = vec2;
    }
    public Vector getVec1(){
        return vec1;
    }
    public Vector getVec2() {
        return vec2;
    }
    public Vector getMinimumPoint() {
        return new Vector(Math.min(vec1.getX(), vec2.getX()),
                Math.min(vec1.getY(), vec2.getY()),
                Math.min(vec1.getZ(), vec2.getZ()));
    }

    public Vector getMaximumPoint() {
        return new Vector(Math.max(vec1.getX(), vec2.getX()),
                Math.max(vec1.getY(), vec2.getY()),
                Math.max(vec1.getZ(), vec2.getZ()));
    }
    public boolean contains(Vector position) {
        double x = position.getX();
        double y = position.getY();
        double z = position.getZ();

        Vector min = getMinimumPoint();
        Vector max = getMaximumPoint();

        return x >= min.getBlockX() && x <= max.getBlockX()
                && y >= min.getBlockY() && y <= max.getBlockY()
                && z >= min.getBlockZ() && z <= max.getBlockZ();
    }


}
