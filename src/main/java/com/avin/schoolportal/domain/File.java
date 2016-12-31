package com.avin.schoolportal.domain;

import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;

/**
 * Created by Yubar on 12/11/2016.
 */

@Entity
public class File implements Serializable {

    private long id;
    private String name;
    private String contentType;
    private long size;
    private byte[] content;
    private School school;

    public File() {
    }

    public File(MultipartFile multipartFile) throws IOException {
        setName(multipartFile.getOriginalFilename());
        setSize(multipartFile.getSize());
        setContentType(multipartFile.getContentType());
        setContent(multipartFile.getBytes());
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @NotNull
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NotNull
    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    @NotNull
    @Column(length = 1024 * 1024 * 10)
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

    @ManyToOne
    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof File)) return false;

        File file = (File) o;

        if (getId() != file.getId()) return false;
        if (getId() > 0)
            return true;
        if (getSize() != file.getSize()) return false;
        if (getName() != null ? !getName().equals(file.getName()) : file.getName() != null) return false;
        if (getContentType() != null ? !getContentType().equals(file.getContentType()) : file.getContentType() != null)
            return false;
        if (!Arrays.equals(getContent(), file.getContent())) return false;
        return getSchool() != null ? getSchool().equals(file.getSchool()) : file.getSchool() == null;

    }

    @Override
    public int hashCode() {
        if (getId() > 0)
            return (int) getId();
        int result = (int) (getId() ^ (getId() >>> 32));
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getContentType() != null ? getContentType().hashCode() : 0);
        result = 31 * result + (int) (getSize() ^ (getSize() >>> 32));
        result = 31 * result + Arrays.hashCode(getContent());
        result = 31 * result + (getSchool() != null ? getSchool().hashCode() : 0);
        return result;
    }
}
