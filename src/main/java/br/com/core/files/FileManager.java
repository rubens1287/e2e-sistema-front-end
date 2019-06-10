package br.com.core.files;

import br.com.core.report.ExtentReports;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Iterator;

import static org.apache.commons.io.FilenameUtils.removeExtension;

/**
 * @since 16/05/2018
 */
public class FileManager {
    private static String[] extensions;

    /**
     * @return the extensions of files that will be managed
     */
    public String[] getExtensions() {
        return extensions;
    }

    /**
     * @param extensions Types of the extensions for manage
     */
    public void setFilterExtensions(String[] extensions) {
        this.extensions = extensions;
    }

    /**
     * Delete one file from path and file name
     *
     * @param file object file with the way to access file system
     */
    public boolean deleteFile(File file) {
        boolean worked = false;
        try {
            worked = file.delete();
        } catch (Exception e) {
            ExtentReports.appendToReport("[deleteFile] - Error while trying to delete file! " + e.getMessage());
        }
        return worked;
    }

    /**
     * Delete all files from path and file name
     *
     * @param fullPath way to access file system
     */
    public void deleteAllFile(String fullPath) {
        try {
            File path = new File(fullPath);
            File folder = new File(path.getParent());
            if (folder.isDirectory()) {
                File[] files = folder.listFiles();
                for (File file : files) {
                    file.delete();
                }
            }
        } catch (NullPointerException e) {
            ExtentReports.appendToReport("[deleteAllFile] - Error while trying to delete all files from directory! " + e.getMessage());
        }

    }

    /**
     * Get path of file
     *
     * @param fullPath way to access file system
     */
    public String getPathOfFile(String fullPath) {
        File path;
        File folder = null;
        try {
            path = new File(fullPath);
            folder = new File(path.getParent());

        } catch (NullPointerException e) {
            ExtentReports.appendToReport("[deleteAllFile] - Error while trying to delete all files from directory! " + e.getMessage());
        }
        return folder.getPath();

    }

    /**
     * Returns the Filename without the extension
     *
     * @param filePath Full path for the file
     * @return the filename without extension
     */
    public String getFileName(String filePath) {
        return removeExtension(filePath);
    }

    /**
     * returns the content of a file in UTF-8
     *
     * @param file file to read
     * @return Full content of a file in UTF-8
     */
    public String getContentFile(File file) {
        String sFileContent = "";
        try {
            sFileContent = FileUtils.readFileToString(file, "UTF-8");
        } catch (IOException e) {
            ExtentReports.appendToReport(e.getMessage());
        }
        return sFileContent;
    }

    /**
     * List files of a certain type (extensions[])
     *
     * @param file object File with path of files
     * @return Collection of Files
     */
    public Collection<File> getFilesList(File file) {
        return FileUtils.listFiles(file, getExtensions(), false);
    }

    /**
     * Gets the iterator of a folder
     *
     * @param path path of files
     * @return iterator of the folder
     */
    public Iterator<File> getFilesIterator(String path) {
        return FileUtils.iterateFiles(new File(path), getExtensions(), false);
    }

    /**
     * Gets the file that contains certain String into the folder
     *
     * @param sPath Path to the folder
     * @param sName Name part to search for
     * @return File that contains sName
     */
    public File containsFileNameInFolder(String sPath, String sName) {
        File file = null;
        File temp;
        Iterator<File> iter = getFilesIterator(sPath);
        while (iter.hasNext()) {
            temp = iter.next();
            if (temp.getName().contains(sName)) {
                file = temp;
                break;
            }
        }
        return file;
    }

    /**
     * Check if the file is newer into the folder
     *
     * @param filePath pass path to be checked
     * @param minutes  the time between modified time and current time, the program will check in this interval
     * @return condition
     */
    public File isFileNewer(String filePath, int minutes) {
        File file = new File(filePath);
        Collection<File> fileCollection;
        SimpleDateFormat sf = new SimpleDateFormat("ddMMyyyyHHmm");
        fileCollection = getFilesList(file);

        for (File files : fileCollection) {
            long currentDate = Long.parseLong(sf.format(Calendar.getInstance().getTime()));
            for (int timeout = 0; timeout < minutes; timeout++) {
                long lastModifiedDate = Long.parseLong(sf.format(files.lastModified()));
                if (Long.toString(lastModifiedDate).contains(Long.toString(currentDate))) {
                    return files;
                }
                currentDate--;
            }
        }
        return null;
    }
}

