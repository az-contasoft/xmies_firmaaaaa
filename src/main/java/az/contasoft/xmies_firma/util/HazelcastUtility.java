package az.contasoft.xmies_firma.util;

import az.contasoft.xmies_firma.db.entity.Firma;
import az.contasoft.xmies_firma.db.service.DatabaseService;
import com.hazelcast.core.IList;
import com.hazelcast.core.IMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
public class HazelcastUtility {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private final DatabaseService databaseService;
    private final IMap<Long, Firma> mapOfFirma;
    private final IList<Firma> listOfFirma;

    @Autowired
    public HazelcastUtility(DatabaseService databaseService, IMap<Long, Firma> mapOfFirma, IList<Firma> listOfFirmat) {
        this.databaseService = databaseService;
        this.mapOfFirma = mapOfFirma;
        this.listOfFirma = listOfFirmat;
    }

    public Firma saveOrUpdate(Firma firma) throws Exception {
        logger.info("{}", "trying to save or update firma, firma info :" + firma.toString());
        firma = databaseService.insertOrUpdate(firma);
        mapOfFirma.put(firma.getIdFirma(), firma);
        return firma;
    }

    public void deleteFirma(long idFirma) throws Exception {
        Firma firma = mapOfFirma.get(idFirma);
        if (firma == null) {

            firma = databaseService.getFirma(idFirma);
        }
        firma.setIsActive(0);
        databaseService.insertOrUpdate(firma);
        mapOfFirma.remove(firma.getIdFirma());
    }

    public IMap<Long, Firma> getMapOfFirma() {
        if (mapOfFirma.isEmpty()) {
            logger.info("\n→→→mapOfFirma was empty therefore trying to cache\n\n");
            startCaching();
        }
        logger.info("\n→→→HAZEL: got mapOfFirma.size(): {}\n\n", mapOfFirma.size());
        return mapOfFirma;
    }

    public IList<Firma> getListOfFirma() {
        if (listOfFirma.isEmpty()) {
            logger.info("\n→→→listOfFirma was empty therefore trying to cache\n\n");
            startCaching();
        }
        logger.info("\n→→→HAZEL: got listOfFirma.size(): {}\n\n", listOfFirma.size());
        return listOfFirma;
    }

    public Firma getFirma(long idFirma) {
        logger.info("\n→→→HAZEL: trying to get idFirma from hazelcast\n\n");
        Firma firma = mapOfFirma.get(idFirma);
        if (firma == null) {
            logger.info("\n→→→HAZEL: firma not found in hazelcast. trying to get from DB\n\n");
            if (mapOfFirma.size() > 0) {
                firma = databaseService.getFirma(idFirma);
                logger.info("\n→→→HAZEL: " + (firma == null ? "not found\n\n" : "found firma : {}\n\n"), firma);
                if (firma != null) {
                    mapOfFirma.put(idFirma, firma);
                }
            } else {
                logger.info("\n→→→HAZEL: mapOfFirma is empty. trying to cache\n\n");
                startCaching();
                firma = mapOfFirma.get(idFirma);
            }
        }
        return firma;
    }


    private void startCaching() {
        try {
            listOfFirma.clear();
            mapOfFirma.clear();
            List<Firma> listOfFirmaFromDB = databaseService.getAll();
            logger.info("\n→→→HAZEL: got listOfEmeliyatFromDB.size(): {}\n\n", listOfFirmaFromDB.size());
            if (!listOfFirmaFromDB.isEmpty()) {
                for (Firma firma : listOfFirmaFromDB) {
                    mapOfFirma.put(firma.getIdFirma(), firma);
                }
                listOfFirma.addAll(listOfFirmaFromDB);
            }

        } catch (Exception e) {
            logger.error("\n→→→HAZEL: error caching e: {}, e: {}\n\n", e, e);
        }
    }

    @PostConstruct
    public void init() {
        logger.info("\n→→→HAZEL: trying to init PostConstruct\n\n");
        startCaching();
    }
}

