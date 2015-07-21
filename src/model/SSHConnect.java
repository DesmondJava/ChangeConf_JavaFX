package model;

import com.jcraft.jsch.*;
import org.controlsfx.dialog.Dialogs;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class SSHConnect {

    private final String SFTPHOST;
    private final int    SFTPPORT = 22;
    private final String SFTPUSER;
    private final String SFTPPASS;
//    private final String SFTPWORKINGDIR = "/etc/pektoral/";
    private final String SFTPWORKINGDIR = "/home/vadym/";

    private Session     session     = null;
    private Channel     channel     = null;
    private ChannelSftp channelSftp = null;

    public SSHConnect(String host, String user, String password) {
        SFTPHOST = host;
        SFTPUSER = user;
        SFTPPASS = password;
    }

    public List<ConfValue> loadConfFileFromSSH(){
        try{
            JSch jsch = new JSch();
            session = jsch.getSession(SFTPUSER,SFTPHOST,SFTPPORT);
            session.setPassword(SFTPPASS);
            java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);
            session.connect();
            channel = session.openChannel("sftp");
            channel.connect();
            channelSftp = (ChannelSftp)channel;
            channelSftp.cd(SFTPWORKINGDIR);
        }catch(JSchException ex){
            Dialogs.create()
                    .title("Lost connection")
                    .masthead("Something wrong with your connection...")
                    .message("Maybe correct you login or password.'")
                    .showWarning();
        }catch(Exception ex){
            ex.printStackTrace();
            Dialogs.create()
                    .title("Error")
                    .masthead("Something wrong with your connection...")
                    .message("No file or directory. Maybe something else...")
                    .showWarning();
        }
        List<ConfValue> data = createObjectsFromFile();
        channelSftp.disconnect();
        channelSftp.exit();
        session.disconnect();
        channel.disconnect();

        return data;
    }

    public void saveFileOnSSH(List<ConfValue> list) {
        try {
            long begin = System.currentTimeMillis();
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

            OutputStream os = channelSftp.put("pektoralTest.conf", ChannelSftp.OVERWRITE);
            OutputStreamWriter safeFile = new OutputStreamWriter (os);
            for (ConfValue values : list){
                safeFile.write(values.getTitle() + " " + values.getValue() + "\n");
                Thread.sleep(100);
            }
            os.flush();
            safeFile.flush();
            safeFile.close();
            os.close();
            channelSftp.disconnect();
            session.disconnect();
            channel.disconnect();
            channelSftp.exit();

            long finish = System.currentTimeMillis();

            System.out.println("begin: " + begin + " - " + finish + " = " + (begin - finish));

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    private List<ConfValue> createObjectsFromFile() {
        final int word1 = 0;
        final int word2 = 1;
        final int word3 = 2;
        ArrayList<ConfValue> confValues = new ArrayList<>();
        try {

            Properties properties = new Properties();
//            properties.load(getClass().getResourceAsStream("/resources/textConfFile.properties"));
            properties.load(new InputStreamReader(new FileInputStream("src/resources/textConfFile.properties"), "windows-1251"));
            BufferedReader readFromFile = new BufferedReader(new InputStreamReader(channelSftp.get("pektoralTest.conf")));
            String line;
            while ((line = readFromFile.readLine()) != null) {
                if (!line.startsWith("#") && !line.isEmpty()) {
                    line = line.replaceAll("\t", " ");
                    line = line.replaceAll(" +", " ");

                    String[] lineArr = line.trim().split(" ");
                    switch (lineArr[word1].substring(0, 2)){
                        case "Au":
                            confValues.add(new ConfValue(lineArr[word1], lineArr[word2],
                                    properties.getProperty("gold"), properties.getProperty("metal_descr")));
                            break;
                        case "Ag":
                            confValues.add(new ConfValue(lineArr[word1], lineArr[word2],
                                    properties.getProperty("silver"), properties.getProperty("metal_descr")));
                            break;
                        case "Pt":
                            confValues.add(new ConfValue(lineArr[word1], lineArr[word2],
                                    properties.getProperty("platina"), properties.getProperty("metal_descr")));
                            break;
                        case "Pl":
                            confValues.add(new ConfValue(lineArr[word1], lineArr[word2],
                                    properties.getProperty("pl"), properties.getProperty("metal_descr")));
                            break;
                    }
                    switch (lineArr[word1]){
                        case "fine":
                            confValues.add(new ConfValue(lineArr[word1], lineArr[word2],
                                    properties.getProperty("stavka"), properties.getProperty("stavka_descr")));
                            break;
                        case "interest":
                            confValues.add(new ConfValue(lineArr[word1] + " " + lineArr[word2], lineArr[word3],
                                    properties.getProperty("percent"), properties.getProperty("percent_descr")));
                            break;
                        case "credit":
                            confValues.add(new ConfValue(lineArr[word1] + " " + lineArr[word2], lineArr[word3],
                                    properties.getProperty("credit"), properties.getProperty("credit_descr")));
                            break;
                        case "discount":
                            confValues.add(new ConfValue(lineArr[word1] + " " + lineArr[word2], lineArr[word3],
                                    properties.getProperty("discount"), properties.getProperty("discount_descr")));
                            break;
                        case "bonus":
                            confValues.add(new ConfValue(lineArr[word1] + " " + lineArr[word2], lineArr[word3],
                                    properties.getProperty("bonus"), properties.getProperty("bonus_descr")));
                            break;
                        case "withdraw_term":
                            confValues.add(new ConfValue(lineArr[word1], lineArr[word2],
                                    properties.getProperty("diff"), properties.getProperty("withdraw_term")));
                            break;
                        case "repawn_percent":
                            confValues.add(new ConfValue(lineArr[word1], lineArr[word2],
                                    properties.getProperty("diff"), properties.getProperty("repawn_percent")));
                            break;
                        case "default_term":
                            confValues.add(new ConfValue(lineArr[word1], lineArr[word2],
                                    properties.getProperty("diff"), properties.getProperty("default_term")));
                            break;
                        case "serial_port":
                            confValues.add(new ConfValue(lineArr[word1], lineArr[word2],
                                    properties.getProperty("diff"), properties.getProperty("serial_port")));
                            break;
                        case "printer_name":
                            confValues.add(new ConfValue(lineArr[word1], lineArr[word2],
                                    properties.getProperty("diff"), properties.getProperty("printer_name")));
                            break;
                        case "discount_border":
                            confValues.add(new ConfValue(lineArr[word1], lineArr[word2],
                                    properties.getProperty("diff"), properties.getProperty("withdraw_term")));
                            break;
                        case "nowait_term":
                            confValues.add(new ConfValue(lineArr[word1], lineArr[word2],
                                    properties.getProperty("diff"), properties.getProperty("nowait_term")));
                            break;

                    }
                }
            }
            readFromFile.close();
        } catch (SftpException | IOException e) {
            e.printStackTrace();
        }

        return confValues;
    }


}
