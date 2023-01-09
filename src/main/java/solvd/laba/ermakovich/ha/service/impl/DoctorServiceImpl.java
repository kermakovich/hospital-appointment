package solvd.laba.ermakovich.ha.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import solvd.laba.ermakovich.ha.domain.UserInfo;
import solvd.laba.ermakovich.ha.domain.doctor.AvailibleSlots;
import solvd.laba.ermakovich.ha.domain.doctor.Doctor;
import solvd.laba.ermakovich.ha.domain.doctor.Specialization;
import solvd.laba.ermakovich.ha.domain.exception.ResourceNotFoundException;
import solvd.laba.ermakovich.ha.domain.hospital.Department;
import solvd.laba.ermakovich.ha.domain.hospital.OpeningHours;
import solvd.laba.ermakovich.ha.repository.mapper.UserInfoMapper;
import solvd.laba.ermakovich.ha.repository.DoctorRepository;
import solvd.laba.ermakovich.ha.service.AppointmentService;
import solvd.laba.ermakovich.ha.service.DoctorService;
import solvd.laba.ermakovich.ha.service.UserInfoService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository doctorRepository;
    private final UserInfoService userInfoService;
    private final AppointmentService appointmentService;
    private final UserInfoMapper userInfoMapper;
    private final OpeningHours openingHours;


    @Override
    @Transactional
    public Doctor save(Doctor doctor) {
        UserInfo userInfo = userInfoMapper.mapToUserInfo(doctor);
        userInfoService.save(userInfo);
        doctor.setId(userInfo.getId());
        doctorRepository.save(doctor);
        return doctor;
    }

    @Override
    public List<Doctor> getAllByDepartmentAndSpecialization(Department department, Specialization specialization) {
        return doctorRepository.getAllByDepartmentAndSpecialization(department, specialization);
    }

    @Override
    public AvailibleSlots getSchedule(long id, LocalDate date) {
        if (!existsById(id)) {
            throw new ResourceNotFoundException(entityName, id);
        }
        int steps = (int) openingHours.start.until(openingHours.finish, ChronoUnit.HOURS);
        List<LocalTime> starts = new java.util.ArrayList<>(IntStream.rangeClosed(0, steps - 1)
                .mapToObj(n -> openingHours.start
                        .plus(openingHours.minutesRange * n, ChronoUnit.MINUTES))
                .toList());
        List<LocalTime> timeList = appointmentService.getTimeSlotsByDoctorIdAndDate(id, date);
        starts.removeAll(timeList);
        return new AvailibleSlots(id, date, starts);
    }

    @Override
    public boolean existsById(long id) {
        return doctorRepository.existsById(id);
    }

}
