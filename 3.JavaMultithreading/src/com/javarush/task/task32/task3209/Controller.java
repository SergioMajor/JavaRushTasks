package com.javarush.task.task32.task3209;

import com.javarush.task.task32.task3209.listeners.UndoListener;

import javax.swing.*;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import java.io.*;
import java.nio.file.Files;

public class Controller {

    private View view;
    private HTMLDocument document;
    private File currentFile;

    public Controller(View view) {
        this.view = view;
    }

    public HTMLDocument getDocument() {
        return document;
    }

    public static void main(String[] args) {
        View view = new View();

        Controller controller = new Controller(view);

        view.setController(controller);

        view.init();
        controller.init();
    }

    public void init() {
        createNewDocument();
    }

    public void exit() {
        System.exit(0);
    }

    public void resetDocument() {
        UndoListener listener = view.getUndoListener();

        if (document != null)
            document.removeUndoableEditListener(listener);

        document = (HTMLDocument) new HTMLEditorKit().createDefaultDocument();
        document.addUndoableEditListener(listener);

        view.update();
    }

    public void setPlainText(String text) {
        try {
            resetDocument();

            StringReader reader = new StringReader(text);

            new HTMLEditorKit().read(reader, document, 0);

        } catch(Exception e) {
            ExceptionHandler.log(e);
        }
    }

    public String getPlainText() {
        StringWriter stringWriter = new StringWriter();

        try {
            new HTMLEditorKit().write(stringWriter, document, 0, document.getLength());
        } catch (Exception e) {
            ExceptionHandler.log(e);
        }

        return stringWriter.toString();
    }

    public void createNewDocument() {

        view.selectHtmlTab();
        resetDocument();
        view.setTitle("HTML редактор");
        view.resetUndo();
        currentFile = null;
    }

    public void saveDocumentAs() {
        view.selectHtmlTab();
        JFileChooser jFileChooser = new JFileChooser();
        jFileChooser.setFileFilter(new HTMLFileFilter());
        jFileChooser.setDialogTitle("Save File");

        int choose = jFileChooser.showSaveDialog(view);
        if (choose == JFileChooser.APPROVE_OPTION) {
            currentFile = jFileChooser.getSelectedFile();
            view.setTitle(currentFile.getName());
            try {
                FileWriter fileWriter = new FileWriter(currentFile);
                new HTMLEditorKit().write(fileWriter, document, 0, document.getLength());
            } catch (Exception e) {
                ExceptionHandler.log(e);
            }
        }
    }

    public void saveDocument() {
        if (currentFile != null) {
            view.selectHtmlTab();

            try {
                FileWriter fileWriter = new FileWriter(currentFile);
                new HTMLEditorKit().write(fileWriter, document, 0, document.getLength());
            } catch (Exception e) {
                ExceptionHandler.log(e);
            }
        }
        else {
            saveDocumentAs();
        }
    }

    public void openDocument() {
        view.selectHtmlTab();

        JFileChooser jFileChooser = new JFileChooser();
        jFileChooser.setFileFilter(new HTMLFileFilter());
        jFileChooser.setDialogTitle("Open File");

        int choose = jFileChooser.showOpenDialog(view);

        if (choose == JFileChooser.APPROVE_OPTION) {
            resetDocument();

            currentFile = jFileChooser.getSelectedFile();
            view.setTitle(currentFile.getName());

            try {
                FileReader fileReader = new FileReader(currentFile);
                new HTMLEditorKit().read(fileReader, document, 0);
                view.resetUndo();
            } catch (Exception e) {
                ExceptionHandler.log(e);
            }
        }
    }
}
