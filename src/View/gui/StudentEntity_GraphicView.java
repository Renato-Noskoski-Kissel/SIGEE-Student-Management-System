package View.gui;

import Model.Factory;
import Model.SystemSIGEE;
import Model.domain.StudentEntity;
import View.StudentEntity_View;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.*;

public class StudentEntity_GraphicView extends JDialog implements StudentEntity_View {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField textField1;
    private JTextField textField2;
    private JButton setNameButton;
    private JButton setDescriptionButton;
    private JButton getEventsButton;
    private JList entitiesList;
    private JLabel labelName;
    private JLabel labelDescription;
    private JButton getBondsButton;
    private SystemSIGEE model;

    public StudentEntity_GraphicView(Dialog owner) {
        super(owner);
        this.model = SystemSIGEE.getInstance();
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        entitiesList.setSelectedIndex(0);

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

        DefaultListModel<StudentEntity> listModel = new DefaultListModel<>();

        for(StudentEntity entity : model.getEntities()){
            listModel.addElement(entity);
        }

        entitiesList.setModel(listModel);

        entitiesList.addListSelectionListener(new ListSelectionListener() {
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
                handleSetDescripton();
            }
        });

        getEventsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StudentEntity entity = (StudentEntity) entitiesList.getSelectedValue();

                if (entity == null) {
                    JOptionPane.showMessageDialog(StudentEntity_GraphicView.this,
                            "Por favor, selecione uma entidade primeiro.",
                            "Nenhuma Entidade Selecionada",
                            JOptionPane.ERROR_MESSAGE);
                    return; // Sai do método
                }

                Factory.getConcreteFactory().newListView(entity.getEvents(), "All events of " + entitiesList.getSelectedValue(), StudentEntity_GraphicView.this).display();
            }
        });

        getBondsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StudentEntity entity = (StudentEntity) entitiesList.getSelectedValue();

                if (entity == null) {
                    JOptionPane.showMessageDialog(StudentEntity_GraphicView.this,
                            "Por favor, selecione uma entidade primeiro.",
                            "Nenhuma Entidade Selecionada",
                            JOptionPane.ERROR_MESSAGE);
                    return; // Sai do método
                }

                Factory.getConcreteFactory().newListView(model.getBondsOfEntity(entity.getName()), "All bond of " + entitiesList.getSelectedValue(), StudentEntity_GraphicView.this).display();
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
        StudentEntity entity = (StudentEntity) entitiesList.getSelectedValue();
        labelName.setText("Name: " + entity.getName());
        labelDescription.setText("Description: " + entity.getDescription());
    }

    private void handleSetName(){
        try {
            StudentEntity selectedEntity = (StudentEntity) entitiesList.getSelectedValue();
            selectedEntity.setName(textField1.getText());
            labelName.setText("Name: " + selectedEntity.getName());
        } catch (Exception e){
            JOptionPane.showMessageDialog(
                    StudentEntity_GraphicView.this,
                    "Invalid argument for name",
                    "Invalid argument",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void handleSetDescripton(){
        try {
            StudentEntity selectedEntity = (StudentEntity) entitiesList.getSelectedValue();
            selectedEntity.setDescription(textField2.getText());
            labelDescription.setText("Description: " + selectedEntity.getDescription());
        } catch (Exception e){
            JOptionPane.showMessageDialog(
                    StudentEntity_GraphicView.this,
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
        this.dispose(); // Limpa a janela da memória
    }
}
