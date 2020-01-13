//package az.contasoft.xmies_firma.api.crudServices.internalServices;
//
//import az.contasoft.xmies_firma.api.crudServices.internal.SaveFirmaRequest;
//import az.contasoft.xmies_firma.api.crudServices.internal.UpdateFirmaRequest;
//import az.contasoft.xmies_firma.db.entity.Firma;
//import az.contasoft.xmies_firma.util.HazelcastUtility;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Component;
//
//@Component
//public class FirmaCrudService {
//
//    private final HazelcastUtility hazelcastUtility;
//
//    private final Logger logger = LoggerFactory.getLogger(this.getClass());
//
//    @Autowired
//    public FirmaCrudService(HazelcastUtility hazelcastUtility) {
//        this.hazelcastUtility = hazelcastUtility;
//    }
//
//
//    public ResponseEntity<Firma> saveFirma(SaveFirmaRequest saveFirmaRequest) {
//        try {
//            Firma firma = new Firma();
////            firma.setIdFirma(saveFirmaRequest.getIdFirma());
//            firma.setAdi(saveFirmaRequest.getAdi());
//            firma.setAddress(saveFirmaRequest.getAddress());
//            firma.setTelefon(saveFirmaRequest.getTelefon());
//            firma.setIsActive(1);
//            //firma = repoFirma.save(firma);
//            firma = hazelcastUtility.saveOrUpdateOrDelete(firma);
//            logger.info("\n→→→CRUD_SERVICE: saved successfully\n\n");
//            return new ResponseEntity<>(firma, HttpStatus.OK);
//        } catch (Exception e) {
//            logger.error("\n→→→CRUD_SERVICE: saving error e: {}, e: {}\n\n", e, e);
//            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//
//
//    public ResponseEntity<Firma> updateFirma(UpdateFirmaRequest updateFirmaRequest){
//        try {
//            //Firma firma = repoFirma.findByIdFirmaAndIsActive(updateFirmaRequest.getIdFirma(),1);
//            Firma firma = hazelcastUtility.getMapOfFirma().get(updateFirmaRequest.getIdFirma());
//            if (firma == null) {
//                logger.info("\n→→→CRUD_SERVICE: firma not found\n\n");
//                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
//            }
//            firma.setIdFirma(updateFirmaRequest.getIdFirma());
//            firma.setAdi(updateFirmaRequest.getAdi());
//            firma.setAddress(updateFirmaRequest.getAddress());
//            firma.setTelefon(updateFirmaRequest.getTelefon());
//            firma = hazelcastUtility.saveOrUpdateOrDelete(firma);
//            logger.info("\n→→→CRUD_SERVICE: updated successfully\n\n");
//            return new ResponseEntity<>(firma, HttpStatus.OK);
//        } catch (Exception e) {
//            logger.error("\n→→→CRUD_SERVICE: error updating e: {}, e: {}\n\n", e, e);
//            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//
//
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
//}