package solvd.laba.ermakovich.ha.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import solvd.laba.ermakovich.ha.domain.Address;
import solvd.laba.ermakovich.ha.domain.Patient;
import solvd.laba.ermakovich.ha.domain.UserInfo;
import solvd.laba.ermakovich.ha.domain.UserRole;
import solvd.laba.ermakovich.ha.domain.exception.IllegalOperationException;
import solvd.laba.ermakovich.ha.repository.PatientRepository;
import solvd.laba.ermakovich.ha.repository.jdbc.mapper.UserInfoMapper;
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
    public Patient create(Patient patient) {
        UserInfo userInfo = userInfoMapper.mapToUserInfo(patient);
        if (!userInfo.getRole().equals(UserRole.PATIENT)) {
            throw new IllegalOperationException("Patient has wrong role");
        }
        userInfoService.create(userInfo);
        Address address = addressService.create(patient.getAddress());
        patient.setAddress(address);
        patient.setId(userInfo.getId());
        patientRepository.save(patient);
        patientCardService.createByPatientId(patient.getId());
        return patient;
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isExistById(long id) {
        return patientRepository.isExistById(id);
    }

}
