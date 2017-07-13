package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
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

        Map<LocalDate, Integer> map = mealList.stream()
                .collect(Collectors.toMap(                  //создаётся Map.merge(LocalDate, calories, v1 + v2);
                        m -> m.getDateTime().toLocalDate(),
                        m -> m.getCalories(),
                        (v1, v2) -> v1 + v2));

        return mealList.stream()
                .map(m -> new UserMealWithExceed(m.getDateTime(), m.getDescription(), m.getCalories(), map.get(m.getDateTime().toLocalDate()) > caloriesPerDay))
                .collect(Collectors.toList());
    }

    /*
    //Вариант с одним стримом, совершенно адовый, но демонстрирующий много возможностей стримов - как пример.
    public static List<UserMealWithExceed> getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay)  {
        return mealList.stream()
                .collect(Collectors.groupingBy(meal -> meal.getDateTime().toLocalDate()))
                .entrySet().stream().map(entry -> {
                    int sumOfCalories = entry.getValue().stream().mapToInt(UserMeal::getCalories).sum();
                    return entry.getValue().stream().filter(meal -> TimeUtil.isBetween(meal.getDateTime().toLocalTime(), startTime, endTime))
                            .map(meal -> new UserMealWithExceed(meal.getDateTime(), meal.getDescription(), meal.getCalories(), sumOfCalories > caloriesPerDay))
                            .collect(Collectors.toList());})
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }*/
}
