package az.contasoft.xmies_firma.db.service;

import az.contasoft.xmies_firma.db.entity.Firma;
import az.contasoft.xmies_firma.db.repo.RepoFirma;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class DatabaseService {

    private final RepoFirma repoFirma;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public DatabaseService(RepoFirma repoFirma) {
        this.repoFirma = repoFirma;
    }

    public Firma saveOrUpdateOrDelete(Firma firma) throws Exception{
        logger.info("{}","Firma insert or update for : " +firma);
        return repoFirma.save(firma);
    }

    public Firma getFirma(long idFirma){
        logger.info("\n->->->REPO: trying to get firma \n\n");
        return repoFirma.findByIdFirmaAndIsActive(idFirma,1);
    }

    public List<Firma> getAll(){
        logger.info("\n->->->REPO: trying to get ALL firma \n\n");
        return repoFirma.findAllByIsActiveOrderByIdFirmaDesc(1);
    }
}
