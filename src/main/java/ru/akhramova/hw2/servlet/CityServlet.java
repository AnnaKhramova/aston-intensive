package ru.akhramova.hw2.servlet;

import ru.akhramova.hw2.controller.CityController;
import ru.akhramova.hw2.repository.CityRepository;
import ru.akhramova.hw2.service.CityService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = "/city/*")
public class CityServlet extends HttpServlet {
    private CityController controller;
    private static final String PATH = "/city";

    @Override
    public void init() {
        final var repository = new CityRepository();
        final var service = new CityService(repository);
        controller = new CityController(service);
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) {
        // если деплоились в root context, то достаточно этого
        try {
            final var path = req.getRequestURI();
            final var method = req.getMethod();
            // primitive routing
            if (method.equals(Methods.GET.getTitle()) && path.matches(PATH)) {
                controller.all(resp);
                return;
            }
            if (method.equals(Methods.GET.getTitle()) && path.matches(PATH + "/\\d+")) {
                // easy way
                final var id = Long.parseLong(path.substring(path.lastIndexOf("/")));
                controller.getById(id, resp);
                return;
            }
            if (method.equals(Methods.POST.getTitle()) && path.equals(PATH)) {
                controller.save(req.getReader(), resp);
                return;
            }
            if (method.equals(Methods.DELETE.getTitle()) && path.matches(PATH + "/\\d+")) {
                // easy way
                final var id = Long.parseLong(path.substring(path.lastIndexOf("/") + 1));
                controller.removeById(id, resp);
                return;
            }
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
