package View.gui;

import Model.SystemSIGEE;
import View.CreateObject_View;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.*;

public class CreateObjectView extends JDialog implements CreateObject_View {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JList<String> list1;
    private JTextField textField1;
    private JTextField textField2;
    private JLabel identifier2;
    private JLabel identifier1;
    private JTextField textField3;
    private JLabel identifier3;

    private SystemSIGEE model;

    public CreateObjectView(Dialog owner) {
        super(owner);
        this.model = SystemSIGEE.getInstance();

        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        setTitle("Create New Object");

        populateList();
        list1.setSelectedIndex(0);

        // --- 2. Configura o estado inicial da UI ---
        handleSelectionChanged();


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

        list1.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    handleSelectionChanged();
                }
            }
        });

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }


    private void populateList() {
        DefaultListModel<String> listModel = new DefaultListModel<>();
        listModel.addElement("Undergraduate Student");
        listModel.addElement("Professor");
        listModel.addElement("StudentEntity");
        listModel.addElement("Role");
        listModel.addElement("Event");
        listModel.addElement("Bond");
        list1.setModel(listModel);
    }


    private void handleSelectionChanged() {
        String selected = list1.getSelectedValue();
        if (selected == null) return;

        identifier1.setVisible(true); textField1.setVisible(true);
        identifier2.setVisible(true); textField2.setVisible(true);
        identifier3.setVisible(true); textField3.setVisible(true);

        switch (selected) {
            case "Undergraduate Student":
                identifier1.setText("Name:");
                identifier2.setText("Email (ex: name@ufsc.com):");
                identifier3.setText("Enrollment (8 digits):");
                break;
            case "Professor":
                identifier1.setText("Name:");
                identifier2.setText("Email (ex: name@ufsc.com):");
                identifier3.setText("SIAPE (8 digits):");
                break;
            case "StudentEntity":
                identifier1.setText("Name:");
                identifier2.setText("Description:");
                // Esconde o campo 3
                identifier3.setVisible(false);
                textField3.setVisible(false);
                break;
            case "Role":
                identifier1.setText("Name:");
                identifier2.setText("Description:");
                identifier3.setText("Payment (ex: 1200,50):");
                break;
            case "Bond":
                identifier1.setText("Person ID (Enrollment/SIAPE):");
                identifier2.setText("Entity Name:");
                identifier3.setText("Role Name:");
                break;
            case "Event":
                identifier1.setText("Entity Name (Owner):");
                identifier2.setText("Event Name:");
                identifier3.setText("Location:");
                break;
        }

        pack();
    }


    private void onOK() {
        String selected = list1.getSelectedValue();
        if (selected == null) return;

        boolean success = false;
        String errorMessage = "Invalid data. Check all fields.";

        try {
            switch (selected) {
                case "Undergraduate Student":
                    success = model.registerUndergraduateStudent(
                            textField1.getText(),
                            textField2.getText(),
                            textField3.getText()
                    );
                    break;
                case "Professor":
                    success = model.registerProfessor(
                            textField1.getText(),
                            textField2.getText(),
                            textField3.getText()
                    );
                    break;
                case "StudentEntity":
                    success = model.createEntity(
                            textField1.getText(),
                            textField2.getText()
                    );
                    break;
                case "Role":
                    // Tenta converter o pagamento para double
                    double payment = Double.parseDouble(textField3.getText());
                    success = model.createRole(
                            textField1.getText(),
                            textField2.getText(),
                            payment
                    );
                    break;
                case "Bond":
                    success = model.createBond(
                            textField1.getText(),
                            textField2.getText(),
                            textField3.getText()
                    );
                    break;
                case "Event":
                    java.time.LocalDateTime defaultDate = java.time.LocalDateTime.now();

                    success = model.createEvent(
                            textField1.getText(),
                            textField2.getText(),
                            textField3.getText(),
                            defaultDate
                    );
                    break;
            }
        } catch (NumberFormatException e) {
            errorMessage = "Invalid format for Payment. Use numbers (e.g., 1200.50).";
            success = false;
        } catch (IllegalArgumentException e) {
            errorMessage = "Invalid data: " + e.getMessage(); // Erro vindo do modelo
            success = false;
        } catch (Exception e) {
            // Pega outros erros (ex: ID não encontrado na criação do Bond)
            errorMessage = "Could not create object. Ensure all IDs/Names are correct.";
            success = false;
        }

        if (success) {
            JOptionPane.showMessageDialog(this,
                    selected + " created successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);
            dispose(); // Fecha a janela
        } else {
            JOptionPane.showMessageDialog(this,
                    "Error creating " + selected + ".\n" + errorMessage,
                    "Creation Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public void display() {
        this.pack();
        this.setLocationRelativeTo(getOwner());
        this.setVisible(true);
        this.dispose();
    }

    private void onCancel() {
        dispose();
    }
}