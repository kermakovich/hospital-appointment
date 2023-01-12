package solvd.laba.ermakovich.ha.web.mapper;

import org.springframework.core.convert.converter.Converter;
import solvd.laba.ermakovich.ha.domain.doctor.Specialization;

public class SpecializationConverter implements Converter<String, Specialization> {

    @Override
    public Specialization convert(String source) {
        return Specialization.valueOf(source.toUpperCase());
    }

}
