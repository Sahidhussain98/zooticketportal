package com.practice.zooticketportal.config;


import com.practice.zooticketportal.repositories.MasterEstablishmentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// This class isn't mandatory to use.however The presence of this class suggests that there might be a need to perform
// some data initialization or setup logic when the application starts, but it depends on the requirements of your
// application.


@Configuration
public class DataInitializationConfig {

    @Autowired
    private MasterEstablishmentRepo masterEstablishmentRepo;

    @Bean
    public CommandLineRunner initializeData() {
        return args -> {
            //Your initialization logic goes here
//            MasterEstablishment establishment1 = new MasterEstablishment("School A", "Type A", "Admin A");
//            masterEstablishmentRepo.save(establishment1);
//
//            MasterEstablishment establishment2 = new MasterEstablishment("School B", "Type B", "Admin B");
//            masterEstablishmentRepo.save(establishment2);
//
//            MasterEstablishment establishment3 = new MasterEstablishment("School C", "Type C", "Admin C");
//            masterEstablishmentRepo.save(establishment3);
//
//            // You can add more initialization logic as needed
//        };
//
//    }

        };
    }


}



