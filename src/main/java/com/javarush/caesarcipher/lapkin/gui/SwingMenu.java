package com.javarush.caesarcipher.lapkin.gui;
import com.javarush.caesarcipher.lapkin.function.Function;
import com.javarush.caesarcipher.lapkin.handler.FileHandler;
import com.javarush.caesarcipher.lapkin.handler.TextDecryption;
import com.javarush.caesarcipher.lapkin.handler.TextHandler;
import static com.javarush.caesarcipher.lapkin.constans.ApplicationsConstants.*;
import static com.javarush.caesarcipher.lapkin.handler.TextDecryption.*;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class SwingMenu extends JFrame {
    public SwingMenu() {
        setTitle(APP_NAME);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        ImageIcon icon = new ImageIcon(
                "src/main/resources/images/im_pic2.jpg");
        setIconImage(icon.getImage());

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 1, 10, 10));
        JButton encryptButton = new JButton(ENCRYPT_FILE_BUTTON);
        JButton decryptButton = new JButton(DECRYPT_FILE_BUTTON);
        JButton bruteForceButton = new JButton(DECRYPTION_BRUTE_FORCE_BUTTON);
        JButton analyzerButton = new JButton("Криптоанализатор"); //...
        JButton exitButton = new JButton(EXIT_BUTTON);

        encryptButton.addActionListener(e -> encryptFile());
        decryptButton.addActionListener(e -> decryptFile());
        bruteForceButton.addActionListener(e -> decryptBruteForce());
        analyzerButton.addActionListener(e -> decryptAnalyzer()); //....
        exitButton.addActionListener(e -> System.exit(0));

        panel.add(encryptButton);
        panel.add(decryptButton);
        panel.add(bruteForceButton);
        panel.add(analyzerButton);
        panel.add(exitButton);
        add(panel);
        setVisible(true);
    }
    private void encryptFile() {
        String inputPath = chooseFile(SELECT_A_FILE_TO_ENCRYPT);
        if (inputPath != null) {
            String suggestedSaveName = inputPath.replaceAll("\\.txt$", POSTFIX_ENCRYPTION_FILE_NAME);
            String outputPath = chooseSaveLocation(SELECT_DIRECTORY_FOR_ENCRYPTED_FILE, suggestedSaveName);
            int shift = getShift();
            if (outputPath != null && shift != -1) {
                Function.encrypt(inputPath, outputPath, shift);
                JOptionPane.showMessageDialog(this, FILE_IS_ENCRYPTED_AND_SAVED_AS + outputPath);
                showFileInEditor(outputPath);
            }
        }
    }
    private void decryptFile() {
        String inputPath = chooseFile(SELECT_FILE_TO_DECRYPT);
        if (inputPath != null) {
            String suggestedSaveName = inputPath.replaceAll("\\.txt$", POSTFIX_DECRYPTION_FILE_NAME);
            String outputPath = chooseSaveLocation(SELECT_DIRECTORY_FOR_DECRYPTION_FILE, suggestedSaveName);
            int shift = getShift();
            if (outputPath != null && shift != -1) {
                Function.decrypt(inputPath, outputPath, shift);
                JOptionPane.showMessageDialog(this, FILE_IS_DECRYPTION_AND_SAVED_AS + outputPath);
                showFileInEditor(outputPath);
            }
        }
    }
    private void decryptBruteForce() {
        String inputPath = chooseFile(SELECT_FILE_TO_BRUTE_FORCE_DECRYPTION);
        if (inputPath != null) {
            String suggestedSaveName = inputPath.replaceAll("\\.txt$", POSTFIX_BRUTE_FORCE_DECRYPTION_FILE_NAME);
            String outputPath = chooseSaveLocation(SELECT_DIRECTORY_FOR_BRUTE_FORCE_DECRYPTION_FILE, suggestedSaveName);
            if (outputPath != null) {
                Function.decryptBruteForce(inputPath, outputPath, REGULAR_EXPRESSION);
                JOptionPane.showMessageDialog(this, FILE_IS_BRUTE_FORCE_DECRYPTION_AND_SAVED_AS + "\n"
                        + outputPath + "\n "   +  FOND_KEY +  TextHandler.getFoundKey()    +
                        "\n" + FILE_PROCESSING_TIME + (TextHandler.getFinalTime()) / 1_000_000.0 + SI_UNIT_MS);
                showFileInEditor(outputPath);
            }
        }
    }
    private void decryptAnalyzer() {
        String inputPath = chooseFile("Выберете зашифрованный файл");
        String inputPath2 = chooseFile("Выберете файл для анализа частотности символов");
        if (inputPath != null ) {
            String suggestedSaveName = inputPath.replaceAll("\\.txt$", POSTFIX_CRYPT_ANALYZER_DECRYPTION_FILE_NAME);
            String outputPath = chooseSaveLocation(SELECT_DIRECTORY_FOR_BRUTE_FORCE_DECRYPTION_FILE, suggestedSaveName);
            if (outputPath != null) {
                try {
                    Function.Crypt(inputPath, inputPath2, outputPath);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                JOptionPane.showMessageDialog(this, FILE_IS_BRUTE_FORCE_DECRYPTION_AND_SAVED_AS + "\n"
                        + outputPath + "\n "+ "\n" + FILE_PROCESSING_TIME + (TextDecryption.getFinalTime()) / 1_000_000.0 + SI_UNIT_MS);
                showFileInEditor(outputPath);
                try {
                    while (true) {
                        char fromChar = (char) getChar();
                        if (fromChar == '0') {
                            break;
                        }
                        char toChar = (char) getChar();
                        if (toChar == '0') {
                            break;
                        }
                        String encryptedText = FileHandler.readFile(outputPath);
                        String newReplaced = replaceCharsInText(encryptedText, fromChar, toChar);
                        FileHandler.writeFile(outputPath, newReplaced);
                        showFileInEditor(outputPath);
                    }
                } catch (IOException e) {
                    System.err.println("Error: " + e.getMessage());
                }
            }
        }
    }
    private String chooseFile(String dialogTitle) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle(dialogTitle);
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            return fileChooser.getSelectedFile().getAbsolutePath();
        }
        return null;
    }
    private String chooseSaveLocation(String dialogTitle, String defaultPath) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle(dialogTitle);
        if (defaultPath != null && !defaultPath.isEmpty()) {
            File defaultFile = new File(defaultPath);
            fileChooser.setSelectedFile(defaultFile);
        }
        int result = fileChooser.showSaveDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            return fileChooser.getSelectedFile().getAbsolutePath();
        }
        return null;
    }
    private int getShift() {
        String shiftString = JOptionPane.showInputDialog(this, SELECT_SHIFT_KEY);
        try {
            return Integer.parseInt(shiftString);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, ERROR_ENTER_AN_INTEGER);
            return -1;
        }
    }
    private int getChar() {
        String ch = JOptionPane.showInputDialog(this, SELECT_CHAR);
        try {
            return  ch.charAt(0);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, ERROR_ENTER_AN_INTEGER);
            return -1;
        }
    }
    private void showFileInEditor(String filePath) {
        int response = JOptionPane.showConfirmDialog(this, WANT_TO_OPEN_A_FILE, OPENING_A_FILE, JOptionPane.YES_NO_OPTION);
        if (response == JOptionPane.YES_OPTION) {
            try {
                Desktop.getDesktop().open(new File(filePath));
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, FILE_OPENING_ERROR + e.getMessage());
            }
        }
    }
}
