package com.javarush.caesarcipher.lapkin;

import com.javarush.caesarcipher.lapkin.gui.SwingMenu;

import javax.swing.*;
public class Entrypoint {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(SwingMenu::new);
    }
}