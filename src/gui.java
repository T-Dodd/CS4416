import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class gui {
    private static JTextField filePathTextField;
    private static JTextArea validityTextArea;

    public gui(){
        run();
    }
    public static void run() {
        JFrame frame = new JFrame("Regex Code Analyzer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 700);

        // Use BorderLayout for the frame
        frame.setLayout(new BorderLayout());

        JButton execute = new JButton("Execute");
        JButton selectFileButton = new JButton("Select File");
        filePathTextField = new JTextField(30);
        validityTextArea = new JTextArea();
        validityTextArea.setEditable(false);

        selectFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectFile();
            }
        });

        execute.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        // Create a panel for the top section with FlowLayout and empty border
        JPanel topPanel = new JPanel(new FlowLayout());
        topPanel.add(execute);
        topPanel.add(selectFileButton);
        topPanel.add(filePathTextField);
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Adjust margins

        // Set the background color of the validity text area to white
        validityTextArea.setBackground(Color.WHITE);

        // Create a panel for the center section with empty border
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(new JScrollPane(validityTextArea), BorderLayout.CENTER);
        centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Adjust margins

        // Add the top panel and the center panel to the frame
        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(centerPanel, BorderLayout.CENTER);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static void selectFile() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(null);

        if (result == JFileChooser.APPROVE_OPTION) {
            String output = "";
            File selectedFile = fileChooser.getSelectedFile();
            filePathTextField.setText(selectedFile.getAbsolutePath());
            //check if the file path exists
            if(!isValidFilePath(selectedFile.getAbsolutePath())) validityTextArea.setText("Invalid file path");
        }
    }
    public static boolean isValidFilePath(String filePath) {
        File file = new File(filePath);
        return file.exists() && file.isFile();
    }



}
