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

@WebServlet("/see")
public class SeeEvent extends HttpServlet {

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
        List<Event> eventList = eventsService.listAll();
        List<Event> activeEventList = eventsService.listAllWhereParticipant(currentUser);
        List<Event> adminEventList = eventsService.listAllWhereAdmin(currentUser);
        req.setAttribute("events", eventList);
        req.setAttribute("activeEvents", activeEventList);
        req.setAttribute("adminEvents", adminEventList);

        req.setAttribute("currentEvent", optionalEvent);
        req.getRequestDispatcher("jsp/see.jsp").forward(req,resp);
    }
}
