import Service.uploadFile;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import practice.sv.bai1.ReadFile;
import practice.sv.bai1.Student;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "Project7")
public class Project7 extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        DiskFileItemFactory fileItemFactory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(fileItemFactory);

        try {
            List<FileItem> fileItems = upload.parseRequest(request);
            String dirUrl = request.getServletContext().getRealPath("") + "files";
            String fileName = new String();
            fileName = uploadFile.uploadFile(fileItems, dirUrl);
            if (fileName == null) {
                out.println("Upload Failed!");
            } else {
                out.println("Upload Succeed");
            }

            if (fileName.endsWith(".csv")) {
                List<Student> students = new ArrayList<>();
                students = ReadFile.listStudent(dirUrl + File.separator + fileName);
                request.setAttribute("students", students);
                RequestDispatcher dispatcher = request.getRequestDispatcher("/show");
                dispatcher.forward(request, response);
            }


        } catch (
                FileUploadException e)

        {
            e.printStackTrace();
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
