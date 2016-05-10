package io.falcon.interview.virtualboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableJms
@EnableJpaRepositories
@EnableTransactionManagement
public class VirtualBoardApplication {

    public static void main(String[] args) {
        SpringApplication.run(VirtualBoardApplication.class, args);
    }
}
