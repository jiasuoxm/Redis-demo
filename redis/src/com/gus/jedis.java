package com.gus;

import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class jedis {

    @Test
    public void JedisTest() throws Exception {
        //1.获得连接对象
        Jedis jedis = new Jedis("192.168.25.129",6379);

        //2.获取数据
        String username = jedis.get("username");
        System.out.println(username);

        //3.存储
        //这个不许要指定编码了，因为这种数据库使用=用的类似于map的形式，存的是什么，取的就是什么
        jedis.set("address", "北京");
        System.out.println(jedis.get("address"));
    }

    //通过jedis的pool获取jedis连接对象
    @Test
    public void  JedisPoolTest() throws Exception {
        //0.穿件连接池配置对象
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        //最大闲置个数
        jedisPoolConfig.setMaxIdle(30);
        //最小闲置个数
        jedisPoolConfig.setMinIdle(10);

        //最大连接数
        jedisPoolConfig.setMaxTotal(50);

        //1.创建连接池对象
        JedisPool jedisPool = new JedisPool(jedisPoolConfig,"192.168.25.129",6379);

        //2.从连接池获取连接资源
        Jedis jedis = jedisPool.getResource();

        //操作数据库
        jedis.set("key","value1");
        String value = jedis.get("key");
        System.out.println(value);

        //关闭资源，在这只是测试，所以说关pool，实际开发中，不会关pool，只会关jedis
        jedis.close();
        jedisPool.close();
    }

    @Test
    public void jedisUtilTest() {
        Jedis jedis = JedisPoolUtils.getJedis();
        jedis.set("key2","value2");
        System.out.println(jedis.get("key2"));
        jedis.close();
    }

}
