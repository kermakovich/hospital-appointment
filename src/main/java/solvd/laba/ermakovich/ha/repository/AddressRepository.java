package solvd.laba.ermakovich.ha.repository;


import org.apache.ibatis.annotations.Mapper;
import solvd.laba.ermakovich.ha.domain.Address;

import java.util.Optional;

@Mapper
public interface AddressRepository {

    void save(Address address);

    Optional<Address> find(Address address);

}
