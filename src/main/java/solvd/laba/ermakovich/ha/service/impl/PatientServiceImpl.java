package solvd.laba.ermakovich.ha.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import solvd.laba.ermakovich.ha.domain.Patient;
import solvd.laba.ermakovich.ha.domain.UserInfo;
import solvd.laba.ermakovich.ha.repository.PatientRepository;
import solvd.laba.ermakovich.ha.repository.mapper.UserInfoMapper;
import solvd.laba.ermakovich.ha.service.AddressService;
import solvd.laba.ermakovich.ha.service.PatientCardService;
import solvd.laba.ermakovich.ha.service.PatientService;
import solvd.laba.ermakovich.ha.service.UserInfoService;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {

    private final AddressService addressService;
    private final UserInfoService userInfoService;
    private final PatientCardService patientCardService;
    private final PatientRepository patientRepository;
    private final UserInfoMapper userInfoMapper;

    @Override
    @Transactional
    public Patient save(Patient patient) {
        UserInfo userInfo = userInfoMapper.mapToUserInfo(patient);
        userInfoService.save(userInfo);
        addressService.find(patient.getAddress())
                .ifPresentOrElse(
                        patient::setAddress,
                        () -> addressService.save(patient.getAddress()));
        patient.setId(userInfo.getId());
        patientRepository.save(patient);
        patientCardService.saveByPatientId(patient.getId());
        return patient;
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsById(long id) {
        return patientRepository.existsById(id);
    }

}
