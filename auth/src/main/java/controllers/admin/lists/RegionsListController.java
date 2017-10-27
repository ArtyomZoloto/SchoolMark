package controllers.admin.lists;

import exceptions.RegionDAOException;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import controllers.admin.lists.builder.RegionHtmlPartBuilder;
import services.exceptions.ServicesAuthGetPropertyNotFoundException;
import services.exceptions.ServicesAuthTemplateNotFoundException;
import utils.jsp.GetJspContent;
import utils.properties.PropertyValueGetter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static controllers.constants.ServletConstants.BASE_JSP_PATH;

@Controller
public class RegionsListController extends HttpServlet {
    private static final Logger logger = Logger.getLogger(RegionsListController.class);

    @RequestMapping(value = "/admin/regions", method = RequestMethod.GET)
    public void doGet(HttpServletRequest req, HttpServletResponse resp) {
        RequestDispatcher dispatcher =
                req.getServletContext().getRequestDispatcher(BASE_JSP_PATH);
        try {
            try {
                RegionHtmlPartBuilder regionHtmlPartBuilder = new RegionHtmlPartBuilder(req.getServletContext());
                PropertyValueGetter propertyValueGetter = new PropertyValueGetter();
                String jsp = propertyValueGetter.getPropertyByKey("amr_list_jsp_path");
                req.setAttribute("pagetitle", "Список регионов");
                req.setAttribute("modaltitle", "Добавить регион");
                req.setAttribute("table", regionHtmlPartBuilder.getContentForAllRegions());
                req.setAttribute("content", new GetJspContent(req, resp).getContent(jsp));
                dispatcher.forward(req, resp);
            } catch (ServicesAuthGetPropertyNotFoundException
                    | RegionDAOException
                    | ServicesAuthTemplateNotFoundException
                    | ServletException e) {
                logger.error(e.getMessage());
                req.setAttribute("error", e.getMessage());
                dispatcher.forward(req, resp);
            }
        } catch (IOException | ServletException e) {
            e.printStackTrace();
        }
    }
}
