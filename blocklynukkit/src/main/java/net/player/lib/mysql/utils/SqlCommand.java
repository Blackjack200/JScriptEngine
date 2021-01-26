package net.player.lib.mysql.utils;

public enum  SqlCommand {

    /**创建数据库*/
    CREATE("create table ?(?)"),

    /**更新数据库*/
    UPDATE("update ? set ? = ? where ? = ?"),

    /** 插入数据*/
    INSERT("insert into ?(?) values(?)"),

    /**查找数据库*/
    SELECT("select ? from ? where ? = ?"),
    /**查找数据库*/
    SELECT_DEFAULT("select ? from ?"),

    /** 修改数据表*/
    ALTER("alter table ? add column ? ?");

    private String command;

    private SqlCommand(String s) {
        this.command = s;
    }

    public String getCommand() {
        return this.command;
    }
}
