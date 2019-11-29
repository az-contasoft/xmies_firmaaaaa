package az.contasoft.xmies_firma.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HazelcastMapConfigurations {


    @Value("${hazelcast.map.mapOfFirma}")
    private String mapOfFirma;

    public HazelcastMapConfigurations() {
    }

    public String getMapOfFirma() {
        return mapOfFirma;
    }

    public void setMapOfFirma(String mapOfFirma) {
        this.mapOfFirma = mapOfFirma;
    }
}
