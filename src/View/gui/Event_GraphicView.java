package View.gui;

import Model.Factory;
import Model.SystemSIGEE;
import Model.domain.Event;
import View.Event_View;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Event_GraphicView extends JDialog implements Event_View {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JLabel labelName;
    private JLabel labelLocation;
    private JTextField textField1;
    private JTextField textField2;
    private JButton setNameButton;
    private JButton setDescriptionButton;
    private JButton getOrganizersButton;
    private JButton getParticipantsButton;
    private JList eventsList;
    private JLabel labelDate;
    private JTextField textField3;
    private JButton setDateButton;
    private JButton notifyObserversButton;
    private JLabel labelEntityOrganizer;
    private SystemSIGEE model;

    public Event_GraphicView(Dialog owner) {
        super(owner);
        this.model = SystemSIGEE.getInstance();
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        eventsList.setSelectedIndex(0);

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

        DefaultListModel<Event> listModel = new DefaultListModel<>();

        for(Event event : model.getAllEvents()){
            listModel.addElement(event);
        }

        eventsList.setModel(listModel);

        eventsList.addListSelectionListener(new ListSelectionListener() {
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

        setDescriptionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleSetLocation();
            }
        });

        setDateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleSetDate();
            }
        });
        notifyObserversButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Event event = (Event) eventsList.getSelectedValue();
                if (event == null) {JOptionPane.showMessageDialog(Event_GraphicView.this, "Por favor, selecione uma evento primeiro.", "Nenhum evento Selecionado", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                event.notifyObservers("Hi, this is your event...");
                JOptionPane.showMessageDialog(Event_GraphicView.this, "Email with information event sent to observers!", "Sent Successfully", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        getOrganizersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Event event = (Event) eventsList.getSelectedValue();
                if (event == null) {JOptionPane.showMessageDialog(Event_GraphicView.this, "Por favor, selecione uma evento primeiro.", "Nenhum evento Selecionado", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                Factory.getConcreteFactory().newListView(event.getOrganizers(), "All organizer of this event", Event_GraphicView.this).display();
            }
        });

        getParticipantsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Event event = (Event) eventsList.getSelectedValue();
                if (event == null) {JOptionPane.showMessageDialog(Event_GraphicView.this, "Por favor, selecione uma evento primeiro.", "Nenhum evento Selecionado", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                Factory.getConcreteFactory().newListView(event.getParticipants(), "All organizer of this event", Event_GraphicView.this).display();
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
        Event event = (Event) eventsList.getSelectedValue();
        labelName.setText("Name: " + event.getName());
        labelLocation.setText("Location: " + event.getLocation());
        labelDate.setText("Date: " + event.getDate());
        labelEntityOrganizer.setText("Organized by Entity: " + model.getEntityOfEvent(event.getName()));
    }

    private void handleSetName(){
        try {
            Event event = (Event) eventsList.getSelectedValue();
            event.setName(textField1.getText());
            labelName.setText("Name: " + event.getName());
        } catch (Exception e){
            JOptionPane.showMessageDialog(
                    Event_GraphicView.this,
                    "Invalid argument for name",
                    "Invalid argument",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void handleSetLocation(){
        try {
            Event event = (Event) eventsList.getSelectedValue();
            event.setLocation(textField2.getText());
            labelLocation.setText("Location: " + event.getLocation());
        } catch (Exception e){
            JOptionPane.showMessageDialog(
                    Event_GraphicView.this,
                    "Invalid argument for location",
                    "Invalid argument",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void handleSetDate(){
        try {
            Event event = (Event) eventsList.getSelectedValue();
            DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
            LocalDateTime novaData = LocalDateTime.parse(textField3.getText().trim(), formatador);
            event.setDate(novaData);
            labelDate.setText("Date: " + event.getDate());

        } catch (Exception e){
            JOptionPane.showMessageDialog(
                    Event_GraphicView.this,
                    "Invalid argument for date",
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
