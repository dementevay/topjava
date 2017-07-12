package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * GKislin
 * 31.05.2015.
 */
public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510)
        );
        List<UserMealWithExceed> mm = getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
    }

    public static List<UserMealWithExceed> getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        return getWithExceed(mealList, caloriesPerDay).stream()
                .filter(m -> m.getDateTime().toLocalTime().isAfter(startTime) & m.getDateTime().toLocalTime().isBefore(endTime))
                .collect(Collectors.toList());
    }

    public static List<UserMealWithExceed> getWithExceed(List<UserMeal> mealList, int caloriesPerDay) {
        Map<LocalDate, Integer> map = new HashMap<>();

        for (UserMeal m : mealList) {
            map.merge(m.getDateTime().toLocalDate(), m.getCalories(), (v1, v2) -> v1 + v2);
        }

        return mealList.stream()
                .map(m -> new UserMealWithExceed(m.getDateTime(), m.getDescription(), m.getCalories(), map.containsKey(m.getDateTime().toLocalDate()) && map.get(m.getDateTime().toLocalDate()) > caloriesPerDay))
                .collect(Collectors.toList());
    }
}
