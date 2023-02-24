package com.example.library;

import com.example.library.model.Student;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.example.library.StudentsServlet.studentList;

@WebServlet(name = "mainServlet", value = "/main-servlet")
public class MainServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        response.setContentType("text/html");

        HttpSession session = request.getSession();

        if (session.getAttribute("id") != null) {
            response.sendRedirect(request.getContextPath() + "/students-servlet");
        }

        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h1>Auth and Reg</h1>");
//        out.println("<a href=\"books-servlet\">Books Servlet</a>\n");

        out.println("<h2>Auth</h2>");
        out.println("<form method='post'>");
        out.println("ID: <input type='text' name='id'><br>");
        out.println("Password: <input type='text' name='password'><br>");
        out.println("<input name='actions' type='submit' value='Auth'>");
        out.println("</form>");

        out.println("<h2>Reg</h2>");
        out.println("<form method='post'>");
        out.println("ID: <input type='text' name='id'><br>");
        out.println("Password: <input type='text' name='password'><br>");
        out.println("Name: <input type='text' name='name'><br>");
        out.println("Surname: <input type='text' name='surname'><br>");
        out.println("Group: <input type='text' name='group'><br>");
        out.println("<input name='actions' type='submit' value='Reg'>");
        out.println("</form>");

        out.println("</body></html>");
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        String actions = request.getParameter("actions");
        HttpSession session = request.getSession();

        if (actions.equals("Auth")) {
            int id = Integer.parseInt(request.getParameter("id"));
            String password = request.getParameter("password");

            for (Student student: studentList) {
                if (student.getId() == id){
                    session.setAttribute("id", id);
                    session.setAttribute("password", password);
                    break;
                }
            }

        } else if (actions.equals("Reg")) {
            String name = request.getParameter("name");
            String password = request.getParameter("password");
            String surname = request.getParameter("surname");
            String group = request.getParameter("group");
            int id = Integer.parseInt(request.getParameter("id"));
            Student newStudent = new Student(id, password, name, surname, group);

            studentList.add(newStudent);
            session.setAttribute("id", id);
            session.setAttribute("password", password);
        }

        doGet(request, response);
    }
}