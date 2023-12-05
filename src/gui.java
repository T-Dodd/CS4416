import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileReader;
import java.util.Arrays;
import java.util.HashMap;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;




public class gui {
    private static boolean isFunctionsDialogOpen = false;
    private static JTextField filePathTextField;
    private static JTextArea validityTextArea;
    private File selectedFile;
    Regex tester;

    public gui() {
        try {
            // Set the look and feel
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
                 UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        setupGui();
        tester = new Regex();
    }

    public void setupGui() {
        JFrame frame = new JFrame("Regex Code Analyzer");

        setFileLogo(frame);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 700);

        // Use BorderLayout for the frame
        frame.setLayout(new BorderLayout());

        JButton execute = new JButton("Execute");
        JButton selectFileButton = new JButton("Select File");
        JButton modifyFuncs = new JButton("Edit Functions");
        filePathTextField = new JTextField(30);
        validityTextArea = new JTextArea();
        validityTextArea.setEditable(false);

        selectFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectFile(frame);
            }
        });

        execute.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedFile == null) {
                    validityTextArea.setText("No file selected");
                    return;
                }
                String programContents = readFile(selectedFile);
                String errors = tester.find(programContents);
                validityTextArea.setText(errors);
            }
        });

        modifyFuncs.addActionListener(new ActionListener() {

            /*Opens up function detection customization window*/
            @Override
            public void actionPerformed(ActionEvent e) {
                showChecklistDialog(frame);
            }
        });

        // Create a panel for the top section with FlowLayout and empty border
        JPanel topPanel = new JPanel(new FlowLayout());
        topPanel.add(modifyFuncs);
        topPanel.add(execute);
        topPanel.add(selectFileButton);
        topPanel.add(filePathTextField);
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 20, 10)); // Adjust margins

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


    private void selectFile(JFrame frame) {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(frame);

        if (result == JFileChooser.APPROVE_OPTION) {
            String output = "";
            selectedFile = fileChooser.getSelectedFile();
            filePathTextField.setText(selectedFile.getAbsolutePath());
            //check if the file path exists
            if (!isValidFilePath(selectedFile.getAbsolutePath())) validityTextArea.setText("Invalid file path");
        }
    }

    public static boolean isValidFilePath(String filePath) {
        File file = new File(filePath);
        return file.exists() && file.isFile();
    }

    /*
     * Reads the contents of a File object and returns a string.
     * */
    private static String readFile(File FILE) {
        try {
            FileReader reader = new FileReader(FILE);
            String fileContents = "";
            boolean reading = true;
            do {
                int nextChar = reader.read();
                if (nextChar == -1) { //EOF reached
                    reading = false;
                }
                fileContents += (char) nextChar;
            } while (reading);
            System.out.println("File contents have been read");

            //DEBUGGING For Testing the file reader contents are correct
            //System.out.print(fileContents);
            //System.out.println();

            reader.close();

            return fileContents;
        } catch (Exception e) {
            System.out.println("Error reading file contents. Returning empty value");
            return "";
        }
    }

    /*
     * This functions gives a check list of all the functions to look for
     * */
    private static void showChecklistDialog(JFrame parent) {

        if (isFunctionsDialogOpen) {
            return;
        }

        JFrame checkboxesFrame = new JFrame("Functions");
        setFileLogo(checkboxesFrame);

        DeprecatedFuncs depFunctions = new DeprecatedFuncs();
        int size = depFunctions.dict.size();
        Object[] keysArray = depFunctions.dict.keySet().toArray();
        Arrays.sort(keysArray);

        checkboxesFrame.setLayout(new GridLayout(size / 4, 4));
        JCheckBox[] checkboxes = new JCheckBox[size];

        for (int i = 0; i < size; i++) {
            checkboxes[i] = new JCheckBox(keysArray[i].toString());
            checkboxesFrame.add(checkboxes[i]);
        }

        toggleAllCheckboxes(checkboxes);
        JButton toggleButton = new JButton("Toggle All");
        toggleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                toggleAllCheckboxes(checkboxes);
            }
        });

        JButton clearButton = new JButton("Clear");
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearAllCheckboxes(checkboxes);
            }
        });

        checkboxesFrame.add(toggleButton);
        checkboxesFrame.add(clearButton);
        checkboxesFrame.pack();
        checkboxesFrame.setLocationRelativeTo(parent);
        checkboxesFrame.setVisible(true);

        isFunctionsDialogOpen = true;

        // Add a WindowListener to reset the flag when the dialog is closed
        checkboxesFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                isFunctionsDialogOpen = false;
            }
        });
    }

    private static void toggleAllCheckboxes(JCheckBox[] checkBoxes) {
        for (int i = 0; i < checkBoxes.length; i++) {
            checkBoxes[i].setSelected(true);
        }
    }

    private static void clearAllCheckboxes(JCheckBox[] checkBoxes) {
        for (int i = 0; i < checkBoxes.length; i++) {
            checkBoxes[i].setSelected(false);
        }
    }

    /*Add a function to the deprecated functions dictionary*/
    private void addFunc(String input) {
    }

    /*Removes a function from the deprecated functions dictionary*/
    private void removeFunc(String input) {
    }

    private static void setFileLogo(JFrame frame) {
        try {
            BufferedImage logoImage = ImageIO.read(gui.class.getResource("/resources/logo.png"));
            frame.setIconImage(logoImage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
