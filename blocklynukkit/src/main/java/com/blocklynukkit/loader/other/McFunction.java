package com.blocklynukkit.loader.other;

import cn.nukkit.Server;
import cn.nukkit.command.ConsoleCommandSender;
import cn.nukkit.level.Level;
import cn.nukkit.math.Vector3;
import com.blocklynukkit.loader.Loader;
import com.blocklynukkit.loader.UnionData;
import com.blocklynukkit.loader.other.particle.Circle;
import com.blocklynukkit.loader.other.particle.FireworkRocket;
import com.blocklynukkit.loader.other.particle.Line;
import com.blocklynukkit.loader.other.particle.Up;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class McFunction {
    private ConsoleCommandSender sender;
    private Vector3 pos;
    private Level level;
    private Server instance = Server.getInstance();
    private File file;
    private String fun = null;
    public double turn = 0;

    public McFunction(String functionname, Vector3 p, Level l) {
        fun = functionname;
        File folder = new File(Loader.plugin.getDataFolder() + "/function");
        folder.mkdirs();
        file = new File(Loader.plugin.getDataFolder() + "/function/" + functionname + ".mcfunction");
        if (file.exists()) {
            sender = new ConsoleCommandSender();
            if (p != null) {
                pos = p;
            } else {
                pos = Server.getInstance().getDefaultLevel().getSafeSpawn();
            }
            if (l != null) {
                level = l;
            } else {
                level = Server.getInstance().getDefaultLevel();
            }
            //Loader.getlogger().info("执行"+functionname+"于"+pos.x+" "+pos.y+" "+pos.z+" "+level.getName());
        } else {
            Loader.getPluginLogger().info(functionname + "不存在");
        }
    }

    public McFunction setturn(double t) {
        turn = t;
        return this;
    }

    public void run() {
        try {
            readF1(file);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public final void readF1(File mcf) throws Throwable {
        String[] lines;
        if (UnionData.mcFunctionMap.get(fun) == null) {
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    new FileInputStream(mcf)));
            String tmp = "";
            for (String line = br.readLine(); line != null; line = br.readLine()) {
                tmp += line + ";;;;";
                tmp.replaceAll("\n", "");
            }
            lines = tmp.split(";;;;");
            br.close();
            UnionData.mcFunctionMap.put(fun, lines);
        } else {
            lines = UnionData.mcFunctionMap.get(fun);
        }


        for (String line : lines) {
            if (line.startsWith("/")) line = line.substring(1);
            line = line.replaceAll("\\{world\\}", level.getName())
                    .replaceAll("\\{x\\}", pos.x + "")
                    .replaceAll("\\{y\\}", pos.y + "")
                    .replaceAll("\\{z\\}", pos.z + "");
            if ((!line.equals("")) && line.length() > 3) {
                if (line.startsWith("partry")) {
                    line = line.substring(7);
                    String args[] = line.split("\\s\\s*");
                    //Main.getlogger().info(args[0]+" "+args[1]+" "+args[2]);
                    if (args[0].equals("line")) {
                        new Thread(new Line(
                                Server.getInstance().getLevelByName(args[1]),
                                new Vector3(Double.parseDouble(args[2]), Double.parseDouble(args[3]), Double.parseDouble(args[4])),
                                Double.parseDouble(args[5]),
                                Double.parseDouble(args[6]),
                                Double.parseDouble(args[7]),
                                Double.parseDouble(args[8]),
                                Integer.parseInt(args[9]),
                                Double.parseDouble(args[10]),
                                turn,
                                args.length >= 12 ? Double.parseDouble(args[11]) : 0d
                        )).start();
                        //Main.getlogger().info(args.length+"--"+(args.length>=12?Double.parseDouble(args[11]):0d));
                    } else if (args[0].equals("circle")) {
                        new Thread(new Circle(
                                Server.getInstance().getLevelByName(args[1]),
                                new Vector3(Double.parseDouble(args[2]), Double.parseDouble(args[3]), Double.parseDouble(args[4])),
                                Double.parseDouble(args[5]),
                                Double.parseDouble(args[6]),
                                Double.parseDouble(args[7]),
                                Integer.parseInt(args[8]),
                                Double.parseDouble(args[9]),
                                turn,
                                args.length >= 11 ? Double.parseDouble(args[10]) : 0.0d
                        )).start();
                        //Main.getlogger().info(args.length+"--"+(args.length>=11?Double.parseDouble(args[10]):0d));
                    } else if (args[0].equals("up")) {
                        new Thread(new Up(
                                Server.getInstance().getLevelByName(args[1]),
                                new Vector3(Double.parseDouble(args[2]), Double.parseDouble(args[3]), Double.parseDouble(args[4])),
                                Double.parseDouble(args[5]),
                                Double.parseDouble(args[6]),
                                Double.parseDouble(args[7]),
                                Integer.parseInt(args[8]),
                                Double.parseDouble(args[9]),
                                turn,
                                args.length >= 11 ? Double.parseDouble(args[10]) : 0.0d
                        )).start();
                    } else if (args[0].equals("firework")) {//firework level x y z colornum isflick istrail shapenum second
                        FireworkRocket.make(Server.getInstance().getLevelByName(args[1]),
                                new Vector3(Double.parseDouble(args[2]), Double.parseDouble(args[3]), Double.parseDouble(args[4])),
                                Integer.parseInt(args[5]),
                                Boolean.parseBoolean(args[6]),
                                Boolean.parseBoolean(args[7]),
                                Integer.parseInt(args[8]),
                                Integer.parseInt(args[9]));
                    } else {
                        instance.dispatchCommand(sender, line);
                    }
                } else {
                    instance.dispatchCommand(sender, line);
                }
            }

        }
    }
}
