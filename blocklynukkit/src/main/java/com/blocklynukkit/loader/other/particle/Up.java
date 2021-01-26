package com.blocklynukkit.loader.other.particle;

import cn.nukkit.level.Level;
import cn.nukkit.level.particle.GenericParticle;
import cn.nukkit.math.Vector3;

import java.util.Random;

public class Up implements Runnable{
    private Vector3 origin;
    private double length;
    //private double route;
    private Level level;
    private double turn;
    private double rou;
    private Vector3 center;
    private int typeid;
    private double height = 0.0d;
    public Up(Level l,Vector3 origina,double x,double r,double length,int type,double p,double yaw){
        level = l;this.origin =origina;this.length=length;typeid=type;typeid=type;rou = p;turn=yaw;
        center = getpos(x,r+yaw);
    }
    public Up(Level l,Vector3 origina,double x,double r,double length,int type,double p){
        level = l;this.origin =origina;this.length=length;typeid=type;typeid=type;rou = p;turn=0;
        center = getpos(x,r+turn);
    }
    public Up(Level l,Vector3 origina,double x,double r,double length,int type,double p,double yaw,double heights){
        level = l;this.origin =origina;this.length=length;typeid=type;typeid=type;rou = p;turn=yaw;height=heights;
        center = getpos(x,r+yaw);

    }
    public Vector3 getpos(double farence,double front){
        double x,y,z,yaw;
        x = origin.x;y=origin.y;z=origin.z;yaw = (front/180.0)*Math.PI;
        double outx,outy,outz;
        outx = x-farence*Math.cos(yaw);
        outz = z-farence*Math.sin(yaw);
        outy = y+0.2+height;
        return new Vector3(outx,outy,outz);
    }
    @Override
    public void run(){
        long time = Math.round(length/rou);
        double dh = length/time;
        double outx = center.x;
        double outy = center.y;
        double outz = center.z;
        Random random = new Random();
        for (int i=0;i<time;i++){
            level.addParticle(new GenericParticle(new Vector3(outx,outy+i*dh,outz),typeid,random.nextInt()));
            try {
                if(i%10==0)Thread.sleep(100);
            }catch (InterruptedException e){
                continue;
            }
        }
    }
}
