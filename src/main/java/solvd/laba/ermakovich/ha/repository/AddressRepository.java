package solvd.laba.ermakovich.ha.repository;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import solvd.laba.ermakovich.ha.domain.Address;

import java.util.Optional;

@ConditionalOnProperty(prefix = "persistence", name = "type")
public interface AddressRepository {

    void save(Address address);

    Optional<Address> find(Address address);

}
