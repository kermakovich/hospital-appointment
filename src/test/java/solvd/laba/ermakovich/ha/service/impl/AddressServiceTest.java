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
        expectedAddress.setId(1L);
        given(addressRepository.find(any(Address.class))).willReturn(Optional.of(expectedAddress));

        Address actualAddress = addressService.create(getAddress());

        assertEquals(expectedAddress, actualAddress, "addresses are not equal");
        verify(addressRepository, times(1)).find(any(Address.class));
        verify(addressRepository, never()).save(any(Address.class));
    }

    @Test
    void verifyCreateAddressDoesNotExistTest() {
        final long addressId = 1L;
        Address expectedAddress = getAddress();
        expectedAddress.setId(addressId);
        given(addressRepository.find(any(Address.class))).willReturn(Optional.empty());
        doAnswer((invocation)-> {
            Address address = invocation.getArgument(0);
            address.setId(addressId);
            return null;
        }).when(addressRepository).save(any(Address.class));

        Address actualAddress = addressService.create(getAddress());

        assertEquals(expectedAddress, actualAddress, "addresses are not equal");
        verify(addressRepository, times(1)).find(any(Address.class));
        verify(addressRepository, times(1)).save(any(Address.class));
    }

    private Address getAddress() {
        return new Address(
                "minsk",
                "bogushevicha",
                23,
                34);
    }

}
