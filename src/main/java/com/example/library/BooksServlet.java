package com.example.library;

import com.example.library.model.Book;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

@WebServlet(name = "booksServlet", value = "/books-servlet")
public class BooksServlet extends HttpServlet {

    public static List<Book> bookList = new ArrayList<>(
            Arrays.asList(
                    new Book("Harry Potter", "Royling", "1234", 2001, 12),
                    new Book("Ice and Fire", "Tommy", "321", 2011, 15),
                    new Book("Heroes and Swords", "Ben", "432", 2021, 55),
                    new Book("Quantum Physics", "John", "657", 2014, 76)
            )
    );

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        response.setContentType("text/html");

        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h1>Book List</h1>");
        out.println("<a href=\"students-servlet\">Students Servlet</a>");
        out.println("<table>");
        out.println("<tr><th>Title</th><th>Author</th><th>ISBN</th><th>Year</th><th>Quantity</th></tr>");
        for (Book book : bookList) {
            out.println("<tr>");
            out.println("<td>" + book.getTitle() + "</td>");
            out.println("<td>" + book.getAuthor() + "</td>");
            out.println("<td>" + book.getIsbn() + "</td>");
            out.println("<td>" + book.getYear() + "</td>");
            out.println("<td>" + book.getQuantity() + "</td>");
            out.println("</tr>");
        }
        out.println("</table>");
        out.println("<h2>Add a new book</h2>");
        out.println("<form method='post'>");
        out.println("Title: <input type='text' name='title'><br>");
        out.println("Author: <input type='text' name='author'><br>");
        out.println("ISBN: <input type='text' name='isbn'><br>");
        out.println("Year: <input type='text' name='year'><br>");
        out.println("Quantity: <input type='text' name='quantity'><br>");
        out.println("<input type='submit' value='Add'>");
        out.println("</form>");
        out.println("</body></html>");
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String title = request.getParameter("title");
        String author = request.getParameter("author");
        String isbn = request.getParameter("isbn");
        int year = Integer.parseInt(request.getParameter("year"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        Book newBook = new Book(title, author, isbn, year, quantity);
        bookList.add(newBook);
        doGet(request, response);
    }
}
