package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.repositories.MealRepository;
import ru.javawebinar.repositories.inmemory.InMemoryMealRepository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);

    private final MealRepository mealRepository;

    public MealServlet() {
        mealRepository = new InMemoryMealRepository();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action") == null ? "all" : request.getParameter("action");
        log.info("action: {}", action);

        switch (action) {
            case "delete":
                mealRepository.delete(getParamId(request));
                request.setAttribute("mealList", MealsUtil.filteredByStreams(mealRepository.getAll(), LocalTime.MIN, LocalTime.MAX, 2000));
                request.getRequestDispatcher("/meals.jsp").forward(request, response);
                break;
            case "update":
                request.setAttribute("meal", mealRepository.getById(getParamId(request)));
                request.getRequestDispatcher("/mealForm.jsp").forward(request, response);
                break;
            case "create":
                request.setAttribute("meal", new Meal(null, LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "Новая еда", 555));
                request.getRequestDispatcher("/mealForm.jsp").forward(request, response);
                break;
            case "all":
            default:
                request.setAttribute("mealList", MealsUtil.filteredByStreams(mealRepository.getAll(), LocalTime.MIN, LocalTime.MAX, 2000));
                request.getRequestDispatcher("/meals.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        log.info("post");
        Integer id = getParamId(req);
        log.info("id: {}", id);
        String description = req.getParameter("description");
        log.info("description: {}", description);
        LocalDateTime dateTime = LocalDateTime.parse(req.getParameter("dateTime"));
        log.info("dateTime: {}", dateTime);
        Integer calories = Integer.parseInt(req.getParameter("calories"));
        log.info("calories: {}", calories);
        mealRepository.save(new Meal(id, dateTime, description, calories));

        resp.sendRedirect(req.getContextPath() + "/meals");
    }

    private Integer getParamId(HttpServletRequest request) {
        String id = request.getParameter("id");
        if (id == null || id.isEmpty()) {
            return null;
        }
        return Integer.parseInt(id);
    }
}
