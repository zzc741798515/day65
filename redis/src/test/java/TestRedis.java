import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class TestRedis {

    @Test
    public void test1(){
        Jedis jedis = new Jedis("127.0.0.1");//根据ip创建连接对象
        jedis.auth("123456");//设置登录认证
        String ping = jedis.ping();
        System.out.println(ping);
//        System.out.println(jedis.info());

        //通过jedis操作redis
        //key操作
        Set<String> keys = jedis.keys("*");
        for (String key : keys) {
            System.out.println(key);

        }
        if(jedis.exists("aofKey")){
            jedis.expire("aofKey", 10);
            try {
                Thread.sleep(8000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(jedis.ttl("aofKey"));
        }

        //value操作
        //string
        jedis.set("jedis","hello jedis");
        System.out.println(jedis.get("jedis"));

        //list
        List<String> list = jedis.lrange("listKey", 0, -1);
        for (String s : list) {
            System.out.println(s);
        }

        //set
        Set<String> myfriends = jedis.smembers("myfriends");
        for (String myfriend : myfriends) {
            System.out.println(myfriend);
        }

        //zset
        Set<String> music = jedis.zrevrange("music", 0, -1);
        for (String s : music) {
            System.out.println(s);
        }


        //hash
        Map<String, String> user1 = jedis.hgetAll("user1");
        Set<Map.Entry<String, String>> entries = user1.entrySet();
        for (Map.Entry<String, String> entry : entries) {
            System.out.println(entry.getKey()+"-"+entry.getValue());
        }

    }
}
