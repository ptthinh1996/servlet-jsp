import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import Service.*;
import Persistence.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

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
            String dirUrl = request.getServletContext().getRealPath("") + "files";
            String fileName = new String();
            fileName = uploadFile.uploadFile(fileItems, dirUrl);
            if (fileName == null) {
                out.println("Upload Failed!");
            } else {
                out.println("Upload Succeed");
            }
            if (fileName.endsWith(".csv")) {
                String fileUrl = dirUrl + File.separator + fileName;
                insertFile.main(fileUrl);
                out.println("<br>");
                out.println("Insert Done!");
            }
            out.println("<html>\n" +
                    "  <head>\n" +
                    "    <title>Project 6</title>\n" +
                    "      <link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css\">\n" +
                    "  </head>\n" +
                    "  <body>\n" +
                    "  <div class=\"container\">\n" +
                    "      <div class=\"row\">\n" +
                    "          <div class=\"col-md-offset-4 col-md-4\" style=\"margin-top: 50px; padding-top: 20px; border: 1px solid #cccccc;\">\n" +
                    "              <form action=\"/print\" method=\"post\">\n" +
                    "                  <button type=\"submit\" class=\"btn btn-default\">Print</button>\n" +
                    "              </form>\n" +
                    "          </div>\n" +
                    "      </div>\n" +
                    "  </div>\n" +
                    "  </body>\n" +
                    "</html>\n");
            out.close();


        } catch (FileUploadException e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
