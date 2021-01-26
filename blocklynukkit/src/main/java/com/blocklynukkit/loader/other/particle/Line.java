package com.blocklynukkit.loader.other.particle;

import cn.nukkit.level.Level;
import cn.nukkit.level.particle.GenericParticle;
import cn.nukkit.math.Vector3;

import java.util.Random;

public class Line implements Runnable{
    private Vector3 start;
    private Vector3 end;
    private int typeid;
    private double rou;
    private Level level;
    private double turn;
    private Vector3 origin;
    private double height = 0.0d;
    public Line(Level l, Vector3 origin, double x1, double r1, double x2, double r2, int type, double p){
        typeid =type;rou = p;level =l;this.origin = origin;turn =0;
        start = getpos(x1,r1+turn);
        end = getpos(x2,r2+turn);

    }
    public Line(Level l, Vector3 origin, double x1, double r1, double x2, double r2, int type, double p, double yaw){
        typeid =type;rou = p;level =l;this.origin = origin;turn = yaw;
        start = getpos(x1,r1+turn);
        end = getpos(x2,r2+turn);
    }
    public Line(Level l, Vector3 origin, double x1, double r1, double x2, double r2, int type, double p, double yaw, double heights){
        typeid =type;rou = p;level =l;this.origin = origin;turn = yaw;height = heights;
        start = getpos(x1,r1+turn);
        end = getpos(x2,r2+turn);

    }
    public Vector3 getpos(double farence, double front){
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
        double length = Math.pow(((end.x-start.x)*(end.x-start.x)+(end.z-start.z)*(end.z-start.z)),0.5);
        long time = Math.round(length/rou);
        double dx = (end.x-start.x)/time;
        double dz = (end.z-start.z)/time;
        Random random = new Random();
        //Main.getlogger().info(length+" "+time+" "+" "+dx+" "+dz);
        for (int i=0;i<time;i++){
            level.addParticle(new GenericParticle(new Vector3(start.x+dx*i,start.y,start.z+dz*i),typeid,random.nextInt()));
            //Main.getlogger().info(length+"-"+time+"-"+(start.x+dx*i)+"-"+(start.z+dz*i));
            try {
                if(i%10==0)Thread.sleep(100);
            }catch (InterruptedException e){
                continue;
            }
        }
    }
}
