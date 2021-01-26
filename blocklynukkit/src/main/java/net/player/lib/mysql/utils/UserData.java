package net.player.lib.mysql.utils;

/**
 * 数据库用户信息
 * @author SmallasWater
 */
public class UserData {

    private String user;

    private String passWorld;

    private String host;

    private int port;

    private String table;

    public UserData(String user,String passWorld,String host,int port,String table){
        this.host = host;
        this.port = port;
        this.user = user;
        this.passWorld = passWorld;
        this.table = table;
    }

    public String getPassWorld() {
        return passWorld;
    }

    public String getTable() {
        return table;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public String getUser() {
        return user;
    }
}
