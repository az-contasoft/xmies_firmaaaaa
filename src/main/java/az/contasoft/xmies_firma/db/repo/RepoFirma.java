package az.contasoft.xmies_firma.db.repo;

import az.contasoft.xmies_firma.db.entity.Firma;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface RepoFirma extends JpaRepository<Firma,Long> {

    Firma findByIdFirmaAndIsActive(long idFirma, int isActive);
    List<Firma> findAllByIsActiveOrderByIdFirmaDesc(int isActive);
//    List<Firma> findAllByIsActive(int isActive);
//    List<Firma> findAllByAdiAndIsActive(String adi,int isActive);
//    Firma findByAdi(String adi);
}
