package solvd.laba.ermakovich.ha.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import solvd.laba.ermakovich.ha.domain.SearchDoctorCriteria;
import solvd.laba.ermakovich.ha.domain.UserInfo;
import solvd.laba.ermakovich.ha.domain.UserRole;
import solvd.laba.ermakovich.ha.domain.doctor.Doctor;
import solvd.laba.ermakovich.ha.domain.exception.IllegalOperationException;
import solvd.laba.ermakovich.ha.repository.DoctorRepository;
import solvd.laba.ermakovich.ha.service.AccountClient;
import solvd.laba.ermakovich.ha.service.DoctorService;
import solvd.laba.ermakovich.ha.service.UserInfoService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository doctorRepository;
    private final UserInfoService userInfoService;
    private final AccountClient accountClient;

    @Override
    @Transactional
    public Doctor create(Doctor doctor) {
        if (!UserRole.DOCTOR.equals(doctor.getRole())) {
            throw new IllegalOperationException("Doctor has wrong role");
        }
        UserInfo info = userInfoService.create(doctor);
        accountClient.create(info.getExternalId());
        doctor.setId(doctor.getId());
        doctorRepository.save(doctor);
        return doctor;
    }


    @Override
    @Transactional(readOnly = true)
    public boolean isExistById(long id) {
        return doctorRepository.isExistById(id);
    }

    @Override
    public List<Doctor> retrieveAllBySearchCriteria(SearchDoctorCriteria searchDoctorCriteria) {
        return doctorRepository.findAllBySearchCriteria(searchDoctorCriteria);
    }

}
