package com.avin.schoolportal.rest;

import com.avin.schoolportal.domain.File;
import com.avin.schoolportal.domain.Manager;
import com.avin.schoolportal.domain.Manciple;
import com.avin.schoolportal.domain.SchoolUser;
import com.avin.schoolportal.dto.FileDTO;
import com.avin.schoolportal.dto.MancipleDTO;
import com.avin.schoolportal.dto.PersonDTO;
import com.avin.schoolportal.repository.ManagerRepository;
import com.avin.schoolportal.repository.MancipleRepository;
import com.avin.schoolportal.repository.SchoolUserRepository;
import com.avin.schoolportal.service.ManagerService;
import com.avin.schoolportal.service.SchoolUserService;
import com.avin.schoolportal.service.UserService;
import com.avin.schoolportal.specification.MancipleSpecification;
import com.avin.schoolportal.validationgroups.EmployeeRegistration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.util.Map;

/**
 * Created by Yubar on 11/3/2016.
 */

@RestController
@RequestMapping("/manciples")
public class MancipleRestService {

    @Autowired
    MancipleRepository mancipleRepository;

    @Autowired
    SchoolUserRepository schoolUserRepository;

    @Autowired
    ManagerService managerService;

    @Autowired
    UserService userService;

    @Autowired
    SchoolUserService schoolUserService;

    @Autowired
    FileRenderer fileRenderer;

    @Autowired
    ManagerRepository managerRepository;

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(method = RequestMethod.GET)
    public Page<MancipleDTO> getManciples(@RequestParam Map<String, String> params, Pageable pageable, Principal principal) {
        SchoolUser user = schoolUserRepository.findByUsername(principal.getName());
        Page<Manciple> manciples = mancipleRepository.findAll(new MancipleSpecification(params, user.getSchool()), pageable);
        return manciples.map(e -> {
            MancipleDTO mancipleDTO = new MancipleDTO(e);
            return mancipleDTO;
        });
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public MancipleDTO getManciple(Principal principal) {
        Manciple manciple = mancipleRepository.findByUsername(principal.getName());
        MancipleDTO mancipleDTO = new MancipleDTO(manciple);
        return mancipleDTO;
    }

    @PreAuthorize("hasPermission(#id, 'Manciple', 'READ')")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public MancipleDTO getManciple(@PathVariable long id) {
        Manciple manciple = mancipleRepository.findOne(id);
        MancipleDTO mancipleDTO = new MancipleDTO(manciple);
        return mancipleDTO;
    }

    @PreAuthorize("hasAuthority('MANAGER')")
    @RequestMapping(method = RequestMethod.POST)
    public MancipleDTO registerManciple(@Validated(EmployeeRegistration.class) @RequestBody MancipleDTO mancipleDTO, Principal principal) {
        Manciple manciple = mancipleDTO.convert();
        SchoolUser user = schoolUserRepository.findByUsername(principal.getName());
        manciple.setSchool(user.getSchool());
        manciple = (Manciple) managerService.registerEmployee(manciple);
        String newPassword = userService.resetPassword(manciple.getUsername());
        schoolUserService.sendRegistrationEmail(manciple, newPassword);
        MancipleDTO ret = new MancipleDTO(manciple);
        return ret;
    }

    @PreAuthorize("hasPermission(#mancipleDTO.id, 'Manciple', 'UPDATE')")
    @RequestMapping(method = RequestMethod.PUT)
    public MancipleDTO updateManciple(@Validated(EmployeeRegistration.class) @RequestBody MancipleDTO mancipleDTO) {
        Manciple manciple = mancipleDTO.convert();
        manciple = (Manciple) managerService.updateEmployee(manciple);
        MancipleDTO ret = new MancipleDTO(manciple);
        return ret;
    }

    @PreAuthorize("hasPermission(#id, 'Manciple', 'DELETE')")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteManciple(@PathVariable long id) {
        Manciple manciple = mancipleRepository.findOne(id);
        managerService.deleteEmployee(manciple);
    }

    @PreAuthorize("hasPermission(#id, 'Manciple', 'READ')")
    @RequestMapping(value = {"/{id}/pictureFile", "/{id}/pictureFile/*"}, method = RequestMethod.GET)
    public void getPictureFile(@PathVariable long id, HttpServletResponse response) throws IOException {
        Manciple manciple = mancipleRepository.findOne(id);
        fileRenderer.renderFile(manciple == null ? null : manciple.getPictureFile(), response);
    }

    @PreAuthorize("hasPermission(#id, 'Manciple', 'UPDATE')")
    @RequestMapping(value = {"/{id}/pictureFile"}, method = RequestMethod.POST)
    public FileDTO uploadPictureFile(MultipartHttpServletRequest request, @PathVariable long id, Principal principal) throws IOException {
        Manager manager = managerRepository.findByUsername(principal.getName());
        Manciple manciple = mancipleRepository.getOne(id);
        MultipartFile file = request.getFile("file");
        File f = new File(file);
        f.setSchool(manager.getSchool());
        manciple.setPictureFile(f);
        schoolUserService.updateSchoolUserPictureFile(manciple);
        return new FileDTO(f);
    }
}
