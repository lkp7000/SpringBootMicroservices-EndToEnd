package com.gigatorb.user.service;

import com.gigatorb.user.entity.User;
import com.gigatorb.user.model.Department;
import com.gigatorb.user.model.ResponseTemplateVO;
import com.gigatorb.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestTemplate restTemplate;

    public User saveUser(User user) {
        log.info("Inside saveUser method of UserService");
        return userRepository.save(user);
    }

    public ResponseTemplateVO getUserWithDepartment(Long userId) {
        log.info("Inside getUserWithDepartment method of UserService");
        ResponseTemplateVO vo = new ResponseTemplateVO();
        User user = userRepository.findByUserId(userId);
        vo.setUser(user);
        Department department = restTemplate.getForObject(
                "http://DEPARTMENT-SERVICE/departments/" + user.getDepartmentId(), Department.class);
        vo.setDepartment(department);
        return vo;
    }
}
