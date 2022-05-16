package com.langrsoft.reporting;

import org.apache.commons.io.IOUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPReply;

import java.io.*;

public class FileReport implements Report {
    private boolean isLoaded = false;
    private String filename;
    private String name;
    private String text;
    private static final String FTP_SERVER = "ftp.somewhere.com";
//       "gatekeeper.dec.com";

    public FileReport(String filename) {
        this.filename = filename;

        FTPClient ftp = new FTPClient();
        FTPClientConfig config = new FTPClientConfig();
        ftp.configure(config);
        try {
            int reply;
            ftp.connect(FTP_SERVER);
            reply = ftp.getReplyCode();

            if (!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();
                throw new RuntimeException("FTP server refused connection");
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
        if (!isLoaded) {
            load();
        }
        return text;
    }

    @Override
    public String getName() {
        if (!isLoaded) {
            load();
        }
        return name;
    }

    public void load() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("local.txt"));
            String[] list = load(reader);
            name = list[0];
            text = list[1];
            isLoaded = true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public String[] load(BufferedReader reader) {
        try {
            String first = reader.readLine();

            StringBuffer buffer = new StringBuffer();

            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
                buffer.append(System.lineSeparator());
            }
            String rest = buffer.toString();

            return new String[]{first, rest};

        } catch (IOException e) {
            throw new RuntimeException("unable to load "+ filename, e);
        }
    }

}
