package calendar.controllers;

import calendar.dao.exceptions.TestDAOException;
import calendar.services.CalendarService;
import calendar.utils.CalendarCell;
import classes.CommonSettings;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import security.CustomUser;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;

import static utils.Settings.ERROR_ATTR;

@Controller
public class CalendarController {

    private static Logger logger = Logger.getLogger(CalendarController.class);

    private CalendarService calendarService;

    @Autowired
    public CalendarController(CalendarService calendarService) {
        this.calendarService = calendarService;
    }

    @RequestMapping(value={"/calendar", "/organizer/calendar"}, method = RequestMethod.GET)
    public ModelAndView getCalendar(@RequestParam(required = false) String dayOfMonth) {
        ModelAndView modelAndView;
        int userId = ((CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
        LocalDate date = getDateFromString(dayOfMonth);
        try {
            CalendarCell[][] calendar = calendarService.getCalendarCells(userId, date);
            modelAndView = fillSuccessModelAndView(calendar, date);
        } catch (TestDAOException e) {
            String errorText = "Ошибка при заполнении календаря";
            logger.error(errorText);
            modelAndView = fillErrorModelAndView(errorText);
        }
        return modelAndView;
    }

    private LocalDate getDateFromString(String dayOfMonthString) {
        if (dayOfMonthString == null) {
            return LocalDate.now();
        } else {
            return LocalDate.parse(dayOfMonthString);
        }
    }

    private String getMonthNameInUpperCase(LocalDate date) {
        return date.getMonth().getDisplayName(TextStyle.FULL_STANDALONE, Locale.forLanguageTag("ru")).toUpperCase();
    }

    private ModelAndView fillSuccessModelAndView(Object calendar, LocalDate date) {
        ModelAndView modelAndView = new ModelAndView("/calendar");
        modelAndView.addObject("calendar", calendar);
        modelAndView.addObject("beginData", date);
        modelAndView.addObject("monthName", getMonthNameInUpperCase(date));
        return modelAndView;
    }

    private ModelAndView fillErrorModelAndView(String errorText) {
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject(ERROR_ATTR, errorText);
        return modelAndView;
    }
}