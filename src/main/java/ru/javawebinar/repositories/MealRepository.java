package ru.javawebinar.repositories;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface MealRepository {
    void save(Meal meal);
    Meal getById(int id);
    List<Meal> getAll();
    void delete(int id);
}
