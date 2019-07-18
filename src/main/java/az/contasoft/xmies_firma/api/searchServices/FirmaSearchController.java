package az.contasoft.xmies_firma.api.searchServices;

import az.contasoft.xmies_firma.api.searchServices.internalServices.FirmaSearchService;
import az.contasoft.xmies_firma.db.entity.Firma;
import com.hazelcast.core.IMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/searchServices")
public class FirmaSearchController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final FirmaSearchService firmaSearchService;

    public FirmaSearchController(FirmaSearchService firmaSearchService) {
        this.firmaSearchService = firmaSearchService;
    }

    @GetMapping("/list")
    public ResponseEntity<IMap<Long,Firma>> getAll(){
        logger.info("{}","getting all from hazelcast");
        return firmaSearchService.getAll();
    }

    @GetMapping("/byIdFirma/{idFirma}")
    public ResponseEntity<Firma> getFirma(@PathVariable("idFirma") long idFirma){
        logger.info("{}","getting firma by idFirma from hazelcast");
        return firmaSearchService.getFirma(idFirma);
    }
    @GetMapping("/cache")
    public ResponseEntity<String> startCaching() {
        return firmaSearchService.startCaching();
    }
}
