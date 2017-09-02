package com.gus;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class JedisPoolUtils {

    private static JedisPool jedisPool = null;
    private static JedisPoolConfig jedisPoolConfig = null;
    private static String auth = null;
    static {
        try {

            Properties properties = new Properties();
            InputStream inputStream = JedisPoolUtils.class.getClassLoader().getResourceAsStream("jedisPoolConfig.properties");
            properties.load(inputStream);
            String maxTotal = properties.getProperty("redis.MaxTotal");
            String maxIdle = properties.getProperty("redis.MaxIdle");
            String minIdle = properties.getProperty("redis.MinIdle");
            String host = properties.getProperty("redis.host");
            String port = properties.getProperty("redis.port");
            auth = properties.getProperty("redis.auth");

            jedisPoolConfig = new JedisPoolConfig();
            jedisPoolConfig.setMaxTotal(parseInt(maxTotal));
            jedisPoolConfig.setMaxIdle(parseInt(maxIdle));
            jedisPoolConfig.setMinIdle(parseInt(minIdle));

            jedisPool = new JedisPool(jedisPoolConfig, host, parseInt(port));


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Jedis getJedis() {
        Jedis jedis = jedisPool.getResource();
        jedis.auth(auth);
        return jedis;
    }

    private static int parseInt(String str) {
        for (int i = 0; i < str.length(); i++) {
            if (!Character.isDigit(str.charAt(i))) {
                return -1;
            }
        }
        return Integer.parseInt(str);
    }

}
