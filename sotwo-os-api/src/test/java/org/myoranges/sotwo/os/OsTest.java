package org.myoranges.sotwo.os;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.myoranges.sotwo.os.config.ObjectStorageConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class OsTest {
    @Autowired
    private ObjectStorageConfig objectStorageConfig;

    @Test
    public void test() {
        System.out.println(objectStorageConfig.getAddress() + ":" + objectStorageConfig.getPort());
    }

}