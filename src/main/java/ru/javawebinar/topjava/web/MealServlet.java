package ru.javawebinar.topjava.web;

import ru.javawebinar.topjava.model.MealWithExceed;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalTime;
import java.util.List;

import static ru.javawebinar.topjava.util.MealsUtil.getFilteredWithExceeded;
import static ru.javawebinar.topjava.util.MealsUtil.meals;

/**
 * Created by Andrey Dementev on 18.07.17.
 */
public class MealServlet extends HttpServlet {
    public static List<MealWithExceed> mealsWithExceeded;
    static {
        mealsWithExceeded = getFilteredWithExceeded(meals, LocalTime.of(00, 01), LocalTime.of(23, 59), 2000);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        request.setAttribute("meals_list", mealsWithExceeded);
        request.getRequestDispatcher("/meals.jsp").forward(request, response);


    }
}
