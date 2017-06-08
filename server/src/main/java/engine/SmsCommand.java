package engine;

import org.apache.log4j.Logger;
import twitter.TwitterManager;
import twitter4j.TwitterException;
import web.PageManager;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;


public class SmsCommand {

    private static final Logger LOG = Logger.getLogger(SmsCommand.class);

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

        if (!commands.containsKey(cmd.toLowerCase())){
            LOG.error("command " +cmd.toLowerCase() + " not exist");
            return error();
        }
        Object o = null;
        Method m;
        try {
            m = this.getClass().getMethod(commands.get(cmd.toLowerCase()).getName(),commands.get(cmd.toLowerCase()).getParameterTypes());
            o = m.invoke(this, data);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            LOG.error(e.getMessage());
            return error();
        }
        return (String) o;
    }

    public String get(String data){
        return "WEB:"+pageManager.getWebpage(data);
    }

    public String next(String data){
         return "WEBNEXT:" + pageManager.nexWebPage(data);
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
        try {
            return "TWITTERHOME:"+twitterManager.getHomeTimeline(id);
        } catch (TwitterException e) {
            LOG.warn(e.getMessage());
            return "TWITTERHOME:FAILURE";
        }
    }
    public String twitternext(String data){
        String id = data;
        try {
            return "TWITTERNEXT:"+twitterManager.getNextHomeTimeline(id);
        } catch (TwitterException e) {
            LOG.warn(e.getMessage());
            return "TWITTERNEXT:FAILURE";
        }
    }

    public String tweet(String data){
        String [] dataArray = data.split(",",2);
        if (dataArray.length != 2) {
            return "TWEET:FAILURE";
        }
        if(twitterManager.postTweet(dataArray[0],dataArray[1]))
            return "TWEET:SUCCESS";
        else
            return "TWEET:FAILURE";
    }


    private String error(){
        return  "WEB:<h2>Mauvaise commande</h2>";
    }
}
