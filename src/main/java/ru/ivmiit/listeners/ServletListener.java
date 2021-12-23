package ru.ivmiit.listeners;

import com.zaxxer.hikari.HikariDataSource;
import ru.ivmiit.repositories.EventsRepository;
import ru.ivmiit.repositories.FilesRepository;
import ru.ivmiit.repositories.UsersRepository;
import ru.ivmiit.repositories.impl.EventRepositoryJdbcTemplateIml;
import ru.ivmiit.repositories.impl.FilesRepositoryJdbcImpl;
import ru.ivmiit.repositories.impl.UserRepositoryJdbcTemplateImpl;
import ru.ivmiit.services.EventsService;
import ru.ivmiit.services.FilesService;
import ru.ivmiit.services.UsersService;
import ru.ivmiit.services.impl.EventsServiceImpl;
import ru.ivmiit.services.impl.FilesServiceImpl;
import ru.ivmiit.services.impl.UsersServiceImpl;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class ServletListener implements ServletContextListener {

    private HikariDataSource dataSource;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        dataSource = new HikariDataSource();
        dataSource.setJdbcUrl("jdbc:postgresql://localhost:5432/eventdb");
        dataSource.setUsername("postgres");
        dataSource.setPassword("qwerty");
        dataSource.setDriverClassName("org.postgresql.Driver");
        UsersRepository usersRepository = new UserRepositoryJdbcTemplateImpl(dataSource);
        UsersService usersService = new UsersServiceImpl(usersRepository);
        EventsRepository eventsRepository = new EventRepositoryJdbcTemplateIml(dataSource);
        EventsService eventsService = new EventsServiceImpl(eventsRepository);
        FilesRepository filesRepository = new FilesRepositoryJdbcImpl(dataSource);
        FilesService filesService = new FilesServiceImpl(filesRepository);
        ServletContext servletContext = sce.getServletContext();
        servletContext.setAttribute("usersService", usersService);
        servletContext.setAttribute("eventsService", eventsService);
        servletContext.setAttribute("filesService", filesService);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        dataSource.close();
    }
}
