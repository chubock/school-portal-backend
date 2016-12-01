package com.avin.schoolportal.rest;

import com.avin.schoolportal.domain.Person;
import com.avin.schoolportal.domain.School;
import com.avin.schoolportal.domain.User;
import com.avin.schoolportal.dto.EmployeeDTO;
import com.avin.schoolportal.dto.PersonDTO;
import com.avin.schoolportal.dto.SchoolDTO;
import com.avin.schoolportal.dto.UserDTO;
import com.avin.schoolportal.repository.PersonRepository;
import com.avin.schoolportal.repository.UserRepository;
import com.avin.schoolportal.service.RegistrationService;
import com.avin.schoolportal.validationgroups.SchoolRegistration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yubar on 10/28/2016.
 */

@RestController
public class RegistrationRestService {

    @Autowired
    RegistrationService registrationService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PersonRepository personRepository;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public SchoolDTO register(@Validated(SchoolRegistration.class) @RequestBody SchoolDTO schoolDTO) {
        School school = registrationService.register(schoolDTO.convert());
        SchoolDTO ret = new SchoolDTO(school);
        return ret;
    }

    @RequestMapping(value = "/register/checkUsername", method = RequestMethod.GET)
    public boolean isUsernameValid(@RequestParam String username) {
        User user = userRepository.findByUsername(username);
        return user == null;
    }

    @RequestMapping(value = "/register/queryNationalID", method = RequestMethod.GET)
    public List<PersonDTO> queryNationalId(@RequestParam String key) {
        List<Person> result = personRepository.findByNationalIdStartingWith(key);
        List<PersonDTO> ret = new ArrayList<>();
        result.forEach(person -> {
            ret.add(new PersonDTO(person));
        });
        return ret;
    }
}
