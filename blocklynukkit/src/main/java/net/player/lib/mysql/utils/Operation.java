package net.player.lib.mysql.utils;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

/**
 * 这里是实现类
 * @author 楠木i,若水
 */
public class Operation {
    private PreparedStatement preparedStatement;


    /**
     * 执行SQL指令 每个args代表指令中的 ?
     *
     * @param connection 数据库连接
     * @param commands 数据库简易指令
     * @param args 参数*/
    public void execute(Connection connection, SqlCommand commands, String... args) {
        String command = this.replace(commands.getCommand(), args);
        try {
            this.preparedStatement = connection.prepareStatement(command);
            this.preparedStatement.execute();
        } catch (SQLException var6) {
            var6.printStackTrace();
        }

    }

    /**
     * 直接执行SQL指令 每个args代表指令中的 ?
     *
     * @param connection 数据库连接
     * @param sql sql语句
     * */
    public void execute(Connection connection, String sql) {
        try {
            this.preparedStatement = connection.prepareStatement(sql);
            this.preparedStatement.execute();
        } catch (SQLException var6) {
            var6.printStackTrace();
        }

    }


    /**
     * 修改数据表 SQL指令 每个args代表指令中的 ?
     *
     * @param connection 数据库连接
     * @param args 参数
     * */
    public void alterTableColumn(Connection connection, String... args) {
        String command = this.replace(SqlCommand.ALTER.getCommand(), args);
        if (!isColumn(connection, args[0], args[1])) {
            execute(connection,command);
        }

    }


    /**
     * 查询数据 SQL指令 每个args代表指令中的 ?
     *
     * @param connection 数据库连接
     * @param args 参数
     *
     * @return 返回的数据列表
     * */
    public LinkedList<Object> getColumnListValue(Connection connection, String... args) {
        String command = this.replace(SqlCommand.SELECT_DEFAULT.getCommand(), args);
        LinkedList<Object> objects = new LinkedList<>();
        try {
            this.preparedStatement = connection.prepareStatement(command);
            ResultSet resultSet = this.preparedStatement.executeQuery();
            while (resultSet.next()){
                objects.add(resultSet.getObject(args[0]));
            }
        } catch (SQLException var5) {
            var5.printStackTrace();
        }
        return objects;
    }

    /**
     * 查询数据 SQL指令 每个args代表指令中的 ?
     *
     * @param connection 数据库连接
     * @param args 参数
     *
     * @return 返回查询到的数据
     * */
    public Object getColumnValue(Connection connection, String... args) {
        String command = this.replace(SqlCommand.SELECT.getCommand(), args);

        try {
            this.preparedStatement = connection.prepareStatement(command);
            ResultSet resultSet = this.preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getObject(args[0]);
            }
        } catch (SQLException var5) {
            var5.printStackTrace();
        }

        return null;
    }

    /**
     * 更新数据 SQL指令 每个args代表指令中的 ?
     *
     * @param connection 数据库连接
     * @param args 参数
     *
     * @return 是否更新成功
     * */
    public boolean upDataTableColumn(Connection connection,String... args){
        String command = this.replace(SqlCommand.UPDATE.getCommand(), args);
        try {
            this.preparedStatement = connection.prepareStatement(command);
            return this.preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 插入数据 SQL指令 每个args代表指令中的 ?
     *
     * @param connection 数据库连接
     * @param args 参数
     *
     * @return 是否插入成功
     * */
    public boolean insertTableColumn(Connection connection,String... args){
        String command = this.replace(SqlCommand.INSERT.getCommand(), args);
        try {
            this.preparedStatement = connection.prepareStatement(command);
            return this.preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 创建表单 SQL指令 每个args代表指令中的 ?
     *
     * @param connection 数据库连接
     * @param args 参数
     *
     * @return 是否创建成功
     * */
    public boolean createTable(Connection connection, String... args) {
        String command = this.replace(SqlCommand.CREATE.getCommand(), args);
        try {
            ResultSet resultSet = connection.getMetaData().getTables(null, null, args[0], null);
            if (!resultSet.next()) {
                this.preparedStatement = connection.prepareStatement(command);
                this.preparedStatement.execute();
                return true;
            }
        } catch (SQLException var5) {
            var5.printStackTrace();
        }

        return false;
    }

    /**
     * 判断数据是否存在 SQL指令 每个args代表指令中的 ?
     *
     * @param connection 数据库连接
     * @param args 参数
     *
     * @return 是否存在此数据
     * */
    public boolean isTableColumnData(Connection connection, String... args) {
        String command = this.replace(SqlCommand.SELECT.getCommand(), args);

        try {
            this.preparedStatement = connection.prepareStatement(command);
            ResultSet resultSet = this.preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString(args[0]) != null;
            } else {
                return false;
            }
        } catch (SQLException var5) {
            return false;
        }
    }
    
    private static boolean isColumn(Connection connection, String table, String column) {
        try {
            ResultSet resultSet = connection.getMetaData().getColumns((String)null, (String)null, table, column);
            return resultSet.next();
        } catch (SQLException var4) {
            return false;
        }
    }

    private String replace(String s, String... args) {
        char[] list = s.toCharArray();
        int size = 0;
        StringBuilder stringBuffer = new StringBuilder();
        for (char c : list) {
            if (String.valueOf(c).contains("?")) {
                stringBuffer.append(args[size]);
                ++size;
            } else {
                stringBuffer.append(c);
            }
        }

        return stringBuffer.toString();
    }
}
