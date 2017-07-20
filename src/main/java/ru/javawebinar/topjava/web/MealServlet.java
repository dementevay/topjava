package ru.javawebinar.topjava.web;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;
import ru.javawebinar.topjava.util.TimeUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
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

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String edit = request.getParameter("edit_btn");
        String delete = request.getParameter("delete_btn");
        String save = request.getParameter("isNew");

        if (edit != null) {
            int id = Integer.valueOf(edit);
            //открыть meal.jsp с данными этой еды
            for (MealWithExceed m : mealsWithExceeded) {
                if (m.getId() == id) {
                    request.setAttribute("meal", mealsWithExceeded.get(id-1));
                    request.getRequestDispatcher("/meal.jsp").forward(request, response);
                    break;
                }
            }
        }

        if (delete != null) {
            int id = Integer.valueOf(delete);
            //удалить еду
            for (Meal m : meals) {
                if (m.getId() == id) {
                    meals.remove(m);
                    updateMealsWithExceed();
                    break;
                }
            }
        }

        if (save != null) {
            int id = Integer.valueOf(save);
            String date = request.getParameter("date");
            String decript = request.getParameter("decript");
            String calories = request.getParameter("calories");

            LocalDateTime dateTime = TimeUtil.stringToLocalDateTime(date);

            //for save
            if (id > 0){
                for (Meal m : meals) {
                    if (m.getId() == id) {
                        m.setDateTime(dateTime);
                        m.setCalories(Integer.valueOf(calories));
                        m.setDescription(decript);
                        updateMealsWithExceed();
                        break;
                    }
                }
            }
            //for create
              else {
                Meal meal = new Meal(dateTime, decript, Integer.valueOf(calories));
                meals.add(meal);
                updateMealsWithExceed();
            }
        }

        request.setAttribute("meals_list", mealsWithExceeded);
        request.getRequestDispatcher("/meals.jsp").forward(request, response);
    }

    private void updateMealsWithExceed () {
        mealsWithExceeded = getFilteredWithExceeded(meals, LocalTime.of(00, 01), LocalTime.of(23, 59), 2000);
    }
}
