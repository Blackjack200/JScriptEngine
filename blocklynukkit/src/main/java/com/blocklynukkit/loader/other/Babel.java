package com.blocklynukkit.loader.other;

import cn.nukkit.utils.TextFormat;
import com.blocklynukkit.loader.Loader;
import jdk.nashorn.api.scripting.NashornScriptEngineFactory;

import javax.script.ScriptEngine;
import javax.script.ScriptException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Babel {
    public ScriptEngine babelRuntime;
    public Babel(){
        babelRuntime = new NashornScriptEngineFactory().getScriptEngine();
        try {
            InputStream is=this.getClass().getResourceAsStream("/babel.js");
            BufferedReader br=new BufferedReader(new InputStreamReader(is));
            String s="";String babeljs="";
            while((s=br.readLine())!=null)babeljs+=s;
            InputStream is2=this.getClass().getResourceAsStream("/polyfill.js");
            BufferedReader br2=new BufferedReader(new InputStreamReader(is2));
            String s2="";String polyfilljs="";
            while((s2=br2.readLine())!=null)polyfilljs+=s2;
            babelRuntime.eval(polyfilljs);
            babelRuntime.eval(babeljs);
        } catch (ScriptException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public String transform(String escode){
        babelRuntime.put("BNinputESCode",escode);
        try {
            return (String)babelRuntime.eval("Babel.transform(BNinputESCode,{ presets: ['env'] }).code");
        } catch (ScriptException e) {
           Loader.getPluginLogger().error(TextFormat.RED+"Failed to transform!");
           e.printStackTrace();
        }
        return escode;
    }
}
