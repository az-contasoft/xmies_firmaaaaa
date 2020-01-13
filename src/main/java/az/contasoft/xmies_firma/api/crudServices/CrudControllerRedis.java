package az.contasoft.xmies_firma.api.crudServices;

import az.contasoft.xmies_firma.api.crudServices.internal.SaveFirmaRequest;
import az.contasoft.xmies_firma.api.crudServices.internal.UpdateFirmaRequest;
import az.contasoft.xmies_firma.api.crudServices.internalServices.CrudServiceRedis;
import az.contasoft.xmies_firma.db.entity.Firma;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/crudServices/redis")
public class CrudControllerRedis {


    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final CrudServiceRedis crudServiceRedis;

    public CrudControllerRedis(CrudServiceRedis crudServiceRedis) {
        this.crudServiceRedis = crudServiceRedis;
    }

    @PostMapping("/add")
    public ResponseEntity<Firma> saveFirma(@RequestBody SaveFirmaRequest saveFirmaRequest) {
        logger.info("\n→→→CONTROLLER: got saveRequestFirma\n\n");
        return crudServiceRedis.saveFirma(saveFirmaRequest);
    }

    @PostMapping("/update")
    public ResponseEntity<Firma> updateFirma(@RequestBody UpdateFirmaRequest updateFirmaRequest) {
        logger.info("\n→→→CONTROLLER: got updateRequestFirma\n\n");
        return crudServiceRedis.updateFirma(updateFirmaRequest);
    }


    @GetMapping("/delete/{idFirma}")
    public ResponseEntity<String> deleteFirma(@PathVariable("idFirma") Long idFirma) {
        logger.info("\n→→→CONTROLLER: got idFirma for deleting\n\n");
        return crudServiceRedis.deleteFirma(idFirma);
    }
}
