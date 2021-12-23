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

@WebServlet("/delete")
public class Delete extends HttpServlet {

    private EventsService eventsService;

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        ServletContext servletContext = servletConfig.getServletContext();
        eventsService = (EventsService) servletContext.getAttribute("eventsService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User currentUser = (User) req.getSession().getAttribute("user");
        Integer id = Integer.parseInt(req.getParameter("id"));

        Optional<Event> optionalEvent = eventsService.findById(id);

        String result = "";

        if(optionalEvent.isPresent()){
            Event event = optionalEvent.get();
            List<Event> adminEvents = eventsService.listAllWhereAdmin(currentUser);
            if(adminEvents.contains(event)){
                eventsService.deleteEvent(event);
                result = "Вы успешно удалили своё мероприятие.";
            }else{
                result = "Вы пытаетесь удалить не своё мероприятие!";
            }
        }else{
            result = "Мероприятие, которое вы пытаетесь удалить, не существует.";
        }
        req.setAttribute("msg", result);
        req.getRequestDispatcher("jsp/join-result.jsp").forward(req,resp);
    }
}
