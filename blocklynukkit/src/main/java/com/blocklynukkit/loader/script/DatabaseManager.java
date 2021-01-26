package com.blocklynukkit.loader.script;

import com.blocklynukkit.loader.script.bases.BaseManager;
import com.blocklynukkit.loader.utils.C3P0Utils;
import site.misaka.engine.EngineAdapter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager extends BaseManager {
    public DatabaseManager(EngineAdapter scriptEngine) {
        super(scriptEngine);
    }

    @Override
    public String toString() {
        return "BlocklyNukkit Based Object";
    }

    /**
     * 例子:
     * <pre>
     *     open("localhost:3306/database","root","password","money (id VARCHAR(64), money double null, constraint money_pk primary key(id))")
     * </pre>
     *
     * @author Penguin_Captain
     * @param url       jdbc连接的url
     * @param username  数据库账号
     * @param password  数据库密码
     * @param table     数据表信息
     * @throws SQLException
     */
    Connection connection = null;

    public void open(String url, String username, String password, String table) throws SQLException {
        String substring = url.substring(url.lastIndexOf('/') + 1, url.length());
        String database;
        if (url.indexOf('?') == -1) {
            database = substring;
        } else {
            database = url.substring(url.lastIndexOf('/') + 1, substring.indexOf('?'));
        }
        C3P0Utils.dataSource.setJdbcUrl("jdbc:mysql://" + url);
        C3P0Utils.dataSource.setUser(username);
        C3P0Utils.dataSource.setPassword(password);
        Connection conn = C3P0Utils.getConnection();
        connection = conn;
        C3P0Utils.update("CREATE TABLE IF NOT EXISTS " + table, conn);
    }

    public void databaseOpen(String url, String username, String password, String table) {
        try {
            open(url, username, password, table);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * {@code update(String, Object...)} 方法可以实现在数据库中的更新(无返回)
     * 若要使用变量,在SQL语句中把变量以{@code ?}代替,然后在objs参数内填入相对的数值
     * <p>
     * 例子:
     * <pre>
     *     update("INSERT INTO database.user (username,password) VALUES (?,?)",username,password)
     * </pre>
     *
     * @param stt SQL语句
     * @throws SQLException
     */
    public void update(String stt) throws SQLException {
        Connection conn = C3P0Utils.getConnection();
        PreparedStatement ps = conn.prepareStatement(stt);
        ps.executeUpdate();
        C3P0Utils.release(conn, ps, null);
    }

    public void databaseUpdate(String stt) {
        try {
            update(stt);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * {@code query(String,String,Object...)} 方法可以实现在数据库中的查询(有返回)
     * 若要使用变量,在SQL语句中把变量以{@code ?}代替,然后在objs参数内填入相对的数值
     * <p>
     * 例子:
     * <pre>
     *  query("SELECT * FROM database.user WHERE id = ?",password,id) returns All Passwords in a list which is corresponding to id.
     * </pre>
     *
     * @param stt SQL语句
     * @param col 列
     * @return 查询结果列表
     * @throws SQLException
     */
    public List query(String stt, String col) throws SQLException {
        Connection conn = C3P0Utils.getConnection();
        PreparedStatement ps = conn.prepareStatement(stt);
        ResultSet rs = ps.executeQuery();
        ArrayList<Object> result = new ArrayList<>();
        while (rs.next()) {
            result.add(rs.getObject(col));
        }
        C3P0Utils.release(conn, ps, rs);
        return result;
    }

    public void databaseQuery(String stt, String col) {
        try {
            query(stt, col);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static int countStr(String source, String str) {
        int beforeLen = source.length();
        String target = source.replaceAll(str, "");
        int afterLen = target.length();
        return beforeLen - afterLen;
    }

}
