package com.blocklynukkit.loader.utils;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class C3P0Utils {
    public static ComboPooledDataSource dataSource = new ComboPooledDataSource("mysql");
    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public static boolean update(String qry, Connection conn) {
        if (conn != null) {
            try {
                PreparedStatement ps = conn.prepareStatement(qry);
                ps.executeUpdate();
                release(conn,ps,null);
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return false;
    }

    public static void release(Connection conn, PreparedStatement ps, ResultSet rs) {
        if (conn != null){
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (ps != null) {
            try {
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }
}
