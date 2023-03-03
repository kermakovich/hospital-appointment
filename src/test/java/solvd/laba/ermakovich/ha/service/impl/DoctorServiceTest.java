package solvd.laba.ermakovich.ha.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import solvd.laba.ermakovich.ha.domain.SearchDoctorCriteria;
import solvd.laba.ermakovich.ha.domain.UserRole;
import solvd.laba.ermakovich.ha.domain.doctor.Doctor;
import solvd.laba.ermakovich.ha.domain.doctor.Specialization;
import solvd.laba.ermakovich.ha.domain.exception.IllegalOperationException;
import solvd.laba.ermakovich.ha.domain.hospital.Department;
import solvd.laba.ermakovich.ha.repository.DoctorRepository;
import solvd.laba.ermakovich.ha.service.AccountClient;
import solvd.laba.ermakovich.ha.service.UserInfoService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DoctorServiceTest {

    @Mock
    private UserInfoService userInfoService;

    @Mock
    private DoctorRepository doctorRepository;

    @Mock
    private AccountClient accountClient;

    @InjectMocks
    private DoctorServiceImpl doctorService;


    @Test
    void verifyCreateSuccessfulTest() {
        final long doctorId = 1L;
        Doctor expectedDoctor = getDoctorDentistFromDental();
        expectedDoctor.setId(doctorId);
        given(userInfoService.create(any(Doctor.class))).willReturn(getDoctorDentistFromDental());
        doAnswer((invocation)-> {
            Doctor doctor = invocation.getArgument(0);
            doctor.setId(doctorId);
            return null;
        }).when(doctorRepository).save(any(Doctor.class));

        Doctor newDoctor = doctorService.create(getDoctorDentistFromDental());

        assertEquals(expectedDoctor, newDoctor, "doctors are not equal");
        verify(doctorRepository, times(1)).save(any(Doctor.class));
    }

    @Test
    void verifyCreateThrowsIllegalOperationExceptionTest() {
        Doctor doctorWithWrongRole = getDoctorDentistFromDental();
        doctorWithWrongRole.setRole(UserRole.PATIENT);

        assertThrows(IllegalOperationException.class,() -> doctorService.create(doctorWithWrongRole),
                "Exception wasn`t thrown");
        verify(doctorRepository, never()).save(any(Doctor.class));
    }

    @Test
    void verifyIsExistByIdTest() {
        given(doctorRepository.isExistById(anyLong())).willReturn(true);

        Boolean actualResponse = doctorService.isExistById(1L);

        assertEquals(true, actualResponse, "boolean types are not equal");
        verify(doctorRepository, times(1)).isExistById(Mockito.anyLong());
    }


    public static Object[][] getSearchDoctorCriteria() {
        return new Object [][] {
                {new SearchDoctorCriteria(Department.THERAPEUTIC, null), getTherapeuticDoctorList()},
                {new SearchDoctorCriteria(Department.THERAPEUTIC, Specialization.THERAPIST), getTherapeuticDoctorList()},
                {new SearchDoctorCriteria(), getFullDoctorList()}
        };
    }

    @ParameterizedTest
    @MethodSource("getSearchDoctorCriteria")
    void verifyRetrieveAllBySearchCriteriaTest(SearchDoctorCriteria criteria, List<Doctor> appointmentList) {
        doReturn(appointmentList).when(doctorRepository).findAllBySearchCriteria(criteria);
        List<Doctor> doctorsTest = doctorService.retrieveAllBySearchCriteria(criteria);

        assertEquals(appointmentList, doctorsTest, "doctor lists are not equal");
        verify(doctorRepository, times(1)).findAllBySearchCriteria(any(SearchDoctorCriteria.class));
    }


    private static Doctor getDoctorDentistFromDental() {
        Doctor doctor = new Doctor();
        doctor.setName("Lera");
        doctor.setSurname("Parova");
        doctor.setFatherhood("Valerievna");
        doctor.setBirthday(LocalDate.of(2001, 9, 23));
        doctor.setEmail("myemail@mail.ru");
        doctor.setRole(UserRole.DOCTOR);
        doctor.setPassword("$2a$10$UmzxqbNCl9aBTtmkdAKajeXFrir6AHD6I3kE/MiLWU.W1RWLMvnYq");
        doctor.setEmail("myemail@gmail.com");
        doctor.setDepartment(Department.DENTAL);
        doctor.setSpecialization(Specialization.DENTIST);
        return doctor;
    }

    private static Doctor getDoctorTherapistFromTherapeutic() {
        Doctor doctor = getDoctorDentistFromDental();
        doctor.setSpecialization(Specialization.THERAPIST);
        doctor.setDepartment(Department.THERAPEUTIC);
        return doctor;
    }

    private static List<Doctor> getFullDoctorList() {
        List<Doctor> doctorList = new ArrayList<>();
        doctorList.add(getDoctorDentistFromDental());
        doctorList.add(getDoctorTherapistFromTherapeutic());
        doctorList.add(getDoctorTherapistFromTherapeutic());
        return doctorList;
    }

    private static List<Doctor> getTherapeuticDoctorList() {
        List<Doctor> doctorList = new ArrayList<>();
        doctorList.add(getDoctorTherapistFromTherapeutic());
        doctorList.add(getDoctorTherapistFromTherapeutic());
        return doctorList;
    }

}