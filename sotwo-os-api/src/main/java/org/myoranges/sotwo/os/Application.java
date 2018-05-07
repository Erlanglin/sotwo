package org.myoranges.sotwo.os;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages={"org.myoranges.sotwo.core", "org.myoranges.sotwo.os","org.myoranges.sotwo.db"})
@MapperScan("org.myoranges.sotwo.db.dao")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}