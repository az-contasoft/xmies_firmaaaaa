package az.contasoft.xmies_firma.api.searchServices;

import az.contasoft.xmies_firma.api.searchServices.internal.RequestText;
import az.contasoft.xmies_firma.api.searchServices.internalServices.SearchServiceRedis;
import az.contasoft.xmies_firma.db.entity.Firma;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/searchServices/redis")
public class SearchControllerRedis {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final SearchServiceRedis searchServiceRedis;

    public SearchControllerRedis(SearchServiceRedis searchServiceRedis) {
        this.searchServiceRedis = searchServiceRedis;
    }

    @GetMapping("/map")
    public ResponseEntity<Map<Long, Firma>> getAll(){
        logger.info("{}","getting all from redis");
        return searchServiceRedis.getAll();
    }

    @GetMapping("/byIdFirma/{idFirma}")
    public ResponseEntity<Firma> getFirma (@PathVariable("idFirma") long idFirma){
        logger.info("{}","getting firma by idFirma from redis");
        return searchServiceRedis.getFirma(idFirma);
    }

    @PostMapping("/byName")
    public ResponseEntity<List<Firma>> getFirmaByName(@RequestBody RequestText requestText){
        logger.info("{}","getting firma by enteredText from redis");
        return searchServiceRedis.getFirmaByName(requestText);
    }

    @GetMapping("/cache")
    public ResponseEntity<String> startCaching() {
        return searchServiceRedis.startCaching();
    }
}
