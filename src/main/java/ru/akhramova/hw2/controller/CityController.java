package ru.akhramova.hw2.controller;

import com.google.gson.Gson;
import ru.akhramova.hw2.exception.NotFoundException;
import ru.akhramova.hw2.model.City;
import ru.akhramova.hw2.service.CityService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Reader;

public class CityController {
    public static final String APPLICATION_JSON = "application/json; charset=UTF-8";
    private final CityService service;

    public CityController(CityService service) {
        this.service = service;
    }

    public void all(HttpServletResponse response) throws IOException {
        response.setContentType(APPLICATION_JSON);
        final var data = service.all();
        final var gson = new Gson();
        response.getWriter().print(gson.toJson(data));
    }

    public void getById(long id, HttpServletResponse response) throws IOException {
        response.setContentType(APPLICATION_JSON);
        try {
            final var data = service.getById(id);
            final var gson = new Gson();
            response.getWriter().print(gson.toJson(data));
        } catch (NotFoundException e) {
            response.sendError(404, e.getMessage());
        }
    }

    public void save(Reader body, HttpServletResponse response) throws IOException {
        response.setContentType(APPLICATION_JSON);
        final var gson = new Gson();
        final var city = gson.fromJson(body, City.class);
        final var data = service.save(city);
        response.getWriter().print(gson.toJson(data));
    }

    public void removeById(long id, HttpServletResponse response) throws IOException {
        service.removeById(id);
        response.setContentType(APPLICATION_JSON);
        final var data = "Post with id " + id + " removed";
        final var gson = new Gson();
        response.getWriter().print(gson.toJson(data));
    }
}
