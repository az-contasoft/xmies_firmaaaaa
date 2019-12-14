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

    private static Logger logger = LoggerFactory.getLogger(RedisService.class);

    @Autowired
    HazelcastMapConfigurations hazelcastMapConfigurations;

    private HashOperations hashOperations;
    private RedisTemplate redisTemplate;




    public RedisService(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.hashOperations = this.redisTemplate.opsForHash();
    }


    public <K,V> boolean add(K k, V v,RedisMapKey redisMapKey){
        try {
            hashOperations.put(redisMapKey.getMapName(),k,v);
//            switch (redisMapKey) {
//                case MAP_OF_PATIENT: {
//                    hashOperations.put(getMapOfPatient(), k, v);
//                }
//                break;
//                case MAP_OF_PATIENTINFO: {
//                    hashOperations.put(getMapOfPatientInfo(), k, v);
//                }break;
//                case MAP_OF_ADDRESS:{
//                    hashOperations.put(getMapOfAddress(),k,v);
//                }
//                break;
//                case MAP_OF_PROPERTIES:{
//                    hashOperations.put(getMapOfProperties(),k,v);
//                }
//            }
        }catch (Exception e){
            logger.error("Error putting redis : "+e,e);
            return false;
        }

        return true;
    }


    public <K,V> Map<K,V> get(RedisMapKey redisMapKey){
        try{
//        return hashOperations.values(redisMapKey.getMapName());
            return hashOperations.entries(redisMapKey.getMapName());

//            switch (redisMapKey){
//                case MAP_OF_PATIENTINFO: return hashOperations.entries(personalData);
//                case MAP_OF_PATIENT: return  hashOperations.entries(personal);
////                case PERSONAL_RELATIVES: return hashOperations.entries(personalRelatives);
//                default:return null;
//            }
        }catch (Exception e){
            logger.error("Error getting map : "+e, e);
            return null;
        }
    }

//    public <K, V> V getDataByKey(K k, RedisMapKey redisMapKey){
//        try{
//            logger.info("trying to get data from redis...");
//            switch (redisMapKey){
//                case PERSONAL:return ((Map<K,V>)hashOperations.entries(personal)).get(k);
//                case PERSONAL_DATA: return ((Map<K,V>)hashOperations.entries(personalData)).get(k);
//                case PERSONAL_RELATIVES: return ((Map<K,V>)hashOperations.entries(personalRelatives)).get(k);
//                default:return null;
//            }
//        }catch (Exception e){
//            logger.error("Error getting data by key : "+e,e);
//            return null;
//        }
//    }
}
