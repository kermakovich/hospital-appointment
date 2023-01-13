package solvd.laba.ermakovich.ha.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import solvd.laba.ermakovich.ha.domain.Address;
import solvd.laba.ermakovich.ha.repository.AddressRepository;
import solvd.laba.ermakovich.ha.service.AddressService;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;

    @Override
    @Transactional
    public Address create(Address address) {
        return addressRepository.find(address)
                .orElseGet(() -> {
                    addressRepository.save(address);
                    return address;
                });
    }

}
