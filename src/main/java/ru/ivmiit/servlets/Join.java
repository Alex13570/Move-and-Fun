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

@WebServlet("/join")
public class Join extends HttpServlet {

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
            List<Event> events = eventsService.listAllWhereParticipant(currentUser);
            if(events.contains(event)){
                result = "Вы уже присоеденились к этому мероприятию.";
            }else{
                eventsService.joinEvent(event, currentUser);
                result = "Вы успешно присоеденились к мероприятию!";
            }
        }else{
            result = "Мероприятие, к которому вы пытаетесь присоединиться, не существует.";
        }
        req.setAttribute("msg", result);
        req.getRequestDispatcher("jsp/join-result.jsp").forward(req,resp);
    }
}
