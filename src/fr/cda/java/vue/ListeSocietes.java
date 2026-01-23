package fr.cda.java.vue;

import fr.cda.java.gestionErreurs.Exceptions.donneeException;
import fr.cda.java.gestionErreurs.Logger.AppLogger;
import fr.cda.java.model.gestion.Client;
import fr.cda.java.model.gestion.Prospect;
import fr.cda.java.model.gestion.Societe;
import fr.cda.java.utilitaire.TypeSociete;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.table.DefaultTableModel;

public class ListeSocietes extends JDialog {

    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTable tableau;
    private TypeSociete typeSociete;
    List<Societe> listeSociete;


    public ListeSocietes(TypeSociete typeSociete, List<Societe> liste) {
        this.typeSociete = typeSociete;
        this.listeSociete = liste;
        this.setTitle(new StringBuilder("Liste des ").append(typeSociete.toString()).toString());
        remplissageJTable();
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        redimensionner();
        this.pack();

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
                                           }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
                JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void remplissageJTable() {
        DefaultTableModel modelTable = null;
        // 2. Définir les en-têtes
        List<String> entetes = new ArrayList<>();
        entetes.add("identifiant");
        entetes.add("raisonSociale");
        entetes.add("adresse");
        entetes.add("telephone");
        entetes.add("adresseMail");

        if (typeSociete.equals(TypeSociete.CLIENT)) {

            entetes.add("Chiffre d'Affaires");
            entetes.add("Nb Employés");
            // en général c'est en fin de tableau.
            entetes.add("commentaire;");

            // 3. Initialiser le DefaultTableModel (avec 0 ligne au départ)
            modelTable = new DefaultTableModel(new Object[][]{}, entetes.toArray());

            // 4. Parcourir la liste de clients et ajouter chaque ligne
            for (Societe societe : listeSociete) {
                Client client = (Client) societe;
                Object[] dataRow = new Object[]{client.getIdentifiant(), client.getRaisonSociale(),
                        client.getAdresse().toString(), // Long
                        client.getTelephone(), // Long
                        client.getAdresseMail(), // Long
                        client.getNombreEmployes(), // int
                        client.getChiffreAffaire(), // int
                        client.getCommentaire() // Long
                };
                modelTable.addRow(dataRow);
            }
        } else if (typeSociete.equals(TypeSociete.PROSPECT)) {

            // 2. Définir les en-têtes
            entetes.add("Date de prospection");
            entetes.add("Interressé");
            // en général c'est en fin de tableau.
            entetes.add("commentaire;");

            // 3. Initialiser le DefaultTableModel (avec 0 ligne au départ)
            modelTable = new DefaultTableModel(new Object[][]{}, entetes.toArray());

            // 4. Parcourir la liste de clients et ajouter chaque ligne
            for (Societe societe : listeSociete) {
                Prospect prospect = (Prospect) societe;

                Object[] dataRow = new Object[]{prospect.getIdentifiant(),
                        prospect.getRaisonSociale(), prospect.getAdresse().toString(), // Long
                        prospect.getTelephone(), // Long
                        prospect.getDateProspection(), // Long
                        prospect.getInteret(), // int
                        prospect.getCommentaire() // Long
                };
                modelTable.addRow(dataRow);
            }
        } else {
            //ca peut arriver dans des tests de robustesse, ca me permettra de savoir que ca s'est passé.
            AppLogger.LOGGER.severe(
                    "le TypeSociete n'était pas initialisé dans l'écran des listes, ca n'est pas sensé être possible");
            throw new donneeException("le TypeSociete ne s'est pas correctement initialisé.");
        }

        // 5. Appliquer le modèle à la table
        tableau.setModel(modelTable);
    }


    private void redimensionner() {
        Dimension tailleEcran = Toolkit.getDefaultToolkit().getScreenSize();
        // Le (int) est obligatoire pour convertir le résultat à virgule en entier
        contentPane.setPreferredSize(
                new Dimension((int) (tailleEcran.width * 0.6), (int) (tailleEcran.height * 0.3)));

    }


    private void onOK() {
        // add your code here
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

}
