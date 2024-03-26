package ru.akhramova.hw2.servlet;

public enum Methods {
    GET("GET"),
    POST("POST"),
    DELETE("DELETE");


    private String title;

    Methods(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
