package solvd.laba.ermakovich.ha.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import solvd.laba.ermakovich.ha.domain.Appointment;
import solvd.laba.ermakovich.ha.domain.AppointmentStatus;
import solvd.laba.ermakovich.ha.domain.PatientCard;
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
    void verifyCreateAppointmentSuccessfulTest() {
        final long patientId = 1L;
        final long appointmentId = 1L;
        Appointment expectedAppointment = getAppointment();
        expectedAppointment.setId(appointmentId);
        given(doctorService.isExistById(anyLong())).willReturn(true);
        given(patientService.isExistById(anyLong())).willReturn(true);
        given(openingHours.isWithinOpenHours(any(LocalTime.class))).willReturn(true);
        given(appointmentRepository.isExistByDoctorIdAndTime(any(Appointment.class))).willReturn(false);
        given(appointmentRepository.isExistByPatientIdAndTime(anyLong(), any(Appointment.class))).willReturn(false);
        doAnswer((invocation)-> {
            Appointment appointment = invocation.getArgument(1);
            appointment.setId(appointmentId);
            return null;
        }).when(appointmentRepository).save(anyLong(), any(Appointment.class));

        Appointment actualAppointment = appointmentService.create(patientId, getAppointment());

        assertEquals(expectedAppointment, actualAppointment, "appointments are not equal");
        verify(appointmentRepository, times(1)).save(anyLong(), any(Appointment.class));
    }

    public static Object[][] getAppointmentData() {
        return new Object[][] {
                {new Boolean[] {false, false, false, false, false}, ResourceDoesNotExistException.class, "doctor with id"},
                {new Boolean[] {true, false, false, false, false}, ResourceDoesNotExistException.class, "patient with id"},
                {new Boolean[] {true, true, false, false, false}, IllegalOperationException.class, "Hospital is closed at this time"},
                {new Boolean[] {true, true, true, true, false}, IllegalOperationException.class, "Appointment is taken"},
                {new Boolean[] {true, true, true, false, true}, IllegalOperationException.class, "Patient already has another appointment"}
        };
    }

    @ParameterizedTest
    @MethodSource("getAppointmentData")
    @MockitoSettings(strictness = Strictness.LENIENT)
    void verifyCreateThrowsPatientAlreadyHasAnotherAppointmentTest(Boolean[] booleans, Class<Exception> exceptionClass, String message) {
        final long patientId = 1L;
        given(doctorService.isExistById(anyLong())).willReturn(booleans[0]);
        given(patientService.isExistById(anyLong())).willReturn(booleans[1]);
        given(openingHours.isWithinOpenHours(any(LocalTime.class))).willReturn(booleans[2]);
        given(appointmentRepository.isExistByDoctorIdAndTime(any(Appointment.class))).willReturn(booleans[3]);
        given(appointmentRepository.isExistByPatientIdAndTime(anyLong(), any(Appointment.class))).willReturn(booleans[4]);

        var exception = assertThrows(exceptionClass, () -> appointmentService.create(patientId, getAppointment()),
                "Exception wasn`t thrown");

        assertTrue(exception.getMessage().contains(message), "exception was thrown with wrong message");
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
    void verifyRetrieveAllByPatientIdAndCriteriaSuccessfulTest() {
        final long patientId = 1L;
        final var criteria = new SearchAppointmentCriteria();
        criteria.setStatus(AppointmentStatus.FUTURE);
        List<Appointment> expectedAppointments = getAppointmentList();
        given(appointmentRepository.findAllByPatientIdAndCriteria(anyLong(), any(SearchAppointmentCriteria.class))).willReturn(expectedAppointments);

        List<Appointment> actualAppointments = appointmentService.retrieveAllByPatientIdAndCriteria(patientId, criteria);

        assertEquals(expectedAppointments, actualAppointments, "lists are not equal");
        verify(appointmentRepository, times(1)).findAllByPatientIdAndCriteria(anyLong(), any(SearchAppointmentCriteria.class));
    }

    @Test
    void verifyRetrieveByIdSuccessfulTest() {
        final long appointmentId = 1L;
        Appointment expectedAppointment = getAppointment();
        given(appointmentRepository.findById(anyLong())).willReturn(Optional.of(expectedAppointment));

        Appointment actualAppointment = appointmentService.retrieveById(appointmentId);

        assertEquals(expectedAppointment, actualAppointment, "appointments are not equal");
        verify(appointmentRepository, times(1)).findById(Mockito.anyLong());
    }

    @Test
    void verifyRetrieveByIdThrowsResourceDoesNotExistExceptionTest() {
        final long appointmentId = 1L;
        given(appointmentRepository.findById(anyLong())).willReturn(Optional.empty());

        assertThrows(ResourceDoesNotExistException.class, () -> appointmentService.retrieveById(appointmentId), "Exception wasn`t thrown");
        verify(appointmentRepository, times(1)).findById(Mockito.anyLong());
    }

    @Test
    void verifyRetrieveAllByDoctorIdAndCriteriaSuccessfulTest() {
        final long doctorId = 1L;
        final var criteria = new SearchAppointmentCriteria();
        criteria.setStatus(AppointmentStatus.FUTURE);
        List<Appointment> expectedAppointments = getAppointmentList();
        given(appointmentRepository.findAllByDoctorIdAndCriteria(anyLong(), any(SearchAppointmentCriteria.class))).willReturn(expectedAppointments);

        List<Appointment> actualAppointments = appointmentService.retrieveAllByDoctorIdAndCriteria(doctorId, criteria);

        assertEquals(expectedAppointments, actualAppointments, "lists are not equal");
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
        appointment.setStart(LocalDateTime.of(LocalDate.of(2028, 1, 4),
                            LocalTime.of(12, 0)));
        Doctor doctor = new Doctor();
        doctor.setId(1L);
        appointment.setDoctor(doctor);
        PatientCard patientCard = new PatientCard();
        patientCard.setId(1L);
        appointment.setPatientCard(patientCard);
        return appointment;
    }

    private Appointment getAppointment(int hour) {
        Appointment appointment = new Appointment();
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