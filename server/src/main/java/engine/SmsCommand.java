package engine;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;


public class SmsCommand {

    private PageManager pageManager;
    private Map<String, Method> commands;

    public SmsCommand(){
        pageManager = new PageManager();
        commands = initCommand();
    }

    private Map<String, Method> initCommand(){
        Map<String, Method> map = new HashMap<>();
        Method[] methods = this.getClass().getDeclaredMethods();
        for (Method m : methods){
            map.put(m.getName().toLowerCase(),m);
        }
        return map;
    }

    public String process(String request){
        String [] arrayRequest = request.split(":",2);
        String cmd = arrayRequest[0];
        String data = "";
        if(arrayRequest.length>1) {
            data = arrayRequest[1];
        }
        if (!commands.containsKey(cmd.toLowerCase())){ return error(); }
        Object o = null;
        Method m;
        try {
            m = this.getClass().getMethod(commands.get(cmd.toLowerCase()).getName(),commands.get(cmd.toLowerCase()).getParameterTypes());
            o = m.invoke(this, data);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            return error();
        }
        return (String) o;
    }

    public String get(String data){
        return pageManager.getWebpage(data);
    }

    public String next(String data){
         return pageManager.nexWebPage(data);
    }

    public String ok(String data){
        return "OK";
    }

    private String error(){
        return  "<h2>Mauvaise commande</h2>";
    }
}
