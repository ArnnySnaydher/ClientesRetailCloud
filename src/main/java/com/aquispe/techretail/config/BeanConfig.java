package com.aquispe.techretail.config;

import com.aquispe.techretail.application.service.ClienteService;
import com.aquispe.techretail.application.util.LifeExpectancyCalculator;
import com.aquispe.techretail.domain.port.out.ClienteRepositoryPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    @Bean
    public LifeExpectancyCalculator lifeExpectancyCalculator() {
        return new LifeExpectancyCalculator();
    }

    @Bean
    public ClienteService clienteService(ClienteRepositoryPort clienteRepositoryPort, LifeExpectancyCalculator lifeExpectancyCalculator) {
        return new ClienteService(clienteRepositoryPort, lifeExpectancyCalculator);
    }
}
