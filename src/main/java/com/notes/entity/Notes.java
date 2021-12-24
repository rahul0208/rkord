package com.notes.entity;

import com.azure.spring.data.cosmos.core.mapping.Container;
import org.springframework.data.annotation.Id;


@Container(containerName = "notescollection")
public class Notes {
    @Id
    private Integer id;
    private String message;

    public void setId(Integer id) {
        this.id = id;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }
}
