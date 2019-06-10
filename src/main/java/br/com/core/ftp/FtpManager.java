package br.com.core.ftp;

import br.com.core.report.ExtentReports;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

import java.io.*;

/**

 * @since 16/05/2018
 */
public class FtpManager {

    private String server;
    private int port;
    private String user;
    private String password;
    private FTPClient ftpClient;

    public FtpManager(String server, int port, String user, String password) {
        this.server = server;
        this.port = port;
        this.user = user;
        this.password = password;
    }

    public FTPClient getFtpClient() {
		return ftpClient;
	}

	public void setFtpClient(FTPClient ftpClient) {
		this.ftpClient = ftpClient;
	}

	/**
	 * Start connection to FTP server
	 */
	public void openConnection(){
		ftpClient = new FTPClient();
		ftpClient.setConnectTimeout(9000);
		try {
			ftpClient.connect(server, port);
			ftpClient.login(user, password);
			ftpClient.enterLocalPassiveMode();
			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
		} catch (IOException e) {
            ExtentReports.appendToReport("Error on method 'openConnection': " + e.getMessage());
		}
	}
	/**
	 * Close the connection with FTP server
	 */
	public void closeConnection(){
		if (ftpClient.isConnected()) {
			try {
				ftpClient.logout();
				ftpClient.disconnect();
			} catch (IOException e) {
                ExtentReports.appendToReport("Error on method 'closeConnection': " + e.getMessage());
			}
		}
	}

	/**
	 * Send a file to explicit FTP server
	 * @param localFilePath Full path to the file that will be sent
	 * @param ftpFilePath Folder in FTP where the file should be saved
     * @param ftpFileName File name with extension to be sent to FTP
	 */
	public void sendFile(String localFilePath, String ftpFilePath, String ftpFileName){
		try {
		    File firstLocalFile = new File(localFilePath);
			String firstRemoteFile = ftpFilePath + ftpFileName;
			InputStream inputStream = new FileInputStream(firstLocalFile);
			ftpClient.storeFile(firstRemoteFile, inputStream);
			ftpClient.getReplyCode();
			inputStream.close();
		} catch (IOException ex) {
            ExtentReports.appendToReport("Error on method 'sendFile': " + ex.getMessage());
		}
	}

    /**
     * List files on specific path in FTP server
     * @param path path to the folder where the files are located
     * @return FTPFile[] of files
     */
    public FTPFile[] listFiles(String path) {
        FTPFile[] listFiles = null;
        try {
            listFiles = ftpClient.listFiles(path);
        } catch (IOException e) {
            ExtentReports.appendToReport("Error on method 'listFiles': " + e.getMessage());
        }
        return listFiles;
    }


    /**
     * Check if File exists at specific path in FTP server
     * @param path Path to the folder where the file should be
     * @param name File to be searched
     * @return Boolean true if found
     */
    public boolean verifyFileExists(String path, String name) {
        FTPFile[] list;
        boolean exists = false;
        list = listFiles(path);
        for (FTPFile ftpFile : list) {
            if (ftpFile.getName().contains(name)) {
                exists = true;
                break;
            }
        }
        return exists;
    }
    /**
     * Download a File from FTP server
     * @param absolutePath absolute path to the file that should be downloaded from FTP server
     * @param absoluteLocalPath absolute path to the folder where the file should be saved
     * */
    public void downloadFile(String absolutePath, String absoluteLocalPath){
        try {
            OutputStream local = new FileOutputStream(absoluteLocalPath);
            ftpClient.retrieveFile(absolutePath, local);
        } catch (Exception e) {
            ExtentReports.appendToReport("Error on method 'downloadFile': " + e.getMessage());
        }
    }
    /**
     * Get the time when the File was last modified in FTP server
     * @param absolutePath fully qualified path of the file in FTP server
     * @return String with the time when the file was last modified
     * */
    public String getModificationTime(String absolutePath){
        String time = "";
        try {
            time = ftpClient.getModificationTime(absolutePath);
        } catch (IOException e) {
            ExtentReports.appendToReport("Error on method 'getModificationTime': " + e.getMessage());
        }
        return time;
    }

    /**
     * Delete a file from FTP Server
     * @param absolutePath Absolute path to the file in FTP
     */
    public void deleteFile(String absolutePath){
        try {
            ftpClient.deleteFile(absolutePath);
        } catch (IOException e) {
            ExtentReports.appendToReport("Error on method 'deleteFile': " + e.getMessage());
        }
    }

    /**
     * Create a directory in FTP
     * @param pathDir path of the directory to be created
     */
    public void createDirectory(String pathDir){
        try {
            ftpClient.makeDirectory(pathDir);
        } catch (IOException e) {
            ExtentReports.appendToReport("Error on method 'createDirectory': " + e.getMessage());
        }
    }

    /**
     * Delete a directory from FTP
     * @param pathDir path to the directory to be deleted
     */
    public void deleteDirectory(String pathDir){
        try {
            ftpClient.removeDirectory(pathDir);
        } catch (IOException e) {
            ExtentReports.appendToReport("Error on method 'deleteDirectory': " + e.getMessage());
        }
    }

    /**
     * List the directories of a Parent Directory on a FTP
     * @param pathParentDir Path to the parent directory to be listed
     * @return Array of FTPFiles with the directories
     */
    public FTPFile[] listDirectories(String pathParentDir){
        FTPFile[] dirs= null;
        try {
            dirs = ftpClient.listDirectories(pathParentDir);
        } catch (IOException e) {
            ExtentReports.appendToReport("Error on method 'listDirectories': " + e.getMessage());
        }
        return dirs;
    }

    /**
     * Verify if a directory exists on FTP
     * @param path path to the directory to be verified
     * @param name name of the directory to be searched
     * @return true if found
     */
    public boolean verifyDirectoryExists(String path, String name) {
        FTPFile[] list;
        boolean exists = false;
        list = listDirectories(path);
        for (FTPFile ftpFile : list) {
            if (ftpFile.getName().contains(name)) {
                exists = true;
                break;
            }
        }
        return exists;
    }

    /**
     * List both Files and Folders in a Directory on FTP
     * @param pathParentDir Path of the directory to be listed.
     * @return Array of Strings with the listed itens
     */
    public String[] listAll(String pathParentDir){
        String[] list = null;
        try {
            list = ftpClient.listNames(pathParentDir);
        } catch (IOException e) {
            ExtentReports.appendToReport("Error on method 'listAll': " + e.getMessage());
        }
        return list;
    }

}


