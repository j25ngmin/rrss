package com.kakaopaysec.rrss.core.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

@Configuration
public class ContextMessageConfig implements WebMvcConfigurer {

    @Value("${spring.messages.basename}")
    String baseName;

    @Value("${spring.messages.encoding}")
    String defaultEncoding;

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource source = new ReloadableResourceBundleMessageSource();
        source.setBasename(baseName);
        source.setDefaultEncoding(defaultEncoding);
        return source;
    }

    @Bean
    public SessionLocaleResolver localResolver() {
        return new SessionLocaleResolver();
    }
    
    /*
    @Bean
    public LocalValidatorFactoryBean getValidator() {
        LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
        bean.setValidationMessageSource(messageSource());
        return bean;
    }
    */
    
}