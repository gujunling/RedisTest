package jedis.util;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * jedis连接池的工具类
 *
 * @author gjq
 * @create 2019-08-22-16:12
 */
public class JedisPoolUtils {

    private static JedisPool jedisPool;

    static {
        //读取配置文件,使用类加载器
        InputStream is = JedisPoolUtils.class.getClassLoader().getResourceAsStream("jedis.properties");


        //创建Properties对象
        Properties pro = new Properties();
        try {
            //关联文件

                pro.load(is);


        } catch (IOException e) {
            e.printStackTrace();
        }

        //获取数据，设置到JedisPoolConfig中
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(Integer.parseInt(pro.getProperty("maxTotal")));
        config.setMaxIdle(Integer.parseInt(pro.getProperty("maxIdle")));

        //初始化JedisPool对象
        jedisPool = new JedisPool(config,pro.getProperty("host"),Integer.parseInt(pro.getProperty("port")));

    }

    /**
     * 获取连接的方法
     *
     * @return
     */
    public static Jedis getJedis() {

        return jedisPool.getResource();
    }
}
