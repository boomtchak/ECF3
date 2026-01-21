package fr.cda.java.vue;

import fr.cda.java.gestionErreurs.Exceptions.MandatoryDataException;
import fr.cda.java.gestionErreurs.Exceptions.RegexException;
import fr.cda.java.model.gestion.Client;
import fr.cda.java.model.gestion.Contrat;
import fr.cda.java.utilitaire.TypeAction;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

public class GestionContrats extends JDialog {

    private JPanel contentPane;
    private JPanel blocFormulaire;
    private JPanel erreur;
    private JTable tableauContrats;
    private JTextField idTextField;
    private JTextField raisonSocialeTextField;
    private JTextField nomCtTextField;
    private JTextField montantCtTextField;
    ArrayList<JComponent> listeChampsFormulaire = new ArrayList<JComponent>();
    private JButton afficherButton;
    private JButton supprimerButton;
    private JButton modifierButton;
    private JButton buttonCancel;
    private JButton sauvegarderButton;
    private JLabel erreurLabel;
    private Client client;
    private Contrat selectedContrat;
    private boolean isAffichage = true;
    private TypeAction typeAction;

    public GestionContrats(Client client, TypeAction typeAction) {

        this.client = client;
        isAffichage = typeAction.equals(TypeAction.AFFICHER);
        this.setTitle("Gestion des contrats");
        //blocFormulaire.setVisible(!isAffichage);
        modifierButton.setVisible(!isAffichage);
        sauvegarderButton.setVisible(!isAffichage);
        enableFormulaire(isAffichage);
        remplissageJTable();
        remplirFormulaire();
        enableButtons(false);
        setContentPane(contentPane);
        setModal(true);
        tableauContrats.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        nomCtTextField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                modification(e);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                modification(e);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                modification(e);
            }

            private void modification(DocumentEvent e) {
                if (!nomCtTextField.getText().equals(selectedContrat.getNomContrat())) {
                    sauvegarderButton.setEnabled(!isAffichage);
                }
            }
        });
        montantCtTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent e) {
                char c = e.getKeyChar();
                // Si ce n'est pas un chiffre, pas un point, et pas un 'backspace'
                if (!(Character.isDigit(c) || (c == java.awt.event.KeyEvent.VK_BACK_SPACE) || (c
                        == '.'))) {
                    e.consume();
                }
                // Empêche d'avoir deux points
                if (c == '.' && montantCtTextField.getText().contains(".")) {
                    e.consume();
                }
            }
        });
        montantCtTextField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                modification(e);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                modification(e);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                modification(e);
            }

            private void modification(DocumentEvent e) {
                try {
                    if (Double.valueOf(montantCtTextField.getText())
                            != selectedContrat.getMontantContrat()) {
                        sauvegarderButton.setEnabled(!isAffichage);
                    }
                } catch (NumberFormatException ex) {
                    SwingUtilities.invokeLater(() -> {
                        if (selectedContrat != null) {
                            montantCtTextField.setText(
                                    String.valueOf(selectedContrat.getMontantContrat()));
                        } else {
                            montantCtTextField.setText("0");
                        }
                        // On sélectionne tout pour que l'utilisateur puisse retaper
                        montantCtTextField.selectAll();
                    });
                }
            }
        });
        sauvegarderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                afficherErreur("");
                try {
                    if (selectedContrat != null) {

                        selectedContrat.setNomContrat(nomCtTextField.getText());
                        selectedContrat.setMontantContrat(
                                Double.valueOf(montantCtTextField.getText()));
                        client.getListeContrats()
                                .put(selectedContrat.getIdentifiant(), selectedContrat);
                    } else {
                        Contrat contrat = new Contrat(client.getIdentifiant(),
                                nomCtTextField.getText(),
                                Double.valueOf(montantCtTextField.getText()));
                        client.getListeContrats().put(contrat.getIdentifiant(), contrat);

                    }
                    remplissageJTable();
                    enableButtons(false);
                } catch (NumberFormatException ex) {
                    afficherErreur("Format invalide. Veuillez saisir un nombre (ex: 15.50)");
                    montantCtTextField.requestFocus();
                } catch (MandatoryDataException ex) {
                    afficherErreur(ex.getMessage());
                } catch (RegexException ex) {
                    afficherErreur(ex.getMessage());
                }
            }
        });
        supprimerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                client.getListeContrats().remove(selectedContrat.getIdentifiant());
                selectedContrat = null;
                enableButtons(false);
                remplissageJTable();

            }
        });
        tableauContrats.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int selectedRow = tableauContrats.getSelectedRow();

                    // Vérification si une ligne est réellement sélectionnée
                    if (selectedRow != -1) {
                        // Conversion de l'index pour gérer le tri/filtre éventuel
                        int modelRow = tableauContrats.convertRowIndexToModel(selectedRow);

                        // Récupération sécurisée de l'objet
                        // On récupère l'ID caché en colonne 0 pour retrouver l'objet dans la liste
                        int indexInList = (int) tableauContrats.getModel().getValueAt(modelRow, 0);

                        selectedContrat = client.getListeContrats().get(indexInList);
                        supprimerButton.setEnabled(!isAffichage);
                        modifierButton.setEnabled(!isAffichage);
                        afficherButton.setEnabled(true);
                    }
                }
            }
        });
        afficherButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                remplirFormulaire();
                enableFormulaire(false);
                sauvegarderButton.setEnabled(false);
            }
        });
        modifierButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                remplirFormulaire();
                enableFormulaire(!isAffichage);
                sauvegarderButton.setEnabled(!isAffichage);
            }
        });
        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    private Contrat getSelectedContrat() {
        int viewRow = tableauContrats.getSelectedRow();
        Contrat contrat = client.getListeContrats().get(viewRow);
        return contrat;
    }

    private void remplissageJTable() {
        DefaultTableModel modelTable = null;
        // 2. Définir les en-têtes
        List<String> entetes = new ArrayList<>();
        entetes.add("Identifiant");
        entetes.add("Client");
        entetes.add("Nom");
        entetes.add("Montant");

        // 3. Initialiser le DefaultTableModel (avec 0 ligne au départ)
        modelTable = new DefaultTableModel(new Object[][]{}, entetes.toArray());

        // 4. Parcourir la liste de clients et ajouter chaque ligne
        for (Contrat contrat : client.getListeContrats().values()) {
            Object[] dataRow = new Object[]{
                    contrat.getIdentifiant(),
                    client.getRaisonSociale(),
                    contrat.getNomContrat(),
                    contrat.getMontantContrat()
            };
            modelTable.addRow(dataRow);
        }

        // Empêche la sélection des lignes
        tableauContrats.setRowSelectionAllowed(!isAffichage);

// empêche aussi la sélection par colonne
        tableauContrats.setColumnSelectionAllowed(false);

        // 5. Appliquer le modèle à la table
        tableauContrats.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tableauContrats.setModel(modelTable);
    }


    /**
     * permet de binder le contenu de mon objet avec les textfields (synchronizeViewToModel)
     */
    private void remplirFormulaire() {

        if (selectedContrat != null) {
            idTextField.setText(String.valueOf(selectedContrat.getIdentifiant()));
            raisonSocialeTextField.setText(client.getRaisonSociale());
            nomCtTextField.setText(selectedContrat.getNomContrat());
            montantCtTextField.setText(
                    String.valueOf(selectedContrat.getMontantContrat()));
        }
        if (!isAffichage) {
            idTextField.setText(String.valueOf(Contrat.compteurIdentifiant));
        }
    }

    /**
     *
     */
    private void enableFormulaire(boolean desactive) {
        for (JComponent component : listeChampsFormulaire) {
            component.setEnabled(desactive);
        }
        idTextField.setEnabled(false);
        raisonSocialeTextField.setEnabled(false);
        nomCtTextField.setEnabled(desactive);
        montantCtTextField.setEnabled(desactive);
    }

    private void enableButtons(boolean desactive) {
        afficherButton.setEnabled(desactive);
        supprimerButton.setEnabled(desactive);
        sauvegarderButton.setEnabled(desactive);
        modifierButton.setEnabled(desactive);
    }

    void afficherErreur(String message) {
        erreur.setVisible(!message.isEmpty());
        erreurLabel.setText(message);
    }


}
