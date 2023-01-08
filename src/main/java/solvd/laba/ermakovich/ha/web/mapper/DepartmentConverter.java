package solvd.laba.ermakovich.ha.web.mapper;

import org.springframework.core.convert.converter.Converter;
import solvd.laba.ermakovich.ha.domain.hospital.Department;

public class DepartmentConverter implements Converter<String, Department> {

    @Override
    public Department convert(String source) {
        return Department.valueOf(source.toUpperCase());
    }
}
