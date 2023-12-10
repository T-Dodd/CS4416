import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileReader;
import java.util.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import javax.imageio.ImageIO;

public class gui {
    private static boolean isFunctionsDialogOpen;
    private static JTextField filePathTextField;
    private static JTextArea validityTextArea;
    private File selectedFile;
    private static Regex tester;
    public static List<String> uncheckedBoxes = new ArrayList<>();

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

        JButton execute = new JButton("Scan");
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
                String errors = tester.find(programContents, uncheckedBoxes);
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

            reader.close();

            return fileContents;
        } catch (Exception e) {
            System.out.println("Error reading file contents. Returning empty value");
            return "";
        }
    }

    /*
     * This functions gives a checklist of all the functions to look for
     * */
    private static void showChecklistDialog(JFrame parent) {
        if (isFunctionsDialogOpen) {
            return;
        }

        JFrame checkboxesFrame = new JFrame("Functions");
        setFileLogo(checkboxesFrame);
        checkboxesFrame.setSize(400, 550);

        // Top panel setup
        JPanel topPanel = new JPanel(new FlowLayout());
        JTextField funcInput = new JTextField("Enter Bad Func", 20);
        JTextField alternativeInput = new JTextField("Enter Preferred Alternative", 20);
        JButton addButton = new JButton("Add Function");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String func = funcInput.getText();
                String alternative = alternativeInput.getText();
                addFunction(func, alternative);
            }
        });

        topPanel.add(funcInput);
        topPanel.add(alternativeInput);
        topPanel.add(addButton);
        topPanel.setBorder(BorderFactory.createEmptyBorder(15, 10, 20, 10));

        // Center panel setup
        JPanel centerPanel = new JPanel();
        centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        int size = tester.getDict().size();
        Object[] keysArray = tester.getDict().keySet().toArray();
        Arrays.sort(keysArray);

        checkboxesFrame.setLayout(new GridLayout(size / 2, 10));
        JCheckBox[] checkboxes = new JCheckBox[size];

        for (int i = 0; i < size; i++) {
            checkboxes[i] = new JCheckBox(keysArray[i].toString());
            checkboxesFrame.add(checkboxes[i]);
            if (uncheckedBoxes.contains(checkboxes[i].getText())) {
                checkboxes[i].setSelected(false);
            } else {
                checkboxes[i].setSelected(true);
            }
        }
        checkboxesFrame.add(centerPanel);

        // Toggle button setup
        JButton toggleButton = new JButton("Toggle All");
        toggleButton.addActionListener(e -> toggleAllCheckboxes(checkboxes));

        // Clear button setup
        JButton clearButton = new JButton("Clear");
        clearButton.addActionListener(e -> clearAllCheckboxes(checkboxes));

        // Adding components to the frame
        checkboxesFrame.add(topPanel, BorderLayout.NORTH);
        checkboxesFrame.add(centerPanel, BorderLayout.CENTER);
        checkboxesFrame.add(toggleButton, BorderLayout.SOUTH);
        checkboxesFrame.add(clearButton, BorderLayout.SOUTH);

        checkboxesFrame.setLocationRelativeTo(parent);
        checkboxesFrame.setVisible(true);

        isFunctionsDialogOpen = true;

        // Add a WindowListener to reset the flag when the dialog is closed
        checkboxesFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                trackUncheckedBoxes(checkboxes);
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

    private static void setFileLogo(JFrame frame) {
        try {
            BufferedImage logoImage = ImageIO.read(gui.class.getResource("/resources/logo.png"));
            frame.setIconImage(logoImage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void trackUncheckedBoxes(JCheckBox[] checkBoxes) {
        uncheckedBoxes.clear();
        for (int i = 0; i < checkBoxes.length; i++) {
            if(!checkBoxes[i].isSelected()) uncheckedBoxes.add(checkBoxes[i].getText());
        }
    }

    private static void addFunction(String badFunc, String alternative){
        if (!tester.getDict().containsKey(badFunc)) {
             tester.addFunc(badFunc, alternative);
        }
    }

    public static void removeFunction(String badFunc){
        if (tester.getDict().containsKey(badFunc)) {
            tester.removeFunc(badFunc);
        }
    }

}
