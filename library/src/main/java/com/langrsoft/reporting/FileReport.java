package com.langrsoft.reporting;

import org.apache.commons.io.IOUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPReply;

import java.io.*;

public class FileReport implements Report {
    static class FTPException extends RuntimeException {
        FTPException(String message) {
            super(message);
        }
    }

    static class FileException extends RuntimeException {
        FileException(String message, Throwable cause) {
            super(message, cause);
        }

        FileException(Throwable cause) {
            super(cause);
        }
    }

    private final String filename;
    private boolean isLoaded = false;
    private String name;
    private String text;
    private static final String FTP_SERVER = "ftp.somewhere.com"; // also gatekeeper.dec.com

    public FileReport(String filename) {
        this.filename = filename;

        var ftp = new FTPClient();
        ftp.configure(new FTPClientConfig());
        try {
            ftp.connect(FTP_SERVER);
            int reply = ftp.getReplyCode();

            if (!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();
                throw new FTPException("FTP server refused connection");
            }

            ftp.login("ftp", "");
            ftp.setFileType(FTP.BINARY_FILE_TYPE);

            try (var inputStream = ftp.retrieveFileStream("robots.txt")) {
                try (var localOutputStream = new FileOutputStream("local.txt")){
                    IOUtils.copy(inputStream, localOutputStream);
                    localOutputStream.flush();
                }
            }

            ftp.logout();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ftp.isConnected()) {
                try {
                    ftp.disconnect();
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            }
        }
    }

    @Override
    public String getText() {
        if (!isLoaded) load();
        return text;
    }

    @Override
    public String getName() {
        if (!isLoaded) load();
        return name;
    }

    public void load() {
        try {
            var reader = new BufferedReader(new FileReader("local.txt"));
            var list = load(reader);
            name = list[0];
            text = list[1];
            isLoaded = true;
        } catch (FileNotFoundException e) {
            throw new FileException(e);
        }
    }

    public String[] load(BufferedReader reader) {
        try {
            var first = reader.readLine();
            var rest = loadAsString(reader);
            return new String[]{first, rest};
        } catch (IOException e) {
            throw new FileException("unable to load " + filename, e);
        }
    }

    private String loadAsString(BufferedReader reader) throws IOException {
        var buffer = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            buffer.append(line);
            buffer.append(System.lineSeparator());
        }
        return buffer.toString();
    }

}
