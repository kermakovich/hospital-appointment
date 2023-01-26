package solvd.laba.ermakovich.ha.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import solvd.laba.ermakovich.ha.domain.Appointment;
import solvd.laba.ermakovich.ha.domain.AppointmentStatus;
import solvd.laba.ermakovich.ha.domain.SearchAppointmentCriteria;
import solvd.laba.ermakovich.ha.domain.exception.IllegalOperationException;
import solvd.laba.ermakovich.ha.domain.exception.ResourceDoesNotExistException;
import solvd.laba.ermakovich.ha.domain.hospital.OpeningHours;
import solvd.laba.ermakovich.ha.repository.AppointmentRepository;
import solvd.laba.ermakovich.ha.service.AppointmentService;
import solvd.laba.ermakovich.ha.service.DoctorService;
import solvd.laba.ermakovich.ha.service.PatientService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final OpeningHours openingHours;
    private final DoctorService doctorService;
    private final PatientService patientService;

    @Override
    @Transactional(readOnly = true)
    public List<LocalTime> getTimeSlotsByDoctorIdAndDate(long id, LocalDate date) {
        return appointmentRepository.findAppointmentsTimeByDoctorIdAndDate(id, date);
    }

    @Override
    @Transactional
    public Appointment create(long patientId, Appointment appointment) {
        if (!doctorService.isExistById(appointment.getDoctor().getId())) {
            throw new ResourceDoesNotExistException("doctor with id: " + appointment.getDoctor().getId() + "doesn`t exist");
        }
        if (!patientService.isExistById(patientId)) {
            throw new ResourceDoesNotExistException("patient with id: " + patientId+ "doesn`t exist");
        }
        if (!openingHours.isWithinOpenHours(appointment.getStart().toLocalTime())) {
            throw new IllegalOperationException("Hospital is closed at this time: "
                                                    + appointment.getStart().toString());
        }
        if (appointmentRepository.isExistByDoctorIdAndTime(appointment)) {
            throw new IllegalOperationException( "Appointment is taken (date : " + appointment.getStart() + " )");
        }
        if (appointmentRepository.isExistByPatientIdAndTime(patientId, appointment)) {
            throw new IllegalOperationException( "Patient already has another appointment ( date : " + appointment.getStart() + " )");
        }

        appointmentRepository.save(patientId, appointment);
        return appointment;
    }

    @Override
    @Transactional
    public void delete(long appointmentId) {
        appointmentRepository.delete(appointmentId);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isExistPastByPatientIdAndDoctorId(long patientId, long doctorId) {
        return appointmentRepository.isExistPastByPatientIdAndDoctorId(patientId, doctorId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Appointment> retrieveAllByPatientIdAndCriteria(long patientId, SearchAppointmentCriteria criteria) {
        if (validSearchCriteria(criteria)) {
            throw new IllegalOperationException("conflict data with status");
        }
        return appointmentRepository.findAllByPatientIdAndCriteria(patientId, criteria);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Appointment> retrieveAllByDoctorIdAndCriteria(long doctorId, SearchAppointmentCriteria criteria) {
        if (validSearchCriteria(criteria)) {
            throw new IllegalOperationException("conflict data with status");
        }
        return appointmentRepository.findAllByDoctorIdAndCriteria(doctorId, criteria);
    }

    @Override
    public Appointment retrieveById(Long appId) {
        return appointmentRepository.findById(appId)
                .orElseThrow(() -> new ResourceDoesNotExistException("appointment with id " + appId + "doesn`t exist"));
    }

    private boolean validSearchCriteria(SearchAppointmentCriteria criteria) {
       return criteria.getDate() != null && criteria.getStatus() != null
                && (criteria.getDate().isBefore(LocalDate.now()) && criteria.getStatus() == AppointmentStatus.FUTURE
                || criteria.getDate().isAfter(LocalDate.now()) && criteria.getStatus() == AppointmentStatus.DONE);
    }

}
