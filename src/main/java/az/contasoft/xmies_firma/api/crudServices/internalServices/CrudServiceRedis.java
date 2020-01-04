package az.contasoft.xmies_firma.api.crudServices.internalServices;

import az.contasoft.xmies_firma.api.crudServices.internal.SaveFirmaRequest;
import az.contasoft.xmies_firma.api.crudServices.internal.UpdateFirmaRequest;
import az.contasoft.xmies_firma.db.entity.Firma;
import az.contasoft.xmies_firma.util.CachService;
import az.contasoft.xmies_firma.util.RedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class CrudServiceRedis {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final RedisService redisService;
    private final CachService cachService;

    public CrudServiceRedis(RedisService redisService, CachService cachService) {
        this.redisService = redisService;
        this.cachService = cachService;
    }


    public ResponseEntity<Firma> saveFirma(SaveFirmaRequest saveFirmaRequest) {
        try {
            Firma firma = new Firma();

            firma.setAdi(saveFirmaRequest.getAdi());
            firma.setAddress(saveFirmaRequest.getAddress());
            firma.setTelefon(saveFirmaRequest.getTelefon());
            firma.setIsActive(1);
            firma = cachService.saveOrUpdate(firma);
            logger.info("\n→→→CRUD_SERVICE: saved Successfully\n\n");
            return new ResponseEntity<>(firma, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("\n→→→CRUD_SERVICE: saving error e: {}, e: {}\n\n", e, e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    public ResponseEntity<Firma> updateFirma(UpdateFirmaRequest updateFirmaRequest){
        try {
            //Firma firma = repoFirma.findByIdFirmaAndIsActive(updateFirmaRequest.getIdFirma(),1);
            Firma firma = cachService.getAllFirmaMapRedis().get(updateFirmaRequest.getIdFirma());
            if (firma == null) {
                logger.info("\n→→→CRUD_SERVICE: Firma not found\n\n");
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }
            firma.setIdFirma(updateFirmaRequest.getIdFirma());
            firma.setAdi(updateFirmaRequest.getAdi());
            firma.setAddress(updateFirmaRequest.getAddress());
            firma.setTelefon(updateFirmaRequest.getTelefon());
            firma = cachService.saveOrUpdate(firma);
            logger.info("\n→→→CRUD_SERVICE: updated Successfully\n\n");
            return new ResponseEntity<>(firma, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("\n→→→CRUD_SERVICE: error updating e: {}, e: {}\n\n", e, e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


//    public ResponseEntity<String> deleteFirma(Long idFirma) {
//        try {
//            //Firma firma = repoFirma.findByIdFirmaAndIsActive(idFirma,1);
//            Firma firma = hazelcastUtility.getMapOfFirma().get(idFirma);
//            if (firma == null) {
//                logger.info("\n→→→CRUD_SERVICE: firma not found\n\n");
//                return new ResponseEntity<>("ALINMADI", HttpStatus.NO_CONTENT);
//            } else {
//                logger.info("→→→CRUD_SERVICE: found firma : {}", firma);
//                firma.setIsActive(0);
//                hazelcastUtility.deleteFirma(idFirma);
//                logger.info("\n→→→CRUD_SERVICE: deleted successfully\n\n");
//                return new ResponseEntity<>("SILINDI", HttpStatus.OK);
//            }
//        } catch (Exception e) {
//            logger.error("\n→→→CRUD_SERVICE: error deleting e: {}, e: {}\n\n", e, e);
//            return new ResponseEntity<>("XETA", HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
}
