package az.contasoft.xmies_firma.util;

import az.contasoft.xmies_firma.db.entity.Firma;
import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HazelcastConfiguration {

    @Bean
    public Config config(){
        return new Config();
    }

    @Bean
    public HazelcastInstance instance(Config config){
        return Hazelcast.newHazelcastInstance(config);
    }

    @Bean
    public IMap<Long, Firma> mapOfFirma(HazelcastInstance instance){
        return instance.getMap("mapOfFirma");
    }
}
