package engine;

import twitter.TwitterManager;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;


public class SmsCommand {

    private PageManager pageManager;
    private Map<String, Method> commands;
    private TwitterManager twitterManager;

    public SmsCommand(){
        pageManager = new PageManager();
        twitterManager = new TwitterManager();
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
        return "ITS:AVAILABLE";
    }

    public String twitterconf(String data){
        List<String> tmp = new ArrayList<>(Arrays.asList(data.split(",")));

        if (tmp.size()!=3) {
            return "TWITTERCONF:FAILURE";
        }

        twitterManager.configureAccount(tmp.get(0),tmp.get(1),tmp.get(2));
        return "TWITTERCONF:SUCCESS";
    }

    public String twitterhome(String data){
        String id = data;
        return twitterManager.getHomeTimeline(id);
    }

    private String error(){
        return  "<h2>Mauvaise commande</h2>";
    }
}
