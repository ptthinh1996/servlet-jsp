import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.io.PrintWriter;

import practice.sv.bai1.*;

@WebServlet(name = "readFile")
public class readFile extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        DiskFileItemFactory fileItemFactory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(fileItemFactory);

        try {
            List<FileItem> fileItems = upload.parseRequest(request);
            for (FileItem fileItem : fileItems) {
                if (!fileItem.isFormField()) {
                    String fileName = fileItem.getName();
                    if (!fileName.equals("")) {
                        String dirUrl = request.getServletContext().getRealPath("") + "files";
                        File dir = new File(dirUrl);

                        if (!dir.exists()) {
                            dir.mkdir();
                        }

                        String fileImg = dirUrl + File.separator + fileName;
                        File file = new File(fileImg);

                        try {
                            fileItem.write(file);
                            System.out.println("Upload Success!");
                            out.println("Upload Success!");
                            System.out.println("Directory: \n" + dirUrl);
                        } catch (Exception e) {
                            System.out.println("Upload Fail! ");
                            out.println("Upload Fail! ");
                            e.printStackTrace();
                        }

                        if (fileName.endsWith(".csv")) {
                            List<Student> students = new ArrayList<>();
                            students = ReadFile.listStudent(dirUrl + File.separator + fileName);
                            for (Student student : students) {
                                out.println("<br>");
                                out.println(student.toString());
                            }
                        }
                    }
                }
            }
        } catch (FileUploadException e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
