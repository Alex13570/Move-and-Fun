package ru.ivmiit.servlets;

import ru.ivmiit.models.User;
import ru.ivmiit.services.UsersService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/sign-up")
public class SignUp extends HttpServlet {

    private UsersService usersService;

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        ServletContext servletContext = servletConfig.getServletContext();
        usersService = (UsersService) servletContext.getAttribute("usersService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("jsp/sign-up.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String firstName = req.getParameter("first_name");
        String lastName = req.getParameter("last_name");

        User currentUser = User.builder()
                .email(email)
                .password(password)
                .firstName(firstName)
                .lastName(lastName)
                .avatarId(-1)
                .build();

        List<String> errors = usersService.getErrors(currentUser);
        req.setAttribute("errors", null);

        if(errors.size()==0){
            // Registration permitted
            currentUser = usersService.register(currentUser);
            HttpSession session = req.getSession(true);
            session.setAttribute("user", currentUser);
            resp.setStatus(302);
            resp.setHeader("Location", "/list");
        }else{
            // Denied
            req.setAttribute("errors", errors);
            req.getRequestDispatcher("jsp/sign-up.jsp").forward(req,resp);
        }
    }
}
