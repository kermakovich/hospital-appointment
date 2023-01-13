package solvd.laba.ermakovich.ha.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import solvd.laba.ermakovich.ha.domain.Appointment;
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
        return appointmentRepository.getTimeSlotsByDoctorIdAndDate(id, date);
    }

    @Override
    @Transactional
    public Appointment save(long patientId, Appointment appointment) {
        if (!doctorService.isExistById(appointment.getDoctor().getId())) {
            throw new ResourceDoesNotExistException("doctor", appointment.getDoctor().getId());
        }
        if (!patientService.isExistById(patientId)) {
            throw new ResourceDoesNotExistException("patient", patientId);
        }
        if (!openingHours.isWithinOpenHours(appointment.getStart().toLocalTime())) {
            throw new IllegalOperationException("Hospital is closed at this time: "
                                                    + appointment.getStart().toString());
        }
        if (appointmentRepository.existsByDoctorIdAndTime(appointment)) {
            throw new IllegalOperationException( "Appointment is taken (date : " + appointment.getStart() + " )");
        }
        if (appointmentRepository.existsByPatientIdAndTime(patientId, appointment)) {
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
    public List<Appointment> getAllByDoctorIdAndDate(long doctorId, LocalDate date) {
        return appointmentRepository.getAllByDoctorIdAndDate(doctorId, date);
    }

    @Override
    public List<Appointment> getAllFutureByDoctorId(long doctorId) {
        return getAllByDoctorIdAndDate(doctorId, null);
    }

    @Override
    public List<Appointment> getAllFutureByPatientId(long patientId) {
        return getAllByPatientIdAndDate(patientId, null);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isExistPastByPatientIdAndDoctorId(long patientId, long doctorId) {
        return appointmentRepository.existsPastByPatientIdAndDoctorId(patientId, doctorId);
    }

    @Override
    public List<Appointment> getAllByPatientIdAndDate(long patientId, LocalDate date) {
        return appointmentRepository.getAllByPatientIdAndDate(patientId, date);
    }

}
