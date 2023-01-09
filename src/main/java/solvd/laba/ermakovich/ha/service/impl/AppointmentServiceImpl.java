package solvd.laba.ermakovich.ha.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import solvd.laba.ermakovich.ha.domain.Appointment;
import solvd.laba.ermakovich.ha.domain.exception.IllegalOperationException;
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
    public Appointment save(long patientId, Appointment appointment) {
        if (!openingHours.isWithinOpenHours(appointment.getStart().toLocalTime())) {
            throw new IllegalOperationException("Hospital is closed at this time: "
                                                    + appointment.getStart().toString());
        }
        if (appointmentRepository.existsByDoctorIdAndTime(appointment)) {
            throw new IllegalOperationException( "Appointment is taken (date : "+appointment.getStart()+" )");
        }
        appointmentRepository.save(patientId, appointment);
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
        appointmentRepository.delete(appointmentId);
    }

//    @Override
//    public List<Appointment> getAllByPatientIdAndDoctorId(long patientId, long doctorId) {
//        return appointmentRepository.getAllByPatientIdAndDoctorId(patientId, doctorId);
//    }

    @Override
    public List<Appointment> getAllFutureByDoctorId(long doctorId) {
        return appointmentRepository.getAllFutureByDoctorId(doctorId);
    }

    @Override
    public boolean existsPastByPatientIdAndDoctorId(long patientId, long doctorId) {
        return appointmentRepository.existsPastByPatientIdAndDoctorId(patientId, doctorId);
    }
}
