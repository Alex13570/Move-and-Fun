package ru.ivmiit.servlets;

import ru.ivmiit.models.Event;
import ru.ivmiit.models.User;
import ru.ivmiit.services.EventsService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@WebServlet("/create")
public class Create extends HttpServlet {

    private EventsService eventsService;

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        ServletContext servletContext = servletConfig.getServletContext();
        eventsService = (EventsService) servletContext.getAttribute("eventsService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("jsp/create.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User currentUser = (User) req.getSession().getAttribute("user");
        //Integer id = Integer.parseInt(req.getParameter("id"));
        String title = req.getParameter("title");
        String description = req.getParameter("description");
        String date = req.getParameter("date");
        //Integer adminId = Integer.parseInt(req.getParameter("admin_id"));

        if(title.length()<1 || description.length()<1){
            req.setAttribute("msg","Введены не все данные!");
            req.getRequestDispatcher("jsp/join-result.jsp").forward(req,resp);
            return;
        }

        if(description.length()>255)description = description.substring(0, 255);
        if(title.length()>31) title = title.substring(0,31);

        Event event = Event.builder()
                .title(title)
                .description(description)
                .adminId(currentUser.getId())
                .date(date)
                .build();

        eventsService.addEvent(event);
        req.setAttribute("msg", "Вы успешно создали своё мероприятие.");
        req.getRequestDispatcher("jsp/join-result.jsp").forward(req,resp);
    }
}
