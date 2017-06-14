package engine;

import compression.ZLibCompression;
import org.apache.log4j.Logger;
import twitter.TwitterManager;
import twitter4j.TwitterException;
import weather.WeatherProxy;
import web.ImageManager;
import web.PageManager;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.List;

/**
 * SmsCommand class
 * @Author : ITS Team
 */


public class SmsCommand {

    private static final Logger LOG = Logger.getLogger(SmsCommand.class);

    private PageManager pageManager;
    private TwitterManager twitterManager;
    private Map<String, Method> commands;
    private String msgOriginator;

    public SmsCommand(){
        pageManager = new PageManager();
        twitterManager = new TwitterManager();
        commands = initCommand();
        msgOriginator="";
    }

    private Map<String, Method> initCommand(){
        Map<String, Method> map = new HashMap<>();
        Method[] methods = this.getClass().getDeclaredMethods();
        for (Method m : methods){
            map.put(m.getName().toLowerCase(),m);
        }
        return map;
    }

    public List<String> process(String request, String msgOriginator){
        this.msgOriginator = msgOriginator;

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
        return (List<String>) o;
    }

    public List<String> get(String data){
        ArrayList<String> result = new ArrayList<>();
        result.add("WEB:"+pageManager.getWebPage(data,msgOriginator));
        return result;
    }
    public List<String> next(String data){
        ArrayList<String> result = new ArrayList<>();
        result.add("WEBNEXT:" + pageManager.nextWebPage(data,msgOriginator));
        return result;
    }
    public List<String> endwebsite(String data){
        pageManager.removeWebPage(data,msgOriginator);
        ArrayList<String> result = new ArrayList<>();
        result.add("ENDWEBSITE:SUCCESS");
        return result;
    }

    public List<String> ok(String data){
        ArrayList<String> result = new ArrayList<>();
        result.add("ITS:AVAILABLE");
        return result;
    }

    public List<String> twitterconf(String data){
        ArrayList<String> result = new ArrayList<>();
        List<String> tmp = new ArrayList<>(Arrays.asList(data.split(",")));

        if (tmp.size()!=3) {
            result.add("TWITTERCONF:FAILURE");
            return result;
        }

        twitterManager.configureAccount(tmp.get(0),tmp.get(1),tmp.get(2));
        result.add("TWITTERCONF:SUCCESS");
        return result;
    }
    public List<String> twitterhome(String data){
        ArrayList<String> result = new ArrayList<>();
        String id = data;
        try {
            result.add("TWITTERHOME:"+twitterManager.getHomeTimeline(id));
            return result;
        } catch (TwitterException e) {
            LOG.warn(e.getMessage());
            result.add("TWITTERHOME:FAILURE");
            return result;
        }
    }
    public List<String> twitternext(String data){
        ArrayList<String> result = new ArrayList<>();
        String id = data;
        try {
            result.add("TWITTERNEXT:"+twitterManager.getNextHomeTimeline(id));
            return result;
        } catch (TwitterException e) {
            LOG.warn(e.getMessage());
            result.add("TWITTERNEXT:FAILURE");
            return result;
        }
    }
    public List<String> tweet(String data){
        ArrayList<String> result = new ArrayList<>();
        String [] dataArray = data.split(",",2);
        if (dataArray.length != 2) {
            result.add("TWEET:FAILURE");
            return result;
        }
        if(twitterManager.postTweet(dataArray[0],dataArray[1])) {
            result.add("TWEET:SUCCESS");
            return result;
        }
        else {
            result.add("TWEET:FAILURE");
            return result;
        }
    }

    public List<String> getimg(String data){
        ArrayList<String> result = new ArrayList<>();
        try {
            ArrayList<String> encodedImage = ImageManager.getImageStringInList(data);
            for (String img: encodedImage) {
                result.add("IMG:"+img);
            }
            result.add("IMGEND:YES");
            return result;
        } catch (IOException e) {
            LOG.error(e.getMessage());
        }
        result.add("IMG:FAILURE");
        return result;
    }

    public List<String> getweather(String data){
        ArrayList<String> resultList = new ArrayList<>();
        String result = null;
        try {
            result = WeatherProxy.getWeather(data);
        } catch (IOException e) {
            resultList.add("WEATHER:FAILURE");
            return resultList;
        }
        resultList.add("WEATHER:"+result);
        return resultList;
    }

    private List<String> error(){
        ArrayList<String> result = new ArrayList<>();
        result.add("WEB:<h2>Mauvaise commande</h2>");
        return  result;
    }
}
