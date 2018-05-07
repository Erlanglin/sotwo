package org.myoranges.sotwo.admin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages={"org.myoranges.sotwo.core", "org.myoranges.sotwo.admin","org.myoranges.sotwo.db"})
@MapperScan("org.myoranges.sotwo.db.dao")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}