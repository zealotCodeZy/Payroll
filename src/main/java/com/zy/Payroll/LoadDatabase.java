package com.zy.Payroll;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadDatabase {
    private static final Logger log=LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(EmployeeRepository repository, OrderRepository orderRepository){
        return args -> {
          log.info("preloading "+ repository.save(new Employee("James alpha","frontend engineer")));
          log.info("preloading "+ repository.save(new Employee("James beta","frontend UI")));
          log.info("preloading "+ repository.save(new Employee("Albert alpha","backend engineer")));

            orderRepository.save(new Order("MacBook air", Status.COMPLETED));
            orderRepository.save(new Order("MacBook Pro", Status.IN_PROGRESS));
            orderRepository.save(new Order("Iphone", Status.CANCELLED));


            orderRepository.findAll().forEach(order -> {
                log.info("Preloaded " + order);
            });

        };
    }
}
