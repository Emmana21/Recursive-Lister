import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

public class RecursiveLister extends JFrame {
    private JTextArea fileListArea;
    private JFileChooser fileChooser;

    public RecursiveLister() {
        super("Recursive File Lister");
        setLayout(new BorderLayout());

        // Title label
        JLabel titleLabel = new JLabel("Recursive File Lister");
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        add(titleLabel, BorderLayout.NORTH);

        // File list area with scroll pane
        fileListArea = new JTextArea(20, 40);
        JScrollPane scrollPane = new JScrollPane(fileListArea);
        add(scrollPane, BorderLayout.CENTER);

        // Buttons panel
        JPanel buttonsPanel = new JPanel();
        JButton startButton = new JButton("Select Directory");
        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                selectDirectory();
            }
        });
        buttonsPanel.add(startButton);

        JButton quitButton = new JButton("Quit");
        quitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        buttonsPanel.add(quitButton);

        add(buttonsPanel, BorderLayout.SOUTH);

        // Set up file chooser
        fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        // Set the look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            SwingUtilities.updateComponentTreeUI(this);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void selectDirectory() {
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            fileListArea.setText(""); // Clear previous contents
            File selectedDirectory = fileChooser.getSelectedFile();
            listFiles(selectedDirectory);
        }
    }

    private void listFiles(File directory) {
        fileListArea.append("Listing files in: " + directory.getAbsolutePath() + "\n");

        // List files in current directory
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    listFiles(file); // Recursive call for subdirectory
                } else {
                    fileListArea.append(file.getAbsolutePath() + "\n");
                }
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new RecursiveLister();
        });
    }
}
