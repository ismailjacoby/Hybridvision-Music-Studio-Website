package be.technobel.makerhub_backend.bll.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.spring6.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring6.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;

/**
 * Enables Web MVC and configures Thymeleaf for view rendering.
 */
@Configuration
@EnableWebMvc
public class ThymeleafConfig {

    /**
     * Sets up Thymeleaf template resolver with predefined template locations and mode.
     * @return Configured template resolver.
     */
    @Bean
    public SpringResourceTemplateResolver templateResolver(){
        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
        templateResolver.setPrefix("classpath:/templates/"); // Sets the prefix for where to look for templates.
        templateResolver.setSuffix(".html"); // Sets the suffix to identify the template files.
        templateResolver.setTemplateMode(TemplateMode.HTML); // Sets the template mode to HTML.
        return templateResolver;
    }

    /**
     * Initializes Thymeleaf template engine with a custom resolver.
     * @return Configured template engine.
     */
    @Bean
    public SpringTemplateEngine templateEngine(){
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver()); // Sets the template resolver.
        return templateEngine;
    }

    /**
     * Configures view resolver for Thymeleaf with UTF-8 encoding.
     * @return Configured view resolver.
     */
    @Bean
    public ThymeleafViewResolver viewResolver() {
        ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
        viewResolver.setTemplateEngine(templateEngine()); // Sets the template engine.
        viewResolver.setCharacterEncoding("UTF-8"); // Sets the character encoding.
        return viewResolver;
    }
}
