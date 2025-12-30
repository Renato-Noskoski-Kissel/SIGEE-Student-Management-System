package View.gui;

import View.List_View;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class ListView extends JDialog implements List_View {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JList listComponent;
    private JLabel titleMessage;
    private JScrollBar scrollBar1;

    public ListView(List<?> array, String title, Dialog owner) {
        super(owner);
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        titleMessage.setText(title);
        populateList(array);


        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK() {
        dispose();
    }

    private void onCancel() {
        dispose();
    }

    private void populateList(List<?> list) {
        DefaultListModel<Object> listModel = new DefaultListModel<>();

        for (Object item : list) {
            listModel.addElement(item);
        }

        listComponent.setModel(listModel);
    }

    @Override
    public void display() {
        this.pack();
        this.setLocationRelativeTo(getOwner());
        this.setVisible(true);
        // O código pausa aqui
        this.dispose();
    }
}
