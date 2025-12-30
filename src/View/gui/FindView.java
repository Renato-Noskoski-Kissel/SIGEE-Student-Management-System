package View.gui;

import Model.SystemSIGEE;
import Model.domain.*;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.*;
import Model.domain.Event;
import Model.domain.Person;
import View.Find_View;

public class FindView extends JDialog implements Find_View {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JList list1;
    private JTextField textField1;
    private JTextField textField2;
    private JLabel identifier1;
    private JLabel identifier2;
    private SystemSIGEE model;

    public FindView(Dialog owner) {
        super(owner);
        this.model = SystemSIGEE.getInstance();
        setContentPane(contentPane);
        setModal(true);
        list1.setSelectedIndex(0);
        identifier1.setText("Identifier (siape for Professor and enrollment for Undergraduate Student):");
        identifier2.setVisible(false);
        textField2.setVisible(false);
        getRootPane().setDefaultButton(buttonOK);

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

        list1.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if(!e.getValueIsAdjusting()){
                    handleSelected((String) list1.getSelectedValue());
                }
            }
        });
    }

    private void handleSelected(String selected){
        if(selected.equals("StudentEntity")){
            identifier1.setText("Entity Name:");
            identifier2.setVisible(false);
            textField2.setVisible(false);
        }

        else if(selected.equals("Event")){
            identifier1.setText("Event Name:");
            identifier2.setVisible(false);
            textField2.setVisible(false);
        }

        else if(selected.equals("Person")){
            identifier1.setText("Identifier (siape for Professor and enrollment for Undergraduate Student):");
            identifier2.setVisible(false);
            textField2.setVisible(false);
        }

        else if(selected.equals("Role")){
            identifier1.setText("Role Name:");
            identifier2.setVisible(false);
            textField2.setVisible(false);
        }

        else if(selected.equals("Bond")){
            identifier1.setText("Identifier (siape for Professor and enrollment for Undergraduate Student):");
            identifier2.setVisible(true);
            textField2.setVisible(true);
            identifier2.setText("Entity Name:");
        }
    }

    private void onOK() {
        String selected = (String)list1.getSelectedValue();
        boolean found = true;

        if(selected.equals("StudentEntity")) {
            StudentEntity entity = model.findEntityByName(textField1.getText());
            if (entity == null) {
                found = false;
            }
        }

        else if(selected.equals("Event")){
            Event event = model.findEventByName(textField1.getText());
            if (event == null) {
                found = false;
            }
        }

        else if(selected.equals("Person")){
            Person person = model.findPersonById(textField1.getText());
            if (person == null) {
                found = false;
            }
        }

        else if(selected.equals("Role")){
            Role role = model.findRoleByName(textField1.getText());
            if (role == null) {
                found = false;
            }
        }

        else if(selected.equals("Bond")){
            Bond bond = model.getBondOfPerson(textField2.getText(), textField1.getText());
            if(bond != null){
                JOptionPane.showMessageDialog(
                        FindView.this,
                        "This Bond Exists, the role is: " + bond.getRole(),
                        "Found",
                        JOptionPane.INFORMATION_MESSAGE
                );
            }
            else{
                found = false;
            }

        }
    if(found) {
        JOptionPane.showMessageDialog(FindView.this, "This object exists, for more, acess the buttons on the first page", "Found", JOptionPane.INFORMATION_MESSAGE);
    }else{
        JOptionPane.showMessageDialog(
                FindView.this,
                "This identifier is not valid.",
                "Not Found",
                JOptionPane.ERROR_MESSAGE
        );
    }
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public void display() {
        this.pack();
        this.setLocationRelativeTo(getOwner());
        this.setVisible(true);
        this.dispose();
    }
}
