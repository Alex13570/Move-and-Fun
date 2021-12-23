package ru.ivmiit.servlets;

import ru.ivmiit.models.Event;
import ru.ivmiit.models.User;
import ru.ivmiit.services.EventsService;
import ru.ivmiit.services.UsersService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

@WebServlet("/list")
public class EventListServlet extends HttpServlet {

    private EventsService eventsService;

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        ServletContext servletContext = servletConfig.getServletContext();
        eventsService = (EventsService) servletContext.getAttribute("eventsService");
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User currentUser = (User) req.getSession().getAttribute("user");
        String s = req.getParameter("sort");
        String param = req.getParameter("sortParameter");
        Integer typeSort = 1;
        if(s!=null){
            try {
                typeSort = Integer.parseInt(s);
            }catch(Exception e){
                typeSort = 1;
            }
        }

        List<Event> eventList;

        if(typeSort==4 && param!=null){
            eventList = eventsService.findByTitle(param);
            typeSort = 1;
        }else{
            eventList = eventsService.listAll();
        }

        if(typeSort>3) typeSort = 1;

        List<Event> activeEventList = eventsService.listAllWhereParticipant(currentUser);
        List<Event> adminEventList = eventsService.listAllWhereAdmin(currentUser);

        req.setAttribute("events", eventList);
        req.setAttribute("activeEvents", activeEventList);
        req.setAttribute("adminEvents", adminEventList);

        req.setAttribute("typeSort", typeSort);

        req.getRequestDispatcher("jsp/list.jsp").forward(req,resp);
    }
}
