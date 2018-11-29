import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main extends JFrame {
    static JTextArea textArea;

    public Main() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(new Dimension(960, 590));
        setResizable(false);
        setTitle("DayZ BS-LogReader Version: -1");


        textArea = new JTextArea();
        JScrollPane scrollBar = new JScrollPane(textArea);

        textArea.setSize(new Dimension(955, 527));
        textArea.setMaximumSize(new Dimension(955, 525));
        textArea.setMinimumSize(new Dimension(955, 525));

        scrollBar.setSize(new Dimension(940, 500));
        scrollBar.setMaximumSize(new Dimension(940, 500));
        scrollBar.setMinimumSize(new Dimension(940, 500));

        textArea.setLineWrap(true);
        textArea.setEditable(false);

        scrollBar.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollBar.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        DefaultCaret caret = (DefaultCaret)textArea.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

        add(scrollBar);

        setVisible(true);
    }

    public static void main(String[] args) {
        new Main();


        BufferedReader reader = null;
        String line;

        try {
            if (!Files.exists(Paths.get("./config.txt"))){
                Files.createFile(Paths.get("./config.txt"));
                JOptionPane.showMessageDialog(null, "Please set the path of your scripts.log in the script.txt file.");
                System.exit(-1);
            }

            File config = new File("./config.txt");
            BufferedReader config_reader = new BufferedReader(new FileReader(config));

            if(config.length() == 0){
                JOptionPane.showMessageDialog(null, "You config is empty.");
                System.exit(-1);
            }
            String logpath = config_reader.readLine();
            if(Paths.get(logpath).toAbsolutePath().toString().endsWith(".log") || Paths.get(logpath).toAbsolutePath().toString().endsWith(".txt")) {
                reader = new BufferedReader(new FileReader(logpath));
            }else{
                JOptionPane.showMessageDialog(null, "Invalid script.log path, please check config.txt. File type must be txt, or log.");
                System.exit(-1);
            }

            do {
                while ((line = reader.readLine()) != null) {
                    textArea.append("   " + line + "\n");
                    textArea.setCaretPosition(textArea.getDocument().getLength());
                }
            } while (true);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}