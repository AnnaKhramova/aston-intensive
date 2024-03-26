package ru.akhramova.hw2.service;

import ru.akhramova.hw2.exception.NotFoundException;
import ru.akhramova.hw2.model.City;
import ru.akhramova.hw2.repository.CityRepository;

import java.util.List;

public class CityService {
    private final CityRepository repository;

    public CityService(CityRepository repository) {
        this.repository = repository;
    }

    public List<City> all() {
        return repository.all();
    }

    public City getById(long id) {
        return repository.getById(id).orElseThrow(NotFoundException::new);
    }

    public City save(City city) {
        return repository.save(city);
    }

    public void removeById(long id) {
        repository.removeById(id);
    }
}
