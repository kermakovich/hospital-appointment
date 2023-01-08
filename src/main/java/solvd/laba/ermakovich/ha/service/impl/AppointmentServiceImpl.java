package solvd.laba.ermakovich.ha.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import solvd.laba.ermakovich.ha.domain.Appointment;
import solvd.laba.ermakovich.ha.domain.exception.AppointmentIsBusyException;
import solvd.laba.ermakovich.ha.domain.exception.AppointmentNotFoundException;
import solvd.laba.ermakovich.ha.domain.exception.WrongAppointmentTimeException;
import solvd.laba.ermakovich.ha.domain.hospital.OpeningHours;
import solvd.laba.ermakovich.ha.repository.AppointmentRepository;
import solvd.laba.ermakovich.ha.service.AppointmentService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final OpeningHours openingHours;

    @Override
    public List<LocalTime> getTimeSlotsByDoctorIdAndDate(long id, LocalDate date) {
        return appointmentRepository.getTimeSlotsByDoctorIdAndDate(id, date);
    }

    @Override
    @Transactional
    public Appointment save(Appointment appointment) {
        if (!openingHours.isWithinOpenHours(appointment.getStart().toLocalTime())) {
            throw new WrongAppointmentTimeException(appointment.getStart());
        }
        if (appointmentRepository.existsByDoctorIdAndTime(appointment)) {
            throw new AppointmentIsBusyException(appointment);
        }
        appointmentRepository.save(appointment);
        return appointment;
    }

    /**
     * @param patientId patient id.
     * @return all future appointments for particular patient.
     */
    @Override
    public List<Appointment> getAllFutureByPatientId(long patientId) {
        return appointmentRepository.getAllFutureByPatientId(patientId);
    }

    @Override
    public void delete(long appointmentId) {
        if (!appointmentRepository.existsById(appointmentId)) {
            throw new AppointmentNotFoundException(appointmentId);
        }
        appointmentRepository.delete(appointmentId);
    }

    @Override
    public List<Appointment> getAllByPatientIdAndDoctorId(long patientId, long doctorId) {
        return appointmentRepository.getAllByPatientIdAndDoctorId(patientId, doctorId);
    }
}
