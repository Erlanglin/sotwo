package org.myoranges.sotwo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

@SpringBootApplication(scanBasePackages = {
        "org.myoranges.sotwo",
        "org.myoranges.sotwo.core",
        "org.myoranges.sotwo.db",
        "org.myoranges.sotwo.os",
        "org.myoranges.sotwo.wx",
        "org.myoranges.sotwo.admin"})
@MapperScan("org.myoranges.sotwo.db.dao")
public class Application extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
    }

}