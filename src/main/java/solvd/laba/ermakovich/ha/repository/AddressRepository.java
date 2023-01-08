package solvd.laba.ermakovich.ha.repository;

import solvd.laba.ermakovich.ha.domain.Address;

import java.util.Optional;

public interface AddressRepository {

    void save(Address address);

    Optional<Address> find(Address address);
}
