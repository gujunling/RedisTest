package service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dao.ProvinceDao;
import dao.impl.ProvinceDaoImpl;
import domain.Province;
import jedis.util.JedisPoolUtils;
import redis.clients.jedis.Jedis;
import service.ProvinceService;

import java.util.List;

/**
 * @author gjq
 * @create 2019-08-22-17:43
 */
public class ProvinceServiceImpl implements ProvinceService {

    private ProvinceDao dao = new ProvinceDaoImpl();

    @Override
    public List<Province> findAll() {
        return dao.findAll();
    }

    /**
     * 使用Redis缓存
     *
     * @return
     */
    @Override
    public String findAllJson() {
        //从Redis中获取数据
        //获取Redis客户端连接
        Jedis jedis = JedisPoolUtils.getJedis();
        String province_json = jedis.get("province");
        //判断province_json数据是否为null
        if (province_json == null || province_json.length() == 0) {
            //Redis中没有数据
            System.out.println("redis中没有数据，查询数据库");
            // 从数据库中查询
            List<Province> provinces = dao.findAll();

            //将list序列化为json
            ObjectMapper mapper = new ObjectMapper();
            try {
                province_json = mapper.writeValueAsString(provinces);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            //将json数据存入Redis中
            jedis.set("province", province_json);
            //注意，在此处，后面的参数千万不能使用引号包裹，否则会将"province_json"存入redis中，页面中下拉列表将无法实现

            //归还连接
            jedis.close();

        } else {
            System.out.println("redis中有数据，查询缓存");

        }

        return province_json;
    }


}
