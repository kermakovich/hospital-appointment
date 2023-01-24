package solvd.laba.ermakovich.ha.web;

import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.Formatter;
import org.springframework.format.FormatterRegistry;
import org.springframework.lang.NonNull;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import solvd.laba.ermakovich.ha.web.mapper.DepartmentConverter;
import solvd.laba.ermakovich.ha.web.mapper.SpecializationConverter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private static final String dateFormat = "dd-MM-yyyy";
    private static final String dateTimeFormat = "dd-MM-yyyy HH:mm:ss";
    private static final String timeFormat = "HH:mm";

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new DepartmentConverter());
        registry.addConverter(new SpecializationConverter());
    }

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
        return builder -> builder.simpleDateFormat(dateTimeFormat)
                .serializerByType(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ofPattern(dateFormat)))
                .deserializerByType(LocalDate.class, new LocalDateDeserializer(DateTimeFormatter.ofPattern(dateFormat)))
                .serializerByType(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(dateTimeFormat)))
                .deserializerByType(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(dateTimeFormat)))
                .serializerByType(LocalTime.class, new LocalTimeSerializer(DateTimeFormatter.ofPattern(timeFormat)))
                .deserializerByType(LocalTime.class, new LocalTimeDeserializer(DateTimeFormatter.ofPattern(timeFormat)));
    }

    @Bean
    public Formatter<LocalDate> localDateFormatter() {
        return new Formatter<>() {

            @Override
            public LocalDate parse(@NonNull String text, @NonNull Locale locale) {
                return LocalDate.parse(text, DateTimeFormatter.ofPattern(dateFormat));
            }

            @Override
            public String print(LocalDate object, Locale locale) {
                return DateTimeFormatter.ofPattern(dateFormat).format(object);
            }
        };
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public OpenAPI customOpenApi() {
        return new OpenAPI().info(new Info()
                        .title("Hospital-appointment API")
                        .version("1.0")
                        .description("booking system for №3 Minsk city hospital")
                        .contact(new Contact()
                                        .email("kermakovich.laba@silnd.com")
                                        .name("Kseniya Ermakovich"))
                );
    }

}
