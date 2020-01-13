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


    public Firma saveOrUpdateOrDelete(Firma firma) throws Exception{
        try {
            firma = databaseService.saveOrUpdateOrDelete(firma);
            if (firma.getIsActive() == 0) {
                logger.info("\n→→→REDIS: Trying to remove firma from REDIS map\n\n");
                redisService.remove(RedisMapKey.MAP_OF_FIRMA, firma.getIdFirma());
            } else {
                logger.info("\n→→→REDIS: trying to put firma to REDIS map\n\n");
                redisService.add(firma.getIdFirma(), firma, RedisMapKey.MAP_OF_FIRMA);
            }
            return firma;
        }catch (Exception e){
            logger.error("error e : {}, e : {}", e, e);
            return null;
        }
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

    public String startCachingFirma() {
        try {
            redisService.destroyMap(RedisMapKey.MAP_OF_FIRMA);
            List<Firma> listOfFirmaFromDB = databaseService.getAll();
            if (listOfFirmaFromDB == null || listOfFirmaFromDB.isEmpty()) {
                logger.info("listOfFirmaFromDB null ve ya isEmpty, cache olmayacaq");
                return "db error";
            } else {
                logger.info("listOfFirmaFromDB.size(): {}", listOfFirmaFromDB.size());
                for (Firma firma: listOfFirmaFromDB) {
                    redisService.add(firma.getIdFirma(), firma, RedisMapKey.MAP_OF_FIRMA);
                }
            }
            int size = redisService.get(RedisMapKey.MAP_OF_FIRMA).size();
            return "success cache size : " + size;
        } catch (Exception e) {
            logger.error("error e: {}, e: {}", e, e);
            return "error caching";
        }
    }



    public Map<Long, Firma> getMapOfFirma() {
        try {
            logger.info("trying to get mapOfFirma");
            Map<Long, Firma> mapOfFirma = redisService.get(RedisMapKey.MAP_OF_FIRMA);
            if (mapOfFirma == null || mapOfFirma.isEmpty()) {
                logger.info("mapOfFirma not found from redis trying to cache");
                String cachingResultFirma = startCachingFirma();
                logger.info("cachingResultFirma : {}", cachingResultFirma);
            }
            if (mapOfFirma == null || mapOfFirma.isEmpty()) {
                logger.info("redis not working getting from database...");
                mapOfFirma = databaseService.getAll().stream().collect(Collectors.toMap(Firma::getIdFirma, firma-> firma));
            }
            logger.info("mapOfFirma size : {}", mapOfFirma.size());
            return mapOfFirma;
        } catch (Exception e) {
            logger.error("error e: {}, e: {}", e, e);
            return null;
        }
    }

    public Firma getFirmaByIdFirma(long idFirma){
        try {
            logger.info("trying to get firma from redis");
            Firma firma= getMapOfFirma().get(idFirma);
            if (firma == null) {
                logger.info("firma not found from redis. trying to get from DB");
                firma = databaseService.getFirma(idFirma);
                if (firma == null) {
                    logger.info("firma not found for ID : {}", idFirma);
                } else {
                    logger.info("firma found : {}", idFirma);
                    redisService.add(firma.getIdFirma(), firma, RedisMapKey.MAP_OF_FIRMA);
                }
            }
            return firma;
        } catch (Exception e) {
            logger.error("error e: {}, e: {}", e, e);
            return null;
        }
    }




}
