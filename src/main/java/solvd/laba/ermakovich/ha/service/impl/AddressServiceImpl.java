package solvd.laba.ermakovich.ha.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import solvd.laba.ermakovich.ha.domain.Address;
import solvd.laba.ermakovich.ha.repository.AddressRepository;
import solvd.laba.ermakovich.ha.service.AddressService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;

    @Override
    @Transactional
    public Address save(Address address) {
        addressRepository.save(address);
        return address;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Address> find(Address address) {
        return addressRepository.find(address);
    }

}
