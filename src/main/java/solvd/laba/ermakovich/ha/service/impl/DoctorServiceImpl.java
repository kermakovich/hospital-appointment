package solvd.laba.ermakovich.ha.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import solvd.laba.ermakovich.ha.domain.SearchCriteria;
import solvd.laba.ermakovich.ha.domain.UserInfo;
import solvd.laba.ermakovich.ha.domain.doctor.Doctor;
import solvd.laba.ermakovich.ha.repository.DoctorRepository;
import solvd.laba.ermakovich.ha.repository.mapper.UserInfoMapper;
import solvd.laba.ermakovich.ha.service.DoctorService;
import solvd.laba.ermakovich.ha.service.UserInfoService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository doctorRepository;
    private final UserInfoService userInfoService;
    private final UserInfoMapper userInfoMapper;

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
    @Transactional(readOnly = true)
    public boolean existsById(long id) {
        return doctorRepository.existsById(id);
    }

    @Override
    public List<Doctor> getAllBySearchCriteria(SearchCriteria searchCriteria) {
        return doctorRepository.getAllBySearchCriteria(searchCriteria);
    }

}
