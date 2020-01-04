package az.contasoft.xmies_firma.util;

import az.contasoft.xmies_firma.db.entity.Firma;
import az.contasoft.xmies_firma.db.service.DatabaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class CachService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    RedisService redisService;

    @Autowired
    DatabaseService databaseService;


    public Firma saveOrUpdate(Firma firma) throws Exception{
        logger.info("{}", "trying to save or update firma, firma info :" + firma.toString());
        firma = databaseService.insertOrUpdate(firma);
        redisService.add(firma.getIdFirma(), firma, RedisMapKey.MAP_OF_FIRMA);
        return firma;
    }

    private Map<Long, Firma> createFirmaFromDB(){
        List<Firma> list = databaseService.getAll();
        logger.info("creating map from db started.");
        logger.info("list size from db : {}",list.size());
        Map<Long, Firma> map = new HashMap<>();
        for (Firma firma : list) {
            logger.info("firma added to map : {}",firma.toString());
            map.put(firma.getIdFirma(),firma);
        }
        return map;
    }

    public void startCacheForRedis(){
        logger.debug("starting to cache data for redis");
        try {
            Map<Long, Firma> map = createFirmaFromDB();
            for (Firma firma : map.values()) {
                logger.debug("adding firma [{}] to redis map ", firma);
                redisService.add(firma.getIdFirma(), firma, RedisMapKey.MAP_OF_FIRMA);
            }
        }catch (Exception e){
            logger.error("Error start cache : {}", e, e);
        }
    }



    public Map<Long, Firma> getAllFirmaMapRedis(){
        logger.debug("trying to get all firma map...");
        try {
            Map<Long, Firma> map = redisService.get(RedisMapKey.MAP_OF_FIRMA);
            if(map == null || map.isEmpty()){
                logger.debug("map of firma not found");
                startCacheForRedis();
                map = redisService.get(RedisMapKey.MAP_OF_FIRMA);
            }
            if(map == null || map.isEmpty()){
                logger.debug("redis not working getting from database...");
                List<Firma> list = databaseService.getAll();
                map = list.stream().collect(Collectors.toMap(Firma::getIdFirma, firma -> firma));
            }
            return map;
        }catch (Exception e){
            logger.error("Error getting firma map : {}", e, e);
            return null;
        }
    }

    public Firma getFirmaByIdFirma(long idFirma){
        logger.debug("trying to get firma from redis.. for idFirma : {}", idFirma);
        try {
            Map<Long, Firma> map = redisService.get(RedisMapKey.MAP_OF_FIRMA);
            Firma firma;
            if(map == null || map.isEmpty()){
                logger.info("map of firma is null or empty trying to start cache..");
                startCacheForRedis();
            }else {
                firma = map.get(idFirma);
                if (firma != null){
                    logger.debug("firma found from redis : {}", firma.toString());
                    return firma;
                }else {
                    logger.debug("firma [not] found from redis... firma id : {}", idFirma);
                }
            }
        }catch (Exception e){
            logger.error("Error getting firma : {}", e, e);
        }
        return null;
    }




}
