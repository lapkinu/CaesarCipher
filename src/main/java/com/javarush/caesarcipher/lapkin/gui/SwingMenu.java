package com.javarush.caesarcipher.lapkin.gui;
import com.javarush.caesarcipher.lapkin.function.Function;
import com.javarush.caesarcipher.lapkin.handler.TextHandler;
import static com.javarush.caesarcipher.lapkin.constans.ApplicationsConstants.*;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class SwingMenu extends JFrame {
    public SwingMenu() {
        setTitle("Шифровальщик");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 1, 10, 10));

        JButton encryptButton = new JButton("Шифровать файл");
        JButton decryptButton = new JButton("Расшифровать файл");
        JButton bruteForceButton = new JButton("Brute force расшифровка");
        JButton exitButton = new JButton("Выход");

        encryptButton.addActionListener(e -> encryptFile());
        decryptButton.addActionListener(e -> decryptFile());
        bruteForceButton.addActionListener(e -> decryptBruteForce());
        exitButton.addActionListener(e -> System.exit(0));

        panel.add(encryptButton);
        panel.add(decryptButton);
        panel.add(bruteForceButton);
        panel.add(exitButton);

        add(panel);
        setVisible(true);
    }

    private void encryptFile() {
        String inputPath = chooseFile("Выберите файл для шифрования");
        if (inputPath != null) {
            String suggestedSaveName = inputPath.replaceAll("\\.txt$", "_encrypted.txt");
            String outputPath = chooseSaveLocation("Выберите куда сохранить зашифрованный файл", suggestedSaveName);
            int shift = getShift();
            if (outputPath != null && shift != -1) {
                // Здесь вызов метода для шифрования файла
                Function.encrypt(inputPath, outputPath, shift);

                JOptionPane.showMessageDialog(this, "Файл зашифрован и сохранен как: " + outputPath);
                showFileInEditor(outputPath);
            }
        }
    }

    private void decryptFile() {
        String inputPath = chooseFile("Выберите файл для расшифровки");
        if (inputPath != null) {
            String suggestedSaveName = inputPath.replaceAll("\\.txt$", "_decrypted.txt");
            String outputPath = chooseSaveLocation("Выберите куда сохранить расшифрованный файл", suggestedSaveName);
            int shift = getShift();
            if (outputPath != null && shift != -1) {
                // Здесь вызов метода для расшифровки файла
                Function.decrypt(inputPath, outputPath, shift);

                JOptionPane.showMessageDialog(this, "Файл расшифрован и сохранен как: " + outputPath);
                showFileInEditor(outputPath);
            }
        }
    }

    private void decryptBruteForce() {
        String inputPath = chooseFile("Выберите файл для brute force расшифровки");
        if (inputPath != null) {
            String suggestedSaveName = inputPath.replaceAll("\\.txt$", "_bruteforced.txt");
            String outputPath = chooseSaveLocation("Выберите куда сохранить результат brute force", suggestedSaveName);
            if (outputPath != null) {
                // Здесь вызов метода brute force расшифровки
                Function.decryptBruteForce(inputPath, outputPath, REGULAR_EXPRESSION);
                JOptionPane.showMessageDialog(this, "Brute force расшифровка выполнена и результат сохранен как:" + "\n"
                        + outputPath + "\n \n" + "                   Время обрабртки файла: " + (TextHandler.getFinalTime()) / 1_000_000.0 + " ms");
                showFileInEditor(outputPath);
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
            fileChooser.setSelectedFile(defaultFile);  // Устанавливаем предлагаемый файл
        }
        int result = fileChooser.showSaveDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            return fileChooser.getSelectedFile().getAbsolutePath();
        }
        return null;
    }

    private int getShift() {
        String shiftString = JOptionPane.showInputDialog(this, "Введите сдвиг для шифрования/расшифровки:");
        try {
            return Integer.parseInt(shiftString);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Ошибка: необходимо ввести целое число.");
            return -1;
        }
    }

    private void showFileInEditor(String filePath) {
        int response = JOptionPane.showConfirmDialog(this, "Хотите открыть файл?", "Открытие файла", JOptionPane.YES_NO_OPTION);
        if (response == JOptionPane.YES_OPTION) {
            try {
                Desktop.getDesktop().open(new File(filePath));
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Не удалось открыть файл: " + e.getMessage());
            }
        }
    }
}
