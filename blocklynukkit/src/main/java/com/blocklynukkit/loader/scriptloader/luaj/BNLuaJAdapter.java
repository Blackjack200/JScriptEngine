package com.blocklynukkit.loader.scriptloader.luaj;

import com.blocklynukkit.loader.EngineFacade;
import com.blocklynukkit.loader.utils.Utils;
import org.luaj.vm2.LuaFunction;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;
import org.luaj.vm2.lib.jse.CoerceLuaToJava;
import org.luaj.vm2.script.LuaScriptEngine;
import site.misaka.luaj.LuaJAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BNLuaJAdapter extends LuaJAdapter {

    public BNLuaJAdapter(LuaScriptEngine engine) {
        super(engine);

        engine.put("asTable", new OneArgFunction() {
            @Override
            public LuaValue call(LuaValue luaValue) {
                Object value = CoerceLuaToJava.coerce(luaValue, Object.class);
                return to(value);
            }

            private LuaValue to(Object value) {
                System.out.println(value.getClass());
                if (value instanceof List) {
                    LuaTable result = new LuaTable();
                    List from = (List) value;
                    int position = 1;
                    for (Object each : from) {
                        // LuaTable默认索引从1开始，0也可以，但是后面获取长度等有问题
                        result.set(position, this.to(each));
                    }
                    return result;
                } else if (value instanceof Map) {
                    LuaTable result = new LuaTable();
                    Map<?, ?> from = (Map) value;
                    for (Map.Entry<?, ?> entry : from.entrySet()) {
                        result.set(CoerceJavaToLua.coerce(entry.getKey().toString()), CoerceJavaToLua.coerce(entry.getValue()));
                    }
                    return result;
                } else if (value instanceof Object[]) {
                    LuaTable result = new LuaTable();
                    Object[] from = (Object[]) value;
                    int position = 1;
                    for (Object each : from) {
                        // LuaTable默认索引从1开始，0也可以，但是后面获取长度等有问题
                        result.set(position, this.to(each));
                    }
                } else if (value instanceof LuaValue) {
                    LuaValue from = (LuaValue) value;
                    if (from.istable()) {
                        return from;
                    }
                }
                return CoerceJavaToLua.coerce(value);
            }
        });

        engine.put("asMap", new OneArgFunction() {
            @Override
            public LuaValue call(LuaValue luaValue) {
                Object value = CoerceLuaToJava.coerce(luaValue, Object.class);
                return to(value);
            }

            public LuaValue to(Object object) {
                if (object instanceof LuaValue) {
                    if (((LuaValue) object).istable()) {
                        LuaTable from = (LuaTable) object;
                        Map<Object, Object> result = new HashMap<>();
                        for (LuaValue each : from.keys()) {
                            result.put(CoerceLuaToJava.coerce(each, Object.class), this.to(from.get(each)));
                        }
                        return CoerceJavaToLua.coerce(result);
                    }
                }
                return CoerceJavaToLua.coerce(object);
            }
        });
        engine.put("asList", new OneArgFunction() {
            @Override
            public LuaValue call(LuaValue luaValue) {
                Object value = CoerceLuaToJava.coerce(luaValue, Object.class);
                return to(value);
            }

            public LuaValue to(Object object) {
                if (object instanceof LuaValue) {
                    if (((LuaValue) object).istable()) {
                        LuaTable from = (LuaTable) object;
                        List<Object> result = new ArrayList<>();
                        for (LuaValue each : from.keys()) {
                            result.add(this.to(from.get(each)));
                        }
                        return CoerceJavaToLua.coerce(result);
                    }
                }
                return CoerceJavaToLua.coerce(object);
            }
        });
        engine.put("F", new OneArgFunction() {
            @Override
            public LuaValue call(LuaValue luaValue) {
                if (luaValue.isfunction()) {
                    return luaValue;
                }
                return LuaValue.NIL;
            }
        });
    }

    @Override
    public void invoke(String name, Object... args) {
        if (EngineFacade.redirect.get(this).containsKey(name)) {
            name = EngineFacade.redirect.get(this).get(name);
        }
        super.invoke(name, args);
    }
}
