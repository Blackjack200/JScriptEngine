package com.blocklynukkit.loader.other.particle;

import cn.nukkit.level.Position;
import cn.nukkit.level.particle.GenericParticle;
import cn.nukkit.math.Vector3;
import com.blocklynukkit.loader.Loader;

import java.util.Random;

import static java.lang.Math.PI;
import static java.lang.Math.sin;
import static java.lang.Math.cos;

public class CircleFlat implements Runnable{
    public Position pos;
    public double radius;
    public int pid;
    public double sep;
    public CircleFlat(Position pos,double radius,int pid,double sep){
        this.pos=pos;this.pid=pid;this.radius=radius;this.sep=sep;
    }
    @Override
    public void run(){
        double round = radius*2* PI;
        int parAnt = (int) (round/sep);
        double perPI = ((2*PI)/(double)parAnt);
        Random random = new Random();
        for(int i=0;i<parAnt;i++){
            if(i%10==0) {
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            double dx = radius*sin(i*perPI);
            double dy = radius*cos(i*perPI);
            Vector3 vec = new Vector3(pos.x+dx,pos.y,pos.z+dy);
            pos.level.addParticle(new GenericParticle(vec,pid,random.nextInt()));
        }
    }
}
