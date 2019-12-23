package com.epam.library.model.configuration;

import com.epam.library.util.constant.BeanNameHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Slf4j
@Configuration
@ComponentScan(basePackages = BeanNameHolder.BASE_PACKAGE)
@EnableTransactionManagement(proxyTargetClass = true)
public class RootConfigurator {

}
