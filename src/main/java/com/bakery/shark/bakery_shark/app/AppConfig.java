package com.bakery.shark.bakery_shark.app;

import com.bakery.shark.bakery_shark.app.converter.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.format.FormatterRegistry;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.LocaleContextResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import javax.persistence.EntityManagerFactory;
import java.util.Locale;

@Configuration
@EnableWebMvc
@EnableTransactionManagement
@ComponentScan(basePackages = "com.bakery.shark.bakery_shark")
@EnableJpaRepositories(basePackages = "com.bakery.shark.bakery_shark")
public class AppConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("home");
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/403").setViewName("403");
        registry.addViewController("/user/create-user").setViewName("registration");

    }

    @Bean
    public JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }


        public void addResourceHandlers(final ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/favicon.ico").addResourceLocations("classpath:/static/");
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(getIngredientConverter());
        registry.addConverter(getRecipeConverter());
        registry.addConverter(getRecipeItemConverter());
        registry.addConverter(getProductConverter());
        registry.addConverter(getManufacturedConverter());
        registry.addConverter(getManufactureItemConverter());
        registry.addConverter(getRoleConverter());
    }

    @Bean
    public RoleConverter getRoleConverter() {
        return new RoleConverter();
    }

    @Bean
    public IngredientConverter getIngredientConverter() {
        return new IngredientConverter();
    }

    @Bean
    public RecipeConverter getRecipeConverter() {
        return new RecipeConverter();
    }

    @Bean
    public RecipeItemConverter getRecipeItemConverter() {
        return new RecipeItemConverter();
    }

    @Bean
    public ProductConverter getProductConverter() {
        return new ProductConverter();
    }

    @Bean
    public ManufacturedConverter getManufacturedConverter() {
        return new ManufacturedConverter();
    }

    @Bean
    public ManufactureItemConverter getManufactureItemConverter() {
        return new ManufactureItemConverter();
    }

//    @Override
//    public void configureDefaultServletHandling(
//            DefaultServletHandlerConfigurer configurer) {
//        configurer.enable();
//    }

    @Bean(name = "localeResolver")
    public LocaleContextResolver getLocaleContextResolver() {
        SessionLocaleResolver localeResolver = new SessionLocaleResolver();
        localeResolver.setDefaultLocale(new Locale("pl", "PL"));
        return localeResolver;
    }

    @Bean
    public LocalValidatorFactoryBean validator() {
        return new LocalValidatorFactoryBean();
    }

    @Bean
    public ResourceBundleMessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("ValidationMessages");
        messageSource.setUseCodeAsDefaultMessage(true);
        return messageSource;
    }
}
