package com.blocklynukkit.loader.utils;

import java.util.ArrayList;

public class ProgressBarThread implements Runnable {
    private ArrayList<Integer> proList = new ArrayList<Integer>();
    private int progress;
    private int totalSize;
    private int progress_size = 50;
    private int bite = 2;
    private String finish;
    private String unfinish;
    private boolean run = true;
    public ProgressBarThread(int totalSize){
        this.totalSize = totalSize;

    }
    private String getNChar(int size,char ch){
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < size; i++){
            sb.append(ch);
        }
        return sb.toString();
    }
    public void updateProgress(int progress){
        synchronized (this.proList) {
            if(this.run){
                this.proList.add(progress);
                this.proList.notify();
            }
        }
    }
    public void finish(){
        this.run = false;
    }
    @Override
    public void run() {
        synchronized (this.proList) {
            try {
                while (this.run) {
                    if(this.proList.size()==0){
                        this.proList.wait();
                    }
                    synchronized (proList) {
                        this.progress += this.proList.remove(0);
                        int index = 100*progress/totalSize;
                        finish = getNChar(index/bite, '█');
                        unfinish = getNChar(progress_size-index/bite,'─');
                        String target = String.format("%3d%%├%s%s┤", index, finish, unfinish);
                        System.out.print(getNChar(progress_size + 6, '\b'));
                        System.out.print(target);
//                        System.err.println("当前进度："+(this.progress/this.totalSize*100)+"%");
                    }
                }
                System.err.println("下载完成");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
