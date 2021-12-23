package ru.ivmiit.servlets;


import ru.ivmiit.models.FileInfo;
import ru.ivmiit.models.User;
import ru.ivmiit.services.FilesService;
import ru.ivmiit.services.UsersService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/file-upload")
@MultipartConfig
public class FilesUploadServlet extends HttpServlet {
    private FilesService filesService;
    private UsersService usersService;

    @Override
    public void init(ServletConfig config) {
        filesService = (FilesService) config.getServletContext().getAttribute("filesService");
        usersService = (UsersService) config.getServletContext().getAttribute("usersService");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/jsp/files-upload.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Part part = request.getPart("file");
        if(part.getSize()<15){
            response.setStatus(400);
            super.doPost(request,response);
        }
        FileInfo fileInfo = filesService.saveFileToStorage(part.getInputStream(),
                part.getSubmittedFileName(),
                part.getContentType(),
                part.getSize());

        User currentUser = (User)request.getSession().getAttribute("user");
        currentUser.setAvatarId(Integer.parseInt(fileInfo.getId().toString()));

        usersService.update(currentUser);

        request.getRequestDispatcher("/profile").forward(request,response);
    }
}