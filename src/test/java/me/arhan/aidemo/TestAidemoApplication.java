package me.arhan.aidemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;

@TestConfiguration(proxyBeanMethods = false)
public class TestAidemoApplication {

    public static void main(String[] args) {
        SpringApplication.from(Application::main).with(TestAidemoApplication.class).run(args);
    }

}
