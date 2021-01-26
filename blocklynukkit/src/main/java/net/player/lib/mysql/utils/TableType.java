package net.player.lib.mysql.utils;

/**
 * 创建数据表传入的参数.
 * @author SmallasWater
 */
public class TableType {

    private String name;

    private Types type;


    public TableType(String name,Types type){
        this.name = name;
        this.type = type;

    }

    public String toTable(){
        return name + " "+type.toString();
    }
}
