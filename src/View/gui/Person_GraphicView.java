package View.gui;

import Model.Factory;
import Model.SystemSIGEE;
import Model.domain.Person;
import Model.domain.UndergraduateStudent;
import View.Person_View;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.*;

public class Person_GraphicView extends JDialog implements Person_View {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JLabel labelName;
    private JLabel labelEmail;
    private JTextField textField1;
    private JTextField textField2;
    private JButton setNameButton;
    private JButton setEmailButton;
    private JButton eventsAttendedButton;
    private JButton getBondsButton;
    private JList peopleList;
    private JButton eventsOrganizedButton;
    private JLabel labelIdentifier;
    private SystemSIGEE model;

    public Person_GraphicView(Dialog owner) {
        super(owner);
        this.model = SystemSIGEE.getInstance();
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        peopleList.setSelectedIndex(0);

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

        DefaultListModel<Person> listModel = new DefaultListModel<>();

        for(Person person : model.getAcademics()){
            listModel.addElement(person);
        }

        peopleList.setModel(listModel);

        peopleList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if(!e.getValueIsAdjusting()){
                    handleSelected();
                }
            }
        });

        setNameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleSetName();
            }
        });

        setEmailButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleSetEmail();
            }
        });
        eventsAttendedButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Person person = (Person) peopleList.getSelectedValue();
                if (person == null) {JOptionPane.showMessageDialog(Person_GraphicView.this, "Por favor, selecione uma pessoa primeiro.", "Nenhuma Entidade Selecionada", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Factory.getConcreteFactory().newListView(model.getEventsofPerson(person.getIdentifier(), "participant"), "All events attended", Person_GraphicView.this).display();
            }
        });
        eventsOrganizedButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Person person = (Person) peopleList.getSelectedValue();
                if (person == null) {JOptionPane.showMessageDialog(Person_GraphicView.this, "Por favor, selecione uma pessoa primeiro.", "Nenhuma Entidade Selecionada", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Factory.getConcreteFactory().newListView(model.getEventsofPerson(person.getIdentifier(), "organizer"), "All events organized", Person_GraphicView.this).display();
            }
        });
        getBondsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Person person = (Person) peopleList.getSelectedValue();
                if (person == null) {JOptionPane.showMessageDialog(Person_GraphicView.this, "Por favor, selecione uma pessoa primeiro.", "Nenhuma Entidade Selecionada", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                Factory.getConcreteFactory().newListView(model.getBondsOfPerson(person.getIdentifier()), "All bonds of this person", Person_GraphicView.this).display();
            }
        });
    }

    private void onOK() {
        // add your code here
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    private void handleSelected(){
        Person person = (Person) peopleList.getSelectedValue();
        labelName.setText("Name: " + person.getName());
        labelEmail.setText("Email: " + person.getEmail());
        if(person instanceof UndergraduateStudent){
            labelIdentifier.setText("Enrollment: " + person.getIdentifier());
        }else {
            labelIdentifier.setText("SIAPE: " + person.getIdentifier());
        }
    }

    private void handleSetName(){
        try {
            Person selectedPerson = (Person) peopleList.getSelectedValue();
            selectedPerson.setName(textField1.getText());
            labelName.setText("Name: " + selectedPerson.getName());
        } catch (Exception e){
            JOptionPane.showMessageDialog(
                    Person_GraphicView.this,
                    "Invalid argument for name",
                    "Invalid argument",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void handleSetEmail(){
        try {
            Person selectedPerson = (Person) peopleList.getSelectedValue();
            selectedPerson.setEmail(textField2.getText());
            labelEmail.setText("Description: " + selectedPerson.getEmail());
        } catch (Exception e){
            JOptionPane.showMessageDialog(
                    Person_GraphicView.this,
                    "Invalid argument for description",
                    "Invalid argument",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    public void display() {
        this.pack();
        this.setLocationRelativeTo(getOwner());
        this.setVisible(true);
        this.dispose();
    }
}
