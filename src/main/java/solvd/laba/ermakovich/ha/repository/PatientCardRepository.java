package solvd.laba.ermakovich.ha.repository;

import org.apache.ibatis.annotations.Mapper;
import solvd.laba.ermakovich.ha.domain.PatientCard;

@Mapper
public interface PatientCardRepository {

    void save(PatientCard card);

}
