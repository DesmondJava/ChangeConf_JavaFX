package model;

import com.jcraft.jsch.*;
import org.controlsfx.dialog.Dialogs;

import java.io.*;
import java.net.*;
import java.util.List;

public class SSHConnect {

    private final String SFTPHOST;
    private final int SFTPPORT = 22;
    private final String SFTPUSER;
    private final String SFTPPASS;
    private final String SFTPWORKINGDIR = "/etc/pektoral/";
    private final String SFTPWORKINFILE = "pektoral.conf";

    private Session session = null;
    private Channel channel = null;
    private ChannelSftp channelSftp = null;

    public SSHConnect(String host, String user, String password) {
        SFTPHOST = host;
        SFTPUSER = user;
        SFTPPASS = password;
    }

    public List<ConfValue> loadConfFileFromSSH() {
        List<ConfValue> result = null;
        try {
            JSch jsch = new JSch();
            session = jsch.getSession(SFTPUSER, SFTPHOST, SFTPPORT);
            session.setPassword(SFTPPASS);
            java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);
            session.connect();
            channel = session.openChannel("sftp");
            channel.connect();
            channelSftp = (ChannelSftp) channel;
            channelSftp.cd(SFTPWORKINGDIR);
            BufferedReader readFromFile = new BufferedReader(new InputStreamReader(channelSftp.get(SFTPWORKINFILE)));
            result = Parse.parseFile(readFromFile);
        } catch (JSchException ex) {
            Dialogs.create()
                    .title("Lost connection")
                    .masthead("Something wrong with your connection...")
                    .message("Maybe correct you login or password.'")
                    .showWarning();
        } catch (Exception ex) {
            ex.printStackTrace();
            Dialogs.create()
                    .title("Error")
                    .masthead("Something wrong with your connection...")
                    .message("No file or directory. Maybe something else...")
                    .showWarning();
        }
        channelSftp.exit();
        channel.disconnect();
        session.disconnect();
        return result;
    }

    public void saveFileOnSSH(List<ConfValue> list) throws ConnectException, JSchException, SftpException, IOException {
        JSch jsch = new JSch();
        session = jsch.getSession(SFTPUSER, SFTPHOST, SFTPPORT);
        session.setPassword(SFTPPASS);
        java.util.Properties config = new java.util.Properties();
        config.put("StrictHostKeyChecking", "no");
        session.setConfig(config);
        session.connect();
        channel = session.openChannel("sftp");
        channel.connect();
        channelSftp = (ChannelSftp) channel;
        channelSftp.cd(SFTPWORKINGDIR);
        try {
            File temp = new File(SFTPWORKINFILE);
            BufferedWriter bw = new BufferedWriter(new FileWriter(temp));
            for (ConfValue values : list) {
                bw.write(values.getTitle() + "\t" + values.getValue() + "\n");
            }
            bw.close();
            channelSftp.put(new FileInputStream(temp), SFTPWORKINFILE, ChannelSftp.OVERWRITE);
            Thread.sleep(6000);
            channelSftp.exit();
            session.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
            Dialogs.create()
                    .title("Error")
                    .masthead("Something wrong with your connection...")
                    .message("No file or directory. Maybe something else...")
                    .showWarning();
        }
    }
}


