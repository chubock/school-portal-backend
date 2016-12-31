package com.avin.schoolportal.rest;

import com.avin.schoolportal.domain.*;
import com.avin.schoolportal.dto.MancipleDTO;
import com.avin.schoolportal.dto.PersonDTO;
import com.avin.schoolportal.dto.StudentDTO;
import com.avin.schoolportal.dto.ViolationDTO;
import com.avin.schoolportal.repository.MancipleRepository;
import com.avin.schoolportal.repository.ViolationRepository;
import com.avin.schoolportal.service.MancipleService;
import com.avin.schoolportal.specification.ViolationSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Map;

/**
 * Created by Yubar on 12/4/2016.
 */

@RestController
@RequestMapping("/violations")
public class ViolationRestService {

    @Autowired
    ViolationRepository violationRepository;

    @Autowired
    MancipleRepository mancipleRepository;

    @Autowired
    MancipleService mancipleService;

    @Value("${dateShortFormat}")
    String dateShortFormat;

    @PreAuthorize("hasAuthority('MANCIPLE')")
    @RequestMapping(method = RequestMethod.GET)
    public Page<ViolationDTO> getViolations(@RequestParam Map<String, String> params, Pageable pageable, Principal principal) {
        Manciple manciple = mancipleRepository.findByUsername(principal.getName());
        return violationRepository.findAll(new ViolationSpecification(params, manciple, dateShortFormat), pageable).map(violation -> {
            ViolationDTO violationDTO = new ViolationDTO(violation);
            violationDTO.setManciple(new MancipleDTO(violation.getManciple()));
            return violationDTO;
        });
    }

    @PreAuthorize("hasPermission(#id, 'Violation', 'READ')")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ViolationDTO getViolation(@PathVariable long id) {
        Violation violation = violationRepository.findOneWithStudents(id);
        ViolationDTO violationDTO = new ViolationDTO(violation);
        violation.getStudents().forEach(student -> {
            StudentDTO studentDTO = new StudentDTO(student);
            violationDTO.getStudents().add(studentDTO);
        });
        return violationDTO;
    }

    @PreAuthorize("hasAuthority('MANCIPLE')")
    @RequestMapping(method = RequestMethod.POST)
    public ViolationDTO registerViolation(@RequestBody ViolationDTO violationDTO, Principal principal) {
        Manciple manciple = mancipleRepository.findByUsername(principal.getName());
        Violation violation = violationDTO.convert();
        violation.setManciple(manciple);
        violation.setSchool(manciple.getSchool());
        violation = mancipleService.registerViolation(violation);
        violationDTO = new ViolationDTO(violation);
        violationDTO.setManciple(new MancipleDTO(manciple));
        return violationDTO;
    }

    @PreAuthorize("hasPermission(#violationDTO.id, 'Violation', 'UPDATE')")
    @RequestMapping(method = RequestMethod.PUT)
    public ViolationDTO updateViolation(@RequestBody ViolationDTO violationDTO) {
        Violation violation = violationDTO.convert();
        mancipleService.updateViolation(violation);
        violation = violationRepository.findOneWithStudents(violation.getId());
        ViolationDTO ret = new ViolationDTO(violation);
        return ret;
    }

    @PreAuthorize("hasPermission(#id, 'Violation', 'DELETE')")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteViolation(@PathVariable long id) {
        Violation violation = violationRepository.findOne(id);
        mancipleService.removeViolation(violation);
    }



}
