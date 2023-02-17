package com.example.library;

import com.example.library.model.Book;
import com.example.library.model.Student;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.example.library.BooksServlet.bookList;

@WebServlet(name = "studentsServlet", value = "/students-servlet")
public class StudentsServlet extends HttpServlet {

    static List<Student> studentList = new ArrayList<>(
            Arrays.asList(
                    new Student(29293, "Nurtas", "Nenurtas", "2006"),
                    new Student(29294, "Almas", "Nealmas", "2006"),
                    new Student(29296, "Abylai", "Neabilay", "2001"),
                    new Student(29297, "Abay", "Neabay", "2011")
            )
    );

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        response.setContentType("text/html");

        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h1>List of Students</h1>");
        out.println("<a href=\"books-servlet\">Books Servlet</a>\n");
        out.println("<table border='1'>");
        out.println("<tr><th>ID</th><th>Name</th><th>Surname</th><th>Group</th><th>Borrowed Books</th><th>Assign Book</th></tr>");
        for (Student student : studentList) {
            out.println("<tr>");
            out.println("<td>" + student.getId() + "</td>");
            out.println("<td>" + student.getName() + "</td>");
            out.println("<td>" + student.getSurname() + "</td>");
            out.println("<td>" + student.getGroup() + "</td>");
            out.println("<td>");
            if (student.getBorrowedBooks().isEmpty()) {
                out.println("None");
            } else {
                for (Book book : student.getBorrowedBooks()) {
                    out.println(book.getTitle() + "<br>");
                }
            }
            out.println("</td>");
            out.println("<td><form method='post'>");
            out.println("<input type='hidden' name='idSign' value='" + student.getId() + "'>");
            out.println("<select name='bookTitleSign'>");
            for (Book book : bookList) {
                if (book.getQuantity() > 0) {
                    out.println("<option value='" + book.getTitle() + "'>" + book.getTitle() + " by " + book.getAuthor() + "</option>");
                }
            }
            out.println("</select>");
            out.println("<input name='actions' type='submit' value='Assign Book'>");
            out.println("</form></td>");

            out.println("<td><form method='post'>");
            out.println("<input type='hidden' name='idUnSign' value='" + student.getId() + "'>");
            out.println("<select name='bookTitleUnSign'>");
            for (Book book : student.getBorrowedBooks()) {
                out.println("<option value='" + book.getTitle() + "'>" + book.getTitle() + " by " + book.getAuthor() + "</option>");
            }
            out.println("</select>");
            out.println("<input name='actions' type='submit' value='UnAssign Book'>");
            out.println("</form></td>");

            out.println("</tr>");
        }
        out.println("</table>");

        out.println("<h2>Add a new student</h2>");
        out.println("<form method='post'>");
        out.println("ID: <input type='text' name='id'><br>");
        out.println("Name: <input type='text' name='name'><br>");
        out.println("Surname: <input type='text' name='surname'><br>");
        out.println("Group: <input type='text' name='group'><br>");
        out.println("<input name='actions' type='submit' value='Add'>");
        out.println("</form>");

        out.println("</body></html>");
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String actions = request.getParameter("actions");
        if (actions.equals("Assign Book")) {
            int studentIdToSign = Integer.parseInt(request.getParameter("idSign"));
            String bookTitleToSign = request.getParameter("bookTitleSign");
            assignBook(studentIdToSign, bookTitleToSign);
        } else if (actions.equals("UnAssign Book")) {
            int studentIdToUnSign = Integer.parseInt(request.getParameter("idUnSign"));
            String bookTitleToUnSign = request.getParameter("bookTitleUnSign");
            unSignBook(studentIdToUnSign, bookTitleToUnSign);
        } else if (actions.equals("Add"))  {
            String name = request.getParameter("name");
            String surname = request.getParameter("surname");
            String group = request.getParameter("group");
            int id = Integer.parseInt(request.getParameter("id"));
            Student newStudent = new Student(id, name, surname, group);
            studentList.add(newStudent);
        }

        doGet(request, response);
    }

    public void assignBook(int studentId, String bookTitle) {
        Student student = null;
        for (Student s : studentList) {
            if (s.getId() == studentId) {
                student = s;
                break;
            }
        }
        Book book = null;
        for (Book b : bookList) {
            if (b.getTitle().equals(bookTitle)) {
                book = b;
                break;
            }
        }
        if (book != null) {
            if (book.getQuantity() > 0) {
                book.setQuantity(book.getQuantity() - 1);
                if (student != null) {
                    student.getBorrowedBooks().add(book);
                }
            }
        }
    }

    public void unSignBook(int studentId, String bookTitle) {
        Student student = null;
        for (Student s : studentList) {
            if (s.getId() == studentId) {
                student = s;
                break;
            }
        }
        Book book = null;
        for (Book b : bookList) {
            if (b.getTitle().equals(bookTitle)) {
                book = b;
                break;
            }
        }
        if (book != null && student != null) {
            student.getBorrowedBooks().remove(book);
            book.setQuantity(book.getQuantity() + 1);
        }
    }
}