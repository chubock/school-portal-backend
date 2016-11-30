package com.avin.schoolportal.dto;

import java.io.Serializable;

/**
 * Created by Yubar on 11/5/2016.
 */
public interface AbstractDTO<T> extends Serializable {
    public T convert();
}
