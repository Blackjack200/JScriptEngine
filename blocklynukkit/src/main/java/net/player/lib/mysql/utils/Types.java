package net.player.lib.mysql.utils;


/**
 * 参数表.. (没有的话执行setSql)
 * @author SmallasWater
 */

public enum Types {

    /**
     * 字符串
     * */
    CHAR("char",255,"not null"),
    /**
     * ID
     * */
    ID("id","","parmary key"),
    /**
     * 长字符串
     * */
    VARCHAR("varchar",255,"notnull"),

    /**
     * 整数
     * */
    INT("int","","not null"),
    /**
     * 小数
     * */
    DOUBLE("Double","","not null"),

    /**
     * 存放2进制
     * */
    BLOB("blob",255,"not null"),
    /**
     * 时间
     *
     * */
    DATE("date","","not null"),
    /**
     * 字符串*/
    TEXT("text","","not null");

    protected String sql;
    protected Object size;
    protected Object value;

    Types(String sql, Object... value){
        this.sql = sql;
        this.size = value[0];
        this.value = value[1];
    }

    public void setSize(Object size) {
        this.size = size;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    @Override
    public String toString() {
        if(!"".equalsIgnoreCase(size.toString())){
            return sql+"("+size.toString()+") "+value.toString();
        }
        return sql+" "+value.toString();
    }
}
