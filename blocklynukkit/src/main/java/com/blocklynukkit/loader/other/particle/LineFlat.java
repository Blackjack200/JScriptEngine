package com.blocklynukkit.loader.other.particle;

import cn.nukkit.level.Position;
import cn.nukkit.level.particle.GenericParticle;

import java.util.Random;
import static java.lang.Math.sqrt;
import static java.lang.Math.pow;


public class LineFlat implements Runnable{
    public Position pos1;
    public Position pos2;
    public double sep;
    public int pid;
    public LineFlat(Position pos1,Position pos2,double sep,int pid){
        this.pos1=pos1;this.pos2=pos2;this.sep=sep;this.pid=pid;
    }
    @Override
    public void run(){
        double length = sqrt(pow(pos2.x-pos1.x,2)+pow(pos2.y-pos1.y,2)+pow(pos2.z-pos1.z,2));
        int parSum = (int)(length/sep);
        double dx = (pos2.x-pos1.x)/(double)parSum;
        double dy = (pos2.y-pos1.y)/(double)parSum;
        double dz = (pos2.z-pos1.z)/(double)parSum;
        Random random = new Random();
        for(int i=0;i<parSum;i++){
            if(i%10==0){
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            pos1.level.addParticle(new GenericParticle(pos1.add(dx*i,dy*i,dz*i),pid,random.nextInt()));
        }
    }
}
