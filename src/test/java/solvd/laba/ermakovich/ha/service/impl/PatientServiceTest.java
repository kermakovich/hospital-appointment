package solvd.laba.ermakovich.ha.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import solvd.laba.ermakovich.ha.domain.Address;
import solvd.laba.ermakovich.ha.domain.Patient;
import solvd.laba.ermakovich.ha.domain.PatientCard;
import solvd.laba.ermakovich.ha.domain.UserRole;
import solvd.laba.ermakovich.ha.domain.exception.IllegalOperationException;
import solvd.laba.ermakovich.ha.repository.PatientRepository;
import solvd.laba.ermakovich.ha.service.AddressService;
import solvd.laba.ermakovich.ha.service.PatientCardService;
import solvd.laba.ermakovich.ha.service.UserInfoService;

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PatientServiceTest {

    @Mock
    private UserInfoService userInfoService;

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private AddressService addressService;

    @Mock
    private PatientCardService patientCardService;

    @InjectMocks
    private PatientServiceImpl patientService;


    @Test
    void verifyCreatePatientSuccessfulTest() {
        Patient expectedPatient = getPatient();
        given(userInfoService.create(any(Patient.class))).willReturn(expectedPatient);
        given(addressService.create(any(Address.class))).willReturn(expectedPatient.getAddress());
        given(patientCardService.createByPatientId(anyLong())).willReturn(getPatientCard(expectedPatient));

        Patient actualPatient = patientService.create(getPatient());

        assertEquals(expectedPatient, actualPatient, "patients are not equal");
        verify(patientRepository, times(1)).save(any(Patient.class));
    }

    @Test
    void verifyCreateThrowsIllegalOperationExceptionTest() {
        Patient patientWithWrongRole = getPatient();
        patientWithWrongRole.setRole(UserRole.DOCTOR);

        assertThrows(IllegalOperationException.class,() -> patientService.create(patientWithWrongRole),
                "Exception wasn`t thrown");
        verify(patientRepository, never()).save(any(Patient.class));
    }

    @Test
    void verifyIsExistByIdTest() {
        final long patientId = 1L;
        given(patientRepository.isExistById(anyLong())).willReturn(true);

        Boolean actualResponse = patientService.isExistById(patientId);

        assertEquals(true, actualResponse, "boolean types are not equal");
        verify(patientRepository, times(1)).isExistById(Mockito.anyLong());
    }

    private Patient getPatient() {
        Patient patient = new Patient();
        patient.setId(1L);
        patient.setName("Lera");
        patient.setSurname("Parova");
        patient.setFatherhood("Valerievna");
        patient.setBirthday(LocalDate.of(2001, 9, 23));
        patient.setEmail("myemail@mail.ru");
        patient.setRole(UserRole.PATIENT);
        patient.setPassword("$2a$10$UmzxqbNCl9aBTtmkdAKajeXFrir6AHD6I3kE/MiLWU.W1RWLMvnYq");
        patient.setAddress(new Address("minsk", "bogushevicha", 24, 56));
        patient.setEmail("myemail@gmail.com");
        return patient;
    }

    private PatientCard getPatientCard(Patient patient) {
        return new PatientCard(1L, patient, UUID.randomUUID(), LocalDate.now());
    }

}