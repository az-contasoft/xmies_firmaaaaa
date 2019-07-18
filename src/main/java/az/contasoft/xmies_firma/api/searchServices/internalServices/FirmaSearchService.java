package az.contasoft.xmies_firma.api.searchServices.internalServices;

import az.contasoft.xmies_firma.db.entity.Firma;
import az.contasoft.xmies_firma.util.HazelcastUtility;
import com.hazelcast.core.IMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class FirmaSearchService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final HazelcastUtility hazelcastUtility;

    @Autowired
    public FirmaSearchService( HazelcastUtility hazelcastUtility) {
        this.hazelcastUtility = hazelcastUtility;
    }

    public ResponseEntity<IMap<Long,Firma>> getAll() {
        try{
            IMap<Long,Firma> mapOfFirma = hazelcastUtility.getMapOfFirma();
            if(mapOfFirma==null || mapOfFirma.isEmpty()){
                logger.info("\n→→→SEARCH_SERVICE: firma map not found\n\n");
                return new ResponseEntity<>(null,HttpStatus.NO_CONTENT);
            }else {
                logger.info("\n→→→SEARCH_SERVICE: mapOfFirma size: {}\n\n", mapOfFirma.size());
                return new ResponseEntity<>(mapOfFirma,HttpStatus.OK);
            }
        }catch (Exception e){
            logger.error("\n→→→SEARCH_SERVICE: error getAllAsMap e: {}, e: {}\n\n", e, e);
            return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Firma> getFirma(long idFirma) {
        try{
            Firma firma = hazelcastUtility.getFirma(idFirma);
            if(firma==null){
                logger.info("\n→→→SEARCH_SERVICE: firma not found\n\n");
                return new ResponseEntity<>(null,HttpStatus.NO_CONTENT);
            }else{
                logger.info("\n→→→SEARCH_SERVICE: firma found : {}\n\n", firma);
                return new ResponseEntity<>(firma,HttpStatus.OK);
            }
        }catch (Exception e){
            logger.error("\n→→→SEARCH_SERVICE: error getFirma e: {}, e: {}\n\n", e, e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    public ResponseEntity<String> startCaching() {
        hazelcastUtility.startCaching();
        return new ResponseEntity<>("Cached", HttpStatus.OK);
    }
}
