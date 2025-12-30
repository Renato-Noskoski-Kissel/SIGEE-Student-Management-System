package View.gui;

import Model.Factory;
import Model.SystemSIGEE;
import Model.domain.*;
import Model.domain.Event;
import Model.factory.GraphicConcreteFactory;
import Model.factory.IAbstractFactory;
import View.System_View;
import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class System_GraphicView extends JDialog implements System_View {
    private final SystemSIGEE model;
    private JPanel contentPane;
    private JButton entitiesButton;
    private JButton peopleButton;
    private JButton eventsButton;
    private JPanel treePanel;
    private JLabel specificity1;
    private JLabel specificity2;
    private JLabel nameSpecificity;
    private JLabel moreInformation;
    private JButton whantToKnowAboutButton;
    private JButton creatAnObjectButton;
    private JButton whantToDeleteAnButton;
    private JButton saveDataButton;
    private JButton loadDataButton;
    private JButton buttonOK;
    private JTree tree;

    public System_GraphicView(SystemSIGEE model) {
        this.model = model;
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        setupTree();
        setTitle("SIGEE");

        tree.addTreeSelectionListener(new TreeSelectionListener() {
            @Override
            public void valueChanged(TreeSelectionEvent e) {
                DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();

                if (selectedNode == null) {
                    handleSelectionListener(null);
                    return;
                }

                Object userObject = selectedNode.getUserObject();
                handleSelectionListener(userObject);
            }
        });

        whantToKnowAboutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Factory.getConcreteFactory().newFindView(System_GraphicView.this, null).display();
            }
        });

        entitiesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Factory.getConcreteFactory().newStudentEntity_View(System_GraphicView.this, null).display();
            }
        });

        peopleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Factory.getConcreteFactory().newPerson_View(System_GraphicView.this, null).display();
            }
        });

        eventsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Factory.getConcreteFactory().newEvent_View(System_GraphicView.this, null).display();
            }
        });

        whantToDeleteAnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleDeleteSelected(); // Chama o método de exclusão
            }
        });

        creatAnObjectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Factory.getConcreteFactory().newCreateObjectView(System_GraphicView.this, null).display();
                refreshTree();
            }
        });

        saveDataButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    model.saveData();
                    JOptionPane.showMessageDialog(System_GraphicView.this, "SAVED", "SAVED", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(System_GraphicView.this, "Error saving, try again", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        loadDataButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    model.loadData();
                    refreshTree();
                    JOptionPane.showMessageDialog(System_GraphicView.this, "LOADED", "LOADED", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(System_GraphicView.this, "Error loading, try again", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private DefaultMutableTreeNode buildTreeModel() {

        DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode("UFSC");

        DefaultMutableTreeNode personNode = new DefaultMutableTreeNode("People");
        DefaultMutableTreeNode entityNode = new DefaultMutableTreeNode("Entities");
        DefaultMutableTreeNode rolesNode = new DefaultMutableTreeNode("Roles");

        rootNode.add(entityNode);
        rootNode.add(personNode);
        rootNode.add(rolesNode);

        for (Person p : model.getAcademics()) {
            personNode.add(new DefaultMutableTreeNode(p));
        }

        for (Role r : model.getRoles()) {
            rolesNode.add(new DefaultMutableTreeNode(r));
        }

        for (StudentEntity se : model.getEntities()) {

            DefaultMutableTreeNode seNode = new DefaultMutableTreeNode(se);

            DefaultMutableTreeNode bondsNode = new DefaultMutableTreeNode("Bonds");
            DefaultMutableTreeNode eventsNode = new DefaultMutableTreeNode("Events");

            for (Bond b : model.getBondsOfEntity(se.getName())) {
                bondsNode.add(new DefaultMutableTreeNode(b));
            }

            for (Event e : se.getEvents()) {
                eventsNode.add(new DefaultMutableTreeNode(e));
            }

            if (bondsNode.getChildCount() > 0) {
                seNode.add(bondsNode);
            }
            if (eventsNode.getChildCount() > 0) {
                seNode.add(eventsNode);
            }

            entityNode.add(seNode);
        }

        return rootNode;

    }

    private void setupTree() {
        DefaultMutableTreeNode rootNode = buildTreeModel();

        this.tree = new JTree(rootNode);
        this.treePanel.setLayout(new BorderLayout());
        this.treePanel.add(new JScrollPane(this.tree), BorderLayout.CENTER);

        tree.addTreeSelectionListener(new TreeSelectionListener() {
            @Override
            public void valueChanged(TreeSelectionEvent e) {
                DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
                if (selectedNode == null) {
                    handleSelectionListener(null);
                    return;
                }
                Object userObject = selectedNode.getUserObject();
                handleSelectionListener(userObject);
            }
        });
    }

    private void refreshTree() {
        DefaultMutableTreeNode newRoot = buildTreeModel();
        this.tree.setModel(new DefaultTreeModel(newRoot));
    }

    private void handleSelectionListener(Object selected){

        if(selected == null){
            return;
        }
        specificity2.setVisible(true);

        if(selected instanceof StudentEntity){
            StudentEntity handling = (StudentEntity) selected;
            nameSpecificity.setText("Name: " + handling.getName());
            specificity1.setText("Description: " + handling.getDescription());
            specificity2.setVisible(false);
        }

        else if (selected instanceof Event) {
            Event handling = (Event) selected;
            nameSpecificity.setText("Name: " + handling.getName());
            specificity1.setText("Location: " + handling.getLocation());
            specificity2.setText("Date: " + handling.getDate());
        }

        else if (selected instanceof Person) {
            Person handling = (Person) selected;
            nameSpecificity.setText("Name: " + handling.getName());
            specificity1.setText("Email: " + handling.getEmail());
            if(handling instanceof UndergraduateStudent){
                specificity2.setText("Enrollment: " + handling.getIdentifier());
            }else {
                specificity2.setText("SIAPE: " + handling.getIdentifier());
            }
        }

        else if (selected instanceof Role) {
            Role handling = (Role) selected;
            nameSpecificity.setText("Name: " + handling.getName());
            specificity1.setText("Description: " + handling.getDescription());
            specificity2.setText("Payment: R$ " + handling.getPayment());
        }

        else if (selected instanceof Bond) {
            Bond handling = (Bond) selected;

            nameSpecificity.setText("Vínculo: " + handling.toString());
            specificity1.setText("Pessoa: " + handling.getPerson().getName());
            specificity2.setText("Cargo: " + handling.getRole().getName());
        }

        else {
            nameSpecificity.setText("");
            specificity1.setText("");
            specificity2.setText("");
            specificity2.setVisible(false);
        }
    }

    // In System_GraphicView.java

    // Em System_GraphicView.java
    private void handleDeleteSelected() {
        DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();

        if (selectedNode == null || selectedNode.isRoot()) {
            JOptionPane.showMessageDialog(this,
                    "Please select an item in the tree to delete.",
                    "No item selected",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        Object selectedObject = selectedNode.getUserObject();

        if (selectedObject instanceof String) {
            JOptionPane.showMessageDialog(this,
                    "You cannot delete a category folder.",
                    "Deletion Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        int choice = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to delete: " + selectedObject.toString() + "?\nThis action cannot be undone.",
                "Confirm Deletion",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.ERROR_MESSAGE);

        if (choice != JOptionPane.YES_OPTION) {
            return;
        }

        boolean success = false;

        if (selectedObject instanceof Person) {
            success = model.deletePerson(((Person) selectedObject).getIdentifier());
        }

        else if (selectedObject instanceof StudentEntity) {
            success = model.deleteEntity(((StudentEntity) selectedObject).getName());
        }

        else if (selectedObject instanceof Role) {
            success = model.deleteRole(((Role) selectedObject).getName());
        }

        else if (selectedObject instanceof Event) {
            Event eventToDelete = (Event) selectedObject;
            String parentEntityName = null;

            for (StudentEntity entity : model.getEntities()) {
                if (entity.getEvents().contains(eventToDelete)) {
                    parentEntityName = entity.getName();
                    break;
                }
            }
            if (parentEntityName != null) {
                success = model.deleteEvent(parentEntityName, eventToDelete.getName());
            }

        }

        else if (selectedObject instanceof Bond) {
            Bond b = (Bond) selectedObject;
            success = model.inactivateBond(b.getPerson().getIdentifier(),
                    b.getEntity().getName(),
                    b.getRole().getName());
        }


        if (success) {
            JOptionPane.showMessageDialog(this,
                    "Item successfully deleted/inactivated!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);

            DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
            model.removeNodeFromParent(selectedNode);

        } else {
            JOptionPane.showMessageDialog(this,
                    "Could not delete the item.\nIt might be in use by another object or was not found.",
                    "Deletion Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public void display() {
        this.pack();
        this.setLocationRelativeTo(getOwner());
        this.setVisible(true);
        this.dispose();
    }


    public static void main(String[] args) {
        IAbstractFactory factory = new GraphicConcreteFactory();
        Model.dataSeeding.popularDados();
        System_GraphicView dialog = new System_GraphicView(SystemSIGEE.getInstance());
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
