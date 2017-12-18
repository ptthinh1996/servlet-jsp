import Persistence.insertFile;
import Service.uploadFile;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import practice.sv.bai1.Student;
import sv.practice.mysql.JDBCStatement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "Project8")
public class Project8 extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DiskFileItemFactory fileItemFactory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(fileItemFactory);
        List<FileItem> fileItems = null;
        try {
            fileItems = upload.parseRequest(request);
        } catch (FileUploadException e) {
            e.printStackTrace();
        }
        String dirUrl = request.getServletContext().getRealPath("") + "files";
        String fileName = new String();
        fileName = uploadFile.uploadFile(fileItems, dirUrl);
        if (fileName.endsWith(".csv") && fileName!=null) {
            String fileUrl = dirUrl + File.separator + fileName;
            insertFile.main(fileUrl);
            response.sendRedirect("/insert");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ArrayList<Student> students;
        students = (ArrayList<Student>) JDBCStatement.readData();
        request.setAttribute("students", students);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/show");
        dispatcher.forward(request, response);
    }
}
