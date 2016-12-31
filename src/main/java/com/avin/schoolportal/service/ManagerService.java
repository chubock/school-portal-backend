package com.avin.schoolportal.service;

import com.avin.schoolportal.domain.*;
import com.avin.schoolportal.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.web.configurers.ExceptionHandlingConfigurer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.List;

/**
 * Created by Yubar on 10/28/2016.
 */

@Service
@Transactional
public class ManagerService {

    @Autowired
    SchoolRepository schoolRepository;

    @Autowired
    PersonRepository personRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    SchoolUserService schoolUserService;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    FileRepository fileRepository;

    @PreAuthorize("hasPermission(#school, 'UPDATE')")
    public void updateSchoolCourses(School school, List<Course> courses) {
        school = schoolRepository.findOne(school.getId());
        school.getCourses().clear();
        school.getCourses().addAll(courses);
    }

    @PreAuthorize("hasPermission(#employee, 'CREATE')")
    public Employee registerEmployee(Employee employee) {
        String lastUsername = userRepository.findLastUsernameLike(employee.getSchool().getCode() + employee.getUsernamePrefix() + "%");
        if (lastUsername == null)
            lastUsername = employee.getSchool().getCode() + employee.getUsernamePrefix() + "000";
        employee.setUsername(new BigDecimal(lastUsername).add(BigDecimal.ONE).toString());
        employee.setSchool(employee.getSchool());
        return (Employee) schoolUserService.registerSchoolUser(employee);
    }

    @PreAuthorize("hasPermission(#employee, 'UPDATE')")
    public Employee updateEmployee(Employee employee) {
        return (Employee) schoolUserService.updateSchoolUser(employee);
    }

    @PreAuthorize("hasPermission(#employee, 'DELETE')")
    public void deleteEmployee(Employee employee) {
        schoolUserService.deleteUser(employee);
    }

    public void fetchBooksImages() {
        Date date = new Date();
        bookRepository.findAll().stream().forEach(book -> {
            if (book.getCover() == null) {
                String urlAddress = null;
                InputStream inputStream = null;
                ByteArrayOutputStream outputStream = null;
                HttpURLConnection connection = null;
                try {
                    StringBuilder urlBuilder = new StringBuilder("http://www.chap.sch.ir/sites/default/files/styles/image_node_book/public/book_image/95-96/C");
                    urlBuilder.append(book.getCode().replace("/", "-"));
                    urlBuilder.append(".jpg");
                    urlAddress = urlBuilder.toString();
                    URL url = new URL(urlAddress);
                    inputStream = url.openStream();
                    outputStream = new ByteArrayOutputStream();
                    byte[] buffer = new byte[4096];
                    int length = 0;
                    while ((length = inputStream.read(buffer)) > 0) {
                        outputStream.write(buffer, 0, length);
                    }
                    File file = new File();
                    file.setContent(outputStream.toByteArray());
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("HEAD");
                    file.setContentType(connection.getContentType());
                    file.setSize(connection.getContentLength());
                    file.setName(date.getTime() + "-C" + book.getCode().replace("/", "-"));
                    book.setCover(fileRepository.save(file));
                    bookRepository.save(book);
                } catch (MalformedURLException e) {
                    System.out.println("MalformedURLException for " + urlAddress);
                } catch (IOException e) {
                    System.out.println("IOException for " + urlAddress);
                } finally {
                    try {
                        if (inputStream != null)
                            inputStream.close();
                        if (outputStream != null)
                            outputStream.close();
                        if (connection != null)
                            connection.disconnect();
                    } catch (Exception e) {

                    }
                }
            }
        });
    }
}
