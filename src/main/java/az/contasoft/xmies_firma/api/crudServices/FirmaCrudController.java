//package az.contasoft.xmies_firma.api.crudServices;
//
//import az.contasoft.xmies_firma.api.crudServices.internal.SaveFirmaRequest;
//import az.contasoft.xmies_firma.api.crudServices.internal.UpdateFirmaRequest;
//import az.contasoft.xmies_firma.api.crudServices.internalServices.FirmaCrudService;
//import az.contasoft.xmies_firma.db.entity.Firma;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/crudServices")
//public class FirmaCrudController {
//
//    private final Logger logger = LoggerFactory.getLogger(this.getClass());
//    private final FirmaCrudService firmaCrudService;
//
//    @Autowired
//    public FirmaCrudController(FirmaCrudService firmaCrudService) {
//        this.firmaCrudService = firmaCrudService;
//    }
//
//
//    @PostMapping("/add")
//    public ResponseEntity<Firma> saveFirma(@RequestBody SaveFirmaRequest saveFirmaRequest) {
//        logger.info("\n→→→CONTROLLER: got saveRequestFirma\n\n");
//        return firmaCrudService.saveFirma(saveFirmaRequest);
//    }
//
//
//    @PostMapping("/update")
//    public ResponseEntity<Firma> updateFirma(@RequestBody UpdateFirmaRequest updateFirmaRequest) {
//        logger.info("\n→→→CONTROLLER: got updateRequestFirma\n\n");
//        return firmaCrudService.updateFirma(updateFirmaRequest);
//    }
//
//
//    @GetMapping("/delete/{idFirma}")
//    public ResponseEntity<String> deleteFirma(@PathVariable("idFirma") Long idFirma) {
//        logger.info("\n→→→CONTROLLER: got idFirma for deleting\n\n");
//        return firmaCrudService.deleteFirma(idFirma);
//    }
//}
