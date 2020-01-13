package az.contasoft.xmies_firma.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Map;
@Repository
public class RedisService {

    private static final Logger logger = LoggerFactory.getLogger(RedisService.class);

    private HashOperations hashOperations;
    private RedisTemplate redisTemplate;

    public RedisService(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.hashOperations = this.redisTemplate.opsForHash();
    }

    public <K, V> boolean add(K k, V v, RedisMapKey redisMapKey) {
        try {
            hashOperations.put(redisMapKey.getMapName(), k, v);
        } catch (Exception e) {
            logger.error("error e : {}, e : {}", e, e);
            return false;
        }
        return true;
    }

    public <K, V> Map<K, V> get(RedisMapKey redisMapKey) {
        try {
            return hashOperations.entries(redisMapKey.getMapName());
        } catch (Exception e) {
            logger.error("error e : {}, e : {}", e, e);
            return null;
        }
    }

    public <K> void remove(RedisMapKey redisMapKey, K k) {
        try {
            hashOperations.delete(redisMapKey.getMapName(), k);
        } catch (Exception e) {
            logger.error("error e : {}, e : {}", e, e);
        }
    }

    public void destroyMap(RedisMapKey redisMapKey) {
        try {
            hashOperations.getOperations().delete(redisMapKey.getMapName());
        } catch (Exception e) {
            logger.error("error e : {}, e : {}", e, e);
        }
    }
}
