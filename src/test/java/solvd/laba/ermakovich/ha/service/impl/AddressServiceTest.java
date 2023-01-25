package solvd.laba.ermakovich.ha.service.impl;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import solvd.laba.ermakovich.ha.domain.Address;
import solvd.laba.ermakovich.ha.repository.AddressRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class AddressServiceTest {

    @Mock
    AddressRepository addressRepository;

    @InjectMocks
    AddressServiceImpl addressService;

    @Test
    void verifyCreateAddressAlreadyExistsTest() {
        Address expectedAddress = getAddress();
        given(addressRepository.find(any(Address.class))).willReturn(Optional.of(expectedAddress));

        Address actualAddress = addressService.create(getAddressWithoutId());

        assertEquals(expectedAddress, actualAddress, "addresses are not equal");
        verify(addressRepository, times(1)).find(any(Address.class));
        verify(addressRepository, never()).save(any(Address.class));
    }

    @Test
    void verifyCreateAddressDoesNotExistTest() {
        given(addressRepository.find(any(Address.class))).willReturn(Optional.empty());

        Address actualAddress = addressService.create(getAddressWithoutId());

        assertNotNull(actualAddress, "address is null");
        verify(addressRepository, times(1)).find(any(Address.class));
        verify(addressRepository, times(1)).save(any(Address.class));

    }

    private Address getAddressWithoutId() {
        return new Address(
                "minsk",
                "bogushevicha",
                23,
                34);
    }
    private Address getAddress() {
        return new Address(
                1L,
                "minsk",
                "bogushevicha",
                23,
                34);
    }


}
