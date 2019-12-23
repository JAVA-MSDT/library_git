package com.epam.library.model.configuration;

import com.epam.library.util.constant.BeanNameHolder;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@ComponentScan(basePackages = BeanNameHolder.BASE_PACKAGE)
@EnableTransactionManagement(proxyTargetClass = true)
@EnableWebMvc
public class RootConfigurator {

}
