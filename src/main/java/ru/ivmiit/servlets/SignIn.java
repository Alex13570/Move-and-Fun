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
import java.io.IOException;
import java.util.Optional;

@WebServlet("/sign-in")
public class SignIn extends HttpServlet {

    private UsersService usersService;

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        ServletContext servletContext = servletConfig.getServletContext();
        usersService = (UsersService) servletContext.getAttribute("usersService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("jsp/sign-in.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        req.setAttribute("errors", null);

        Optional<User> optionalUser = usersService.login(User.builder().email(email).password(password).build());
        if(optionalUser.isPresent()){
            req.getSession(true).setAttribute("user", optionalUser.get());
            resp.setStatus(302);
            resp.setHeader("Location", "/list");
        }else{
            req.setAttribute("error", true);
            req.getRequestDispatcher("jsp/sign-in.jsp").forward(req,resp);
        }
    }
}
