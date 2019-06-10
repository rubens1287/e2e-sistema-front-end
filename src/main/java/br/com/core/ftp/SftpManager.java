package br.com.core.ftp;

import br.com.core.report.ExtentReports;
import com.jcraft.jsch.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Properties;
import java.util.Vector;

/**
 * @since 16/05/2018
 */
public class SftpManager {
    private String server;
    private int port;
    private String user;
    private String password;
    private ChannelSftp channelSftp;
    private Session session;
    private Channel channel;

    /**
     * Constructor to set attributes of the class
     *
     * @param server   IP server to be connected
     * @param port     Port to be connected
     * @param user     user to be authenticated in while connecting
     * @param password password relative of the user param
     */
    public SftpManager(String server, int port, String user, String password) {
        this.server = server;
        this.port = port;
        this.user = user;
        this.password = password;
        channelSftp = null;
        session = null;
        channel = null;
    }

    public ChannelSftp getChannelSftp() {
        return channelSftp;
    }

    public void setChannelSftp(ChannelSftp channelSftp) {
        this.channelSftp = channelSftp;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    /**
     * Start connection to the object server (S)FTP
     */
    public void openConnection() {
        JSch jsch = new JSch();
        try {
            session = jsch.getSession(user, server, port);
            session.setPassword(password);
            Properties config = new Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);
            session.connect();
            channel = session.openChannel("sftp");
            channel.connect();
            channelSftp = (ChannelSftp) channel;
        } catch (JSchException e) {
            ExtentReports.appendToReport("Error on method 'openConnection': " + e.getMessage());
        }
    }

    /**
     * Close existing connection to (S)FTP server
     */
    public void closeConnection() {
        channelSftp.exit();
        channel.disconnect();
        session.disconnect();
    }

    /**
     * List files from a specified (S)FTP server path
     *
     * @param path Folder to have its files listed
     * @return Vector of files
     */
    public Vector listAll(String path) {
        Vector<ChannelSftp.LsEntry> listFiles = null;
        try {
            listFiles = channelSftp.ls(path);
        } catch (SftpException e) {
            ExtentReports.appendToReport("Error on method 'listFiles': " + e.getMessage());
        }
        return listFiles;
    }

    /**
     * Check if File or Folder exists in the specified Path of a SFTP server
     *
     * @param parentPath Folder to search
     * @param name       File or Folder to be searched
     * @return Boolean true if found
     */
    public boolean verifyExists(String parentPath, String name) {
        Vector<ChannelSftp.LsEntry> vector;
        boolean exists = false;
        vector = listAll(parentPath);
        for (ChannelSftp.LsEntry lsEntry : vector) {
            if (lsEntry.getFilename().contains(name)) {
                exists = true;
                break;
            }
        }
        return exists;
    }

    /**
     * Uploads a file to specific (S)FTP Folder
     *
     * @param sFilePath   Full Path to the file that will be uploaded
     * @param sFolderPath Full path to the (S)FTP Folder
     */
    public void sendFile(String sFilePath, String sFolderPath) {
        try {
            channelSftp.cd(sFolderPath);
            File f = new File(sFilePath);
            channelSftp.put(new FileInputStream(f), f.getName());
        } catch (Exception e) {
            ExtentReports.appendToReport("Error on method 'sendFile': " + e.getMessage());
        }
    }

    /**
     * Download a File from specified Path of a sFTP server
     *
     * @param absolutePath      absolute path to the file to be downloaded
     * @param absoluteLocalPath Local absolute path to the folder where the file should be saved
     */
    public void downloadFile(String absolutePath, String absoluteLocalPath) {
        try {
            OutputStream output = new FileOutputStream(absoluteLocalPath);
            channelSftp.get(absolutePath, output);
            output.close();
        } catch (Exception e) {
            ExtentReports.appendToReport("Error on method 'downloadFile': " + e.getMessage());
        }
    }

    /**
     * Get the time when the File was last modified in SFTP server
     *
     * @param absolutePath fully qualified path of the file in SFTP server
     * @return String with the time when the file was last modified
     */
    public String getModificationTime(String absolutePath) {
        String time = "";
        try {
            SftpATTRS attrs = channelSftp.lstat(absolutePath);
            time = attrs.getMtimeString();
        } catch (Exception e) {
            ExtentReports.appendToReport("Error on method 'getModificationTime': " + e.getMessage());
        }
        return time;
    }

    /**
     * Get the SftpATTRS (attributes) object
     *
     * @param absolutePath Folder to find
     * @return SftpATTRS with the attributes
     */
    public SftpATTRS getAttributes(String absolutePath) {
        SftpATTRS attrs = null;
        try {
            attrs = channelSftp.lstat(absolutePath);
        } catch (Exception e) {
            ExtentReports.appendToReport("Error on method 'getAttributes': " + e.getMessage());
        }
        return attrs;
    }

    /**
     * Delete a file from SFTP Server
     *
     * @param absolutePath Absolute path to the file in SFTP
     */
    public void deleteFile(String absolutePath) {
        try {
            channelSftp.rm(absolutePath);
        } catch (Exception e) {
            ExtentReports.appendToReport("Error on method 'deleteFile': " + e.getMessage());
        }
    }

    /**
     * Create a directory in SFTP
     *
     * @param pathDir path of the directory to be created
     */
    public void createDirectory(String pathDir) {
        try {
            channelSftp.mkdir(pathDir);
        } catch (Exception e) {
            ExtentReports.appendToReport("Error on method 'createDirectory': " + e.getMessage());
        }
    }

    /**
     * Delete a directory from SFTP
     *
     * @param pathDir path to the directory to be deleted
     */
    public void deleteDirectory(String pathDir) {
        try {
            channelSftp.rmdir(pathDir);
        } catch (Exception e) {
            ExtentReports.appendToReport("Error on method 'deleteDirectory': " + e.getMessage());
        }
    }

}