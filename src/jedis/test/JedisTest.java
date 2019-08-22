package jedis.test;

import jedis.util.JedisPoolUtils;
import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Jedis的测试类
 *
 * @author gjq
 * @create 2019-08-22-14:58
 */
public class JedisTest {

    @Test
    public void test1() {

        //获取连接
        Jedis jedis = new Jedis("localhost", 6379);

        //操作
        jedis.set("username", "zhangsan");

        //关闭资源
        jedis.close();
    }


    //String数据结构操作
    @Test
    public void test2() {

        //获取连接
        Jedis jedis = new Jedis(); //使用空参构造器，默认值为 localhost , 6379

        //操作
        //存储
        jedis.set("username", "zhangsan");
        //获取
        String username = jedis.get("username");

        System.out.println(username);


        //使用setXxx()方法存储可以指定过期时间的 key,value
        jedis.setex("activecode", 20, "aile");  //会将activecode:aile 这个键值对存入内存中，并且20秒后自动删除此键值对
        //关闭资源
        jedis.close();
    }


    // Hash 数据结构操作
    @Test
    public void test3() {

        //获取连接
        Jedis jedis = new Jedis(); //使用空参构造器，默认值为 localhost , 6379

        //操作
        jedis.hset("user", "name", "哈哈");
        jedis.hset("user", "age", "26");
        jedis.hset("user", "gender", "male");

        String name = jedis.hget("user", "name");
        String age = jedis.hget("user", "age");
        System.out.println(name + "的年龄为" + age);

        //获取hash的所有map中的数据
        Map<String, String> user = jedis.hgetAll("user");

        //使用keyset遍历map集合
        Set<String> users = user.keySet();

//        for (String key : user.keySet()) {

        for (String key : users) {

            String value = user.get(key);
            System.out.println(key + "==" + value);

        }

        //关闭资源
        jedis.close();
    }


    // list 数据结构操作
    @Test
    public void test4() {

        //获取连接
        Jedis jedis = new Jedis(); //使用空参构造器，默认值为 localhost , 6379

        //操作
        jedis.lpush("mylist", "a", "s", "d");
        jedis.rpush("mylist", "a", "s", "d");

        //获取所有
        List<String> mylist = jedis.lrange("mylist", 0, -1);
        System.out.println(mylist);

        String lpop1 = jedis.lpop("mylist");
        System.out.println(lpop1);
        String rpop1 = jedis.rpop("mylist");
        System.out.println(rpop1);

        List<String> mylist1 = jedis.lrange("mylist", 0, -1);
        System.out.println(mylist1);
        //关闭资源
        jedis.close();
    }


    // set 数据结构操作
    @Test
    public void test5() {

        //获取连接
        Jedis jedis = new Jedis(); //使用空参构造器，默认值为 localhost , 6379

        //操作
        jedis.sadd("myset", "a", "q", "z");

        Set<String> myset = jedis.smembers("myset");
        System.out.println(myset);

        //关闭资源
        jedis.close();
    }


    // sortedset 数据结构操作
    @Test
    public void test6() {

        //获取连接
        Jedis jedis = new Jedis(); //使用空参构造器，默认值为 localhost , 6379

        //操作
        jedis.zadd("mysortedset", 20, "haha");
        jedis.zadd("mysortedset", 50, "heihei");
        jedis.zadd("mysortedset", 30, "aiya");

        Set<String> mysortedset = jedis.zrange("mysortedset", 0, -1);
        System.out.println(mysortedset);

        //关闭资源
        jedis.close();
    }

    //jedis连接池的使用
    @Test
    public void test7() {

        //创建配置对象
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(20);//最大允许的连接数
        config.setMaxIdle(10);//最大的空闲连接
        //创建jedis连接池对象
        JedisPool pool = new JedisPool(config, "localhost", 6379);
        //JedisPool pool = new JedisPool(); //空参构造器
        //获取连接
        Jedis jedis = pool.getResource();
        //使用
        jedis.set("haha", "szxrdrftg");

        //关闭资源，归还到连接池中
        jedis.close();

    }



    //jedis连接池工具类的使用
    @Test
    public void test8() {

        //通过连接池工具类获取连接
        Jedis jedis = JedisPoolUtils.getJedis();

        //使用
        jedis.set("qwe", "asdfgh");

        //关闭资源，归还到连接池中
        jedis.close();

    }


}
