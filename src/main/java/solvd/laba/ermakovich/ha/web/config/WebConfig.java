package solvd.laba.ermakovich.ha.web.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import solvd.laba.ermakovich.ha.web.mapper.DepartmentConverter;
import solvd.laba.ermakovich.ha.web.mapper.SpecializationConverter;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new DepartmentConverter());
        registry.addConverter(new SpecializationConverter());
    }
}
