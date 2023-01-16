package solvd.laba.ermakovich.ha.repository;

import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

@Configuration
@RequiredArgsConstructor
public class MyBatisConfig {

    private final DataSource dataSource;

    @Bean
    @ConditionalOnProperty(prefix = "persistence", name = "type", havingValue = "mybatis")
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);
        factoryBean.setConfigLocation(new PathMatchingResourcePatternResolver().getResource("mybatisConfig.xml"));
        return factoryBean.getObject();
    }

    @Bean
    @ConditionalOnProperty(prefix = "persistence", name = "type", havingValue = "mybatis")
    public UserRepository userRepository() throws Exception {
        SqlSessionTemplate template = new SqlSessionTemplate(sqlSessionFactory());
        return template.getMapper(UserRepository.class);
    }

    @Bean
    @ConditionalOnProperty(prefix = "persistence", name = "type", havingValue = "mybatis")
    public ReviewRepository reviewRepository() throws Exception {
        SqlSessionTemplate template = new SqlSessionTemplate(sqlSessionFactory());
        return template.getMapper(ReviewRepository.class);
    }

    @Bean
    @ConditionalOnProperty(prefix = "persistence", name = "type", havingValue = "mybatis")
    public DoctorRepository doctorRepository() throws Exception {
        SqlSessionTemplate template = new SqlSessionTemplate(sqlSessionFactory());
        return template.getMapper(DoctorRepository.class);
    }

    @Bean
    @ConditionalOnProperty(prefix = "persistence", name = "type", havingValue = "mybatis")
    public PatientRepository patientRepository() throws Exception {
        SqlSessionTemplate template = new SqlSessionTemplate(sqlSessionFactory());
        return template.getMapper(PatientRepository.class);
    }

    @Bean
    @ConditionalOnProperty(prefix = "persistence", name = "type", havingValue = "mybatis")
    public PatientCardRepository patientCardRepository() throws Exception {
        SqlSessionTemplate template = new SqlSessionTemplate(sqlSessionFactory());
        return template.getMapper(PatientCardRepository.class);
    }
}
