package com.avin.schoolportal.dto;

import com.avin.schoolportal.domain.File;

/**
 * Created by Yubar on 12/11/2016.
 */
public class FileDTO implements AbstractDTO<File> {

    private long id;
    private String name;
    private String contentType;
    private long size;
    private byte[] content;
    private SchoolDTO school;

    public FileDTO() {
    }

    public FileDTO(File file) {
        if (file != null) {
            setId(file.getId());
            setName(file.getName());
            setContentType(file.getContentType());
            setSize(file.getSize());
        }
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public SchoolDTO getSchool() {
        return school;
    }

    public void setSchool(SchoolDTO school) {
        this.school = school;
    }

    @Override
    public File convert() {
        File file = new File();
        file.setId(getId());
        file.setName(getName());
        file.setSize(getSize());
        file.setContentType(getContentType());
        file.setContent(getContent());
        if (getSchool() != null)
            file.setSchool(getSchool().convert());
        return file;
    }
}
