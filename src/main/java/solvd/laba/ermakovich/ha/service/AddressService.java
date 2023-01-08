package solvd.laba.ermakovich.ha.service;

import solvd.laba.ermakovich.ha.domain.Address;

import java.util.Optional;

public interface AddressService {

    Address save(Address address);

    Optional<Address> find(Address address);
}
