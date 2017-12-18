package Service;

import org.apache.commons.fileupload.FileItem;

import java.io.File;
import java.util.List;

public class uploadFile {
    public static String uploadFile(List<FileItem> fileItems, String dirUrl) {
        for (FileItem fileItem : fileItems) {
            if (!fileItem.isFormField()) {
                String fileName = fileItem.getName();
                if (!fileName.equals("")) {
                    File dir = new File(dirUrl);
                    if (!dir.exists()) {
                        dir.mkdir();
                    }
                    String fileImg = dirUrl + File.separator + fileName;
                    File file = new File(fileImg);

                    try {
                        fileItem.write(file);
                        System.out.println("Directory: \n" + dirUrl);
                        return fileName;
                    } catch (Exception e) {
                        e.printStackTrace();
                        return null;
                    }
                }
            }
        }
        return null;
    }

}
