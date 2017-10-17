package classes;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Teacher extends User {
    private final int id;
    private final String lastname;
    private final String firstname;
    private final String patronymic;
    private final School school;
    private final List<SubjectTeacherConnector> subject;
    private final int minClass;
    private final int maxClass;
    //TODO: квалификаций у учителя может быть много и каждая из них связана с предметом
    private final String qualification;


    //TODO вернуть тип subject перед комитом
    public Teacher(int id, int userId, String login, LocalDate regDate, String lastname, String firstname, String patronymic, School school, Collection<Subject> subject, int minClass, int maxClass, String qualification) {
        super(userId, login, regDate);
        this.id = id;
        this.lastname = lastname;
        this.firstname = firstname;
        this.patronymic = patronymic;
        this.school = school;
        this.subject = new ArrayList<>();
        this.subject.addAll(subject);
        this.minClass = minClass;
        this.maxClass = maxClass;
        this.qualification = qualification;
    }

    public int getId() {
        return id;
    }

    public String getLastname() {
        return lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public School getSchool() {
        return school;
    }

    public List<SubjectTeacherConnector> getSubject() {
        return subject;
    }

    public int getMinClass() {
        return minClass;
    }

    public int getMaxClass() {
        return maxClass;
    }

    public String getQualification() {
        return qualification;
    }

    /**
     * @return фамилия учителя + инициалы
     */
    public String getFullName() {
        return lastname + " "
                + firstname.substring(0, 1) + ". "
                + patronymic.substring(0, 1) + ".";
    }
}
