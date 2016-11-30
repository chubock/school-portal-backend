package com.avin.schoolportal.rest;

import com.avin.schoolportal.domain.Employee;
import com.avin.schoolportal.domain.User;
import com.avin.schoolportal.dto.EmployeeDTO;
import com.avin.schoolportal.dto.PersonDTO;
import com.avin.schoolportal.dto.UserDTO;
import com.avin.schoolportal.repository.EmployeeRepository;
import com.avin.schoolportal.repository.UserRepository;
import com.avin.schoolportal.service.ManagerService;
import com.avin.schoolportal.service.UserService;
import com.avin.schoolportal.specification.EmployeeSpecification;
import com.avin.schoolportal.validationgroups.EmployeeRegistration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Yubar on 11/3/2016.
 */

@RestController
@RequestMapping("/employees")
public class EmployeeRestService {
    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ManagerService managerService;

    @Autowired
    UserService userService;

    @PreAuthorize("hasAuthority('MANAGER')")
    @RequestMapping(method = RequestMethod.GET)
    public Page<EmployeeDTO> getEmployees(@RequestParam Map<String, String> params, Pageable pageable, Principal principal) {
        User user = userRepository.findByUsername(principal.getName());
        Page<Employee> employees = employeeRepository.findAll(new EmployeeSpecification(params, user.getSchool()), pageable);
        return employees.map(e -> {
            EmployeeDTO employeeDTO = new EmployeeDTO(e);
            employeeDTO.setUser(new UserDTO(e.getUser()));
            employeeDTO.getUser().setPerson(new PersonDTO(e.getUser().getPerson()));
            return employeeDTO;
        });
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public EmployeeDTO getUserEmployee(Principal principal) {
        User user = userRepository.findByUsername(principal.getName());
        Employee employee = employeeRepository.findByUser(user);
        EmployeeDTO employeeDTO = new EmployeeDTO(employee);
        employeeDTO.setUser(new UserDTO(employee.getUser()));
        employeeDTO.getUser().setPerson(new PersonDTO(employee.getUser().getPerson()));
        return employeeDTO;
    }

    @PreAuthorize("hasPermission(#id, 'Employee', 'READ')")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public EmployeeDTO getEmployee(@PathVariable long id) {
        Employee employee = employeeRepository.findOne(id);
        EmployeeDTO employeeDTO = new EmployeeDTO(employee);
        employeeDTO.setUser(new UserDTO(employee.getUser()));
        employeeDTO.getUser().setPerson(new PersonDTO(employee.getUser().getPerson()));
        employeeDTO.getUser().setRoles(new ArrayList<>(employee.getUser().getRoles()));
        return employeeDTO;
    }

    @PreAuthorize("hasAuthority('MANAGER')")
    @RequestMapping(method = RequestMethod.POST)
    public EmployeeDTO registerEmployee(@Validated(EmployeeRegistration.class) @RequestBody EmployeeDTO employeeDTO, Principal principal) {
        Employee employee = employeeDTO.convert();
        User user = userRepository.findByUsername(principal.getName());
        employee.setSchool(user.getSchool());
        employee = managerService.registerEmployee(employee);
        String newPassword = userService.resetPassword(employee.getUser().getUsername());
        userService.sendRegistrationEmail(employee.getUser(), newPassword);
        EmployeeDTO ret = new EmployeeDTO(employee);
        ret.setUser(new UserDTO(employee.getUser()));
        ret.getUser().setPerson(new PersonDTO(employee.getUser().getPerson()));
        return ret;
    }

    @PreAuthorize("hasPermission(#employeeDTO.id, 'Employee', 'UPDATE')")
    @RequestMapping(method = RequestMethod.PUT)
    public EmployeeDTO updateEmployee(@Validated(EmployeeRegistration.class) @RequestBody EmployeeDTO employeeDTO) {
        Employee employee = employeeDTO.convert();
        employee = managerService.updateEmployee(employee);
        EmployeeDTO ret = new EmployeeDTO(employee);
        ret.setUser(new UserDTO(employee.getUser()));
        ret.getUser().setPerson(new PersonDTO(employee.getUser().getPerson()));
        return ret;
    }

    @PreAuthorize("hasPermission(#id, 'Employee', 'DELETE')")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteEmployee(@PathVariable long id) {
        Employee employee = employeeRepository.findOne(id);
        managerService.deleteEmployee(employee);
    }
}
