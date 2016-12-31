package com.avin.schoolportal.rest;

import com.avin.schoolportal.domain.File;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Yubar on 12/29/2016.
 */

@Component
class FileRenderer {

    public void renderFile(File file, HttpServletResponse response) throws IOException {
        renderFile(file, response, "attachment");
    }

    public void renderFile(File file, HttpServletResponse response, String deposition) throws IOException {
        if (file == null)
            response.sendError(404);
        else {
            response.setContentType(file.getContentType());
            response.setContentLengthLong(file.getSize());
            response.setHeader("Content-Disposition", deposition + "; filename=" + file.getName());
            response.getOutputStream().write(file.getContent());
        }
    }
}
