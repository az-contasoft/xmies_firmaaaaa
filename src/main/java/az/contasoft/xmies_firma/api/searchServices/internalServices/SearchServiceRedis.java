package az.contasoft.xmies_firma.api.searchServices.internalServices;

import az.contasoft.xmies_firma.api.searchServices.internal.RequestText;
import az.contasoft.xmies_firma.db.entity.Firma;
import az.contasoft.xmies_firma.util.CachService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class SearchServiceRedis {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final CachService cachService;

    public SearchServiceRedis(CachService cachService) {
        this.cachService = cachService;
    }

    public ResponseEntity<Map<Long, Firma>> getAll() {
        try {
            Map<Long, Firma> firmaMap = cachService.getMapOfFirma();
            if (firmaMap == null || firmaMap.isEmpty()) {
                logger.info("\n→→→SEARCH_SERVICE: firma map not found\n\n");
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            } else {
                logger.info("\n→→→SEARCH_SERVICE: firmaMap size: {}\n\n", firmaMap.size());
                return new ResponseEntity<>(firmaMap, HttpStatus.OK);
            }
        } catch (Exception e) {
            logger.error("\n→→→SEARCH_SERVICE: error getAllAsMap e: {}, e: {}\n\n", e, e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Firma> getFirma(long idFirma) {
        try {
            Firma firma = cachService.getFirmaByIdFirma(idFirma);
            if (firma == null) {
                logger.info("\n→→→SEARCH_SERVICE: firma not found\n\n");
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            } else {
                logger.info("\n→→→SEARCH_SERVICE: firma found : {}\n\n", firma);
                return new ResponseEntity<>(firma, HttpStatus.OK);
            }
        } catch (Exception e) {
            logger.error("\n→→→SEARCH_SERVICE: error getFirma e: {}, e: {}\n\n", e, e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    public ResponseEntity<List<Firma>> getFirmaByName(RequestText requestText) {
        try {
            Map<Long, Firma> mapOfFirma = cachService.getMapOfFirma();
            String[] enteredText = requestText.getEnteredText().split(" ");

            for(String enteredTextmas : enteredText){
                Map<Long, Firma> yeniMap = new HashMap<>();
                for(Firma firma : mapOfFirma.values()){
                    if(firma.getAdi().toLowerCase().contains(enteredTextmas.trim().toLowerCase())){
                        yeniMap.put(firma.getIdFirma(), firma);
                    }
                }
                mapOfFirma = yeniMap;
            }
            if(mapOfFirma == null || mapOfFirma.isEmpty()){
                return new ResponseEntity<>(null,HttpStatus.NO_CONTENT);
            }
            List<Firma> resultlist = new ArrayList<>();
            for(Firma firma : mapOfFirma.values()){
                resultlist.add(firma);
                if(resultlist.size() == 10){
                    return new ResponseEntity<>(resultlist, HttpStatus.OK);
                }
            }
            return new ResponseEntity<>(resultlist, HttpStatus.OK);
        }catch (Exception e){
            logger.error("\n→→→SEARCH_SERVICE: error getFirma byName e: {}, e: {}\n\n", e, e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<String> startCaching() {
        cachService.startCachingFirma();
        return new ResponseEntity<>("Cached", HttpStatus.OK);
    }

}
