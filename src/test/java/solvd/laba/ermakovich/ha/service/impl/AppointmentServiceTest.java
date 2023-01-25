package solvd.laba.ermakovich.ha.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import solvd.laba.ermakovich.ha.domain.Appointment;
import solvd.laba.ermakovich.ha.domain.AppointmentStatus;
import solvd.laba.ermakovich.ha.domain.SearchAppointmentCriteria;
import solvd.laba.ermakovich.ha.domain.doctor.Doctor;
import solvd.laba.ermakovich.ha.domain.exception.IllegalOperationException;
import solvd.laba.ermakovich.ha.domain.exception.ResourceDoesNotExistException;
import solvd.laba.ermakovich.ha.domain.hospital.OpeningHours;
import solvd.laba.ermakovich.ha.repository.AppointmentRepository;
import solvd.laba.ermakovich.ha.service.DoctorService;
import solvd.laba.ermakovich.ha.service.PatientService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AppointmentServiceTest {

    @Mock
    private AppointmentRepository appointmentRepository;

    @Mock
    private DoctorService doctorService;

    @Mock
    private PatientService patientService;

    @Mock
    private OpeningHours openingHours;

    @InjectMocks
    private AppointmentServiceImpl appointmentService;


    @Test
    void verifyGetTimeSlotsByDoctorIdAndDateSuccessfulTest() {
        final long doctorId = 1L;
        final LocalDate date = LocalDate.now().plusDays(1);
        List<LocalTime> expectedAppointmentsTime = getSlots();
        given(appointmentRepository.findAppointmentsTimeByDoctorIdAndDate(anyLong(), any(LocalDate.class)))
                .willReturn(expectedAppointmentsTime);

        List<LocalTime> actualTime = appointmentService.getTimeSlotsByDoctorIdAndDate(doctorId, date);

        assertEquals(expectedAppointmentsTime, actualTime, "time lists are not equal");
        verify(appointmentRepository, times(1)).findAppointmentsTimeByDoctorIdAndDate(anyLong(), any(LocalDate.class));
    }

    @Test
    void verifySaveAppointmentSuccessfulTest() {
        final long patientId = 1L;
        given(doctorService.isExistById(anyLong())).willReturn(true);
        given(patientService.isExistById(anyLong())).willReturn(true);
        given(openingHours.isWithinOpenHours(any(LocalTime.class))).willReturn(true);
        given(appointmentRepository.isExistByDoctorIdAndTime(any(Appointment.class))).willReturn(false);
        given(appointmentRepository.isExistByPatientIdAndTime(anyLong(), any(Appointment.class))).willReturn(false);

        Appointment actualAppointment = appointmentService.save(patientId, getAppointment());

        assertNotNull(actualAppointment, "appointment is null");
        verify(appointmentRepository, times(1)).save(anyLong(), any(Appointment.class));
    }


    @Test
    void verifySaveAppointmentWhenDoctorIdThrowsResourceDoesNotExistExceptionTest() {
        final long patientId = 1L;
        given(doctorService.isExistById(anyLong())).willReturn(false);

        var exception = assertThrows(ResourceDoesNotExistException.class, () -> appointmentService.save(patientId, getAppointment()),
                "Exception wasn`t thrown (that doctor with id doesn`t exist)");
        String expectedMessage = "doctor with id";
        assertTrue(exception.getMessage().contains(expectedMessage), "ResourceDoesNotExistException was thrown with wrong message");
    }

    @Test
    void verifySaveAppointmentWhenPatientIdThrowsResourceDoesNotExistExceptionByTest() {
        final long patientId = 1L;
        given(doctorService.isExistById(anyLong())).willReturn(true);
        given(patientService.isExistById(anyLong())).willReturn(false);
       // given(patientService.create(any())).willReturn(new Patient());

        var exception = assertThrows(ResourceDoesNotExistException.class, () -> appointmentService.save(patientId, getAppointment()),
                "Exception wasn`t thrown (that patient with id doesn`t exist)");
        String expectedMessage = "patient with id";
        assertTrue(exception.getMessage().contains(expectedMessage), "ResourceDoesNotExistException was thrown with wrong message");
    }

    @Test
    void verifySaveWhenTimeAppointmentIsWithinHospitalClosedHoursTest() {
        final long patientId = 1L;
        given(doctorService.isExistById(anyLong())).willReturn(true);
        given(patientService.isExistById(anyLong())).willReturn(true);
        given(openingHours.isWithinOpenHours(any(LocalTime.class))).willReturn(false);

        var exception = assertThrows(IllegalOperationException.class, () -> appointmentService.save(patientId, getAppointment()),
                "Exception wasn`t thrown (that hospital is closed at this time)");
        String expectedMessage = "Hospital is closed at this time";
        assertTrue(exception.getMessage().contains(expectedMessage), "IllegalOperationException was thrown with wrong message");
    }

    @Test
    void verifySaveWhenAppointmentIsAlreadyTakenTest() {
        final long patientId = 1L;
        given(doctorService.isExistById(anyLong())).willReturn(true);
        given(patientService.isExistById(anyLong())).willReturn(true);
        given(openingHours.isWithinOpenHours(any(LocalTime.class))).willReturn(true);
        given(appointmentRepository.isExistByDoctorIdAndTime(any(Appointment.class))).willReturn(true);

        var exception = assertThrows(IllegalOperationException.class, () -> appointmentService.save(patientId, getAppointment()),
                "Exception wasn`t thrown (that appointment is taken)");
        String expectedMessage = "Appointment is taken";
        assertTrue(exception.getMessage().contains(expectedMessage), "IllegalOperationException was thrown with wrong message");
    }

    @Test
    void verifySaveWhenPatientAlreadyHasAnotherAppointmentTest() {
        final long patientId = 1L;
        given(doctorService.isExistById(anyLong())).willReturn(true);
        given(patientService.isExistById(anyLong())).willReturn(true);
        given(openingHours.isWithinOpenHours(any(LocalTime.class))).willReturn(true);
        given(appointmentRepository.isExistByDoctorIdAndTime(any(Appointment.class))).willReturn(false);
        given(appointmentRepository.isExistByPatientIdAndTime(anyLong(), any(Appointment.class))).willReturn(true);

        var exception = assertThrows(IllegalOperationException.class, () -> appointmentService.save(patientId, getAppointment()),
                "Exception wasn`t thrown (that patient already has another appointment)");
        String expectedMessage = "Patient already has another appointment";
        assertTrue(exception.getMessage().contains(expectedMessage), "IllegalOperationException was thrown with wrong message");
    }

    @Test
    void verifyDeleteTest() {
        final long appointmentId = 1L;

        appointmentService.delete(appointmentId);

        verify(appointmentRepository, times(1)).delete(anyLong());
    }

    @Test
    void verifyIsExistPastByPatientIdAndDoctorIdTest() {
        final long doctorId = 1L;
        final long patientId = 1L;
        given(appointmentRepository.isExistPastByPatientIdAndDoctorId(anyLong(), anyLong())).willReturn(true);

        Boolean actualResponse = appointmentService.isExistPastByPatientIdAndDoctorId(patientId, doctorId);

        assertEquals(true, actualResponse, "boolean types are not equal");
        verify(appointmentRepository, times(1)).isExistPastByPatientIdAndDoctorId(anyLong(), anyLong());
    }

    @Test
    void verifyRetrieveAllByPatientIdAndCriteriaSuccessTest() {
        final long patientId = 1L;
        final var criteria = new SearchAppointmentCriteria();
        criteria.setStatus(AppointmentStatus.FUTURE);
        List<Appointment> expectedAppointments = getAppointmentList();
        given(appointmentRepository.findAllByPatientIdAndCriteria(anyLong(), any(SearchAppointmentCriteria.class))).willReturn(expectedAppointments);

        List<Appointment> actualAppointments = appointmentService.retrieveAllByPatientIdAndCriteria(patientId, criteria);

        assertEquals(expectedAppointments, actualAppointments);
        verify(appointmentRepository, times(1)).findAllByPatientIdAndCriteria(anyLong(), any(SearchAppointmentCriteria.class));
    }

    @Test
    void verifyRetrieveByIdSuccessTest() {
        final long appointmentId = 1L;
        Appointment expectedAppointment = getAppointment();
        given(appointmentRepository.findById(anyLong())).willReturn(Optional.of(expectedAppointment));

        Appointment actualAppointment = appointmentService.retrieveById(appointmentId);

        assertEquals(expectedAppointment, actualAppointment, "appointments are not equal");
        verify(appointmentRepository, times(1)).findById(Mockito.anyLong());
    }

    @Test
    void verifyRetrieveThrowsResourceDoesNotExistExceptionTest() {
        final long appointmentId = 1L;
        given(appointmentRepository.findById(anyLong())).willReturn(Optional.empty());

        assertThrows(ResourceDoesNotExistException.class, () -> appointmentService.retrieveById(appointmentId), "Exception wasn`t thrown");
        verify(appointmentRepository, times(1)).findById(Mockito.anyLong());
    }

    @Test
    void verifyRetrieveAllByDoctorIdAndCriteriaSuccessTest() {
        final long doctorId = 1L;
        final var criteria = new SearchAppointmentCriteria();
        criteria.setStatus(AppointmentStatus.FUTURE);
        List<Appointment> expectedAppointments = getAppointmentList();
        given(appointmentRepository.findAllByDoctorIdAndCriteria(anyLong(), any(SearchAppointmentCriteria.class))).willReturn(expectedAppointments);

        List<Appointment> actualAppointments = appointmentService.retrieveAllByDoctorIdAndCriteria(doctorId, criteria);

        assertEquals(expectedAppointments, actualAppointments);
        verify(appointmentRepository, times(1)).findAllByDoctorIdAndCriteria(anyLong(), any(SearchAppointmentCriteria.class));
    }

    public static List<SearchAppointmentCriteria> searchCriteriaData() {
        List<SearchAppointmentCriteria> criteria = new ArrayList<>();
        criteria.add(new SearchAppointmentCriteria(LocalDate.now().minusDays(1), AppointmentStatus.FUTURE));
        criteria.add(new SearchAppointmentCriteria(LocalDate.now().plusDays(1), AppointmentStatus.DONE));
        return criteria;
    }


    @ParameterizedTest
    @MethodSource("searchCriteriaData")
    void verifyRetrieveAllByPatientIdAndCriteriaThrowsIllegalOperationExceptionTest(SearchAppointmentCriteria criteria) {
        final long patientId = 1L;

        assertThrows(IllegalOperationException.class, () -> appointmentService.retrieveAllByPatientIdAndCriteria(patientId, criteria),
                "Exception wasn`t thrown") ;
        verify(appointmentRepository, never()).findAllByPatientIdAndCriteria(anyLong(), any(SearchAppointmentCriteria.class));
    }

    @ParameterizedTest
    @MethodSource("searchCriteriaData")
    void verifyRetrieveAllByDoctorIdAndCriteriaThrowsIllegalOperationExceptionTest(SearchAppointmentCriteria criteria) {
        final long doctorId = 1L;

        assertThrows(IllegalOperationException.class, () -> appointmentService.retrieveAllByDoctorIdAndCriteria(doctorId, criteria),
                "Exception wasn`t thrown") ;
        verify(appointmentRepository, never()).findAllByDoctorIdAndCriteria(anyLong(), any(SearchAppointmentCriteria.class));
    }


    private List<LocalTime> getSlots() {
        List<LocalTime> slots = new ArrayList<>();
        slots.add(LocalTime.of(8,0));
        slots.add(LocalTime.of(10,0));
        slots.add(LocalTime.of(11,0));
        slots.add(LocalTime.of(12,0));
        slots.add(LocalTime.of(13,0));
        slots.add(LocalTime.of(15,0));
        slots.add(LocalTime.of(16,0));
        return slots;
    }

    private Appointment getAppointment() {
        Appointment appointment = new Appointment();
        appointment.setId(1L);
        appointment.setStart(LocalDateTime.of(LocalDate.of(2028, 1, 4),
                            LocalTime.of(12, 0)));
        Doctor doctor = new Doctor();
        doctor.setId(1L);
        appointment.setDoctor(doctor);
        return appointment;
    }

    private Appointment getAppointment(int hour) {
        Appointment appointment = new Appointment();
        appointment.setId(1L);
        appointment.setStart(LocalDateTime.of(LocalDate.of(2028, 1, 4),
                LocalTime.of(hour, 0)));
        Doctor doctor = new Doctor();
        doctor.setId(1L);
        appointment.setDoctor(doctor);
        return appointment;
    }

    private List<Appointment> getAppointmentList() {
        List<Appointment> appointments = new ArrayList<>();
        appointments.add(getAppointment(11));
        appointments.add(getAppointment(12));
        appointments.add(getAppointment(13));
        return appointments;
    }

}