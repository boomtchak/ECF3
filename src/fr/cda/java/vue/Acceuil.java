package fr.cda.java.vue;

import fr.cda.java.model.gestion.Client;
import fr.cda.java.model.gestion.Prospect;
import fr.cda.java.model.gestion.Societe;
import fr.cda.java.utilitaire.TypeAction;
import fr.cda.java.utilitaire.TypeSociete;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Acceuil extends JDialog {


    private JPanel contentPane;
    private JButton quitterButton;
    private JButton afficherToutButton;
    private JComboBox selectionSociete;
    private JButton créerButton;
    private JButton modifierButton;
    private JButton supprimerButton;
    private JButton afficherButton;
    private JButton gestionDesClientsButton;
    private JButton gestionDesProspectsButton;
    private JLabel description;
    private JPanel paneauSociete;
    private JPanel paneauActionsOnSelection;
    private JButton afficherLesContratsButton;
    private TypeSociete typeSociete
            = null;
    private Societe selectedSociete;

    /**
     * @return paneauSociete description
     */
    public JPanel getPaneauSociete() {
        return paneauSociete;
    }

    /**
     * permet de sauvegarder charger
     */
    public Acceuil() {

        this.setTitle("Menu principal");
        setContentPane(contentPane);
        setModal(true);

        afficherToutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ListeSocietes formulaireSociete = new ListeSocietes(typeSociete);
                formulaireSociete.pack();
                formulaireSociete.setVisible(true);
            }
        });
        gestionDesClientsButton.addActionListener(new ActionListener() {
            @Override
            /**
             * Quand on clic, on passe en TypeSociete = Client
             */
            public void actionPerformed(ActionEvent e) {
                typeSociete = TypeSociete.CLIENT;
                choixTypeSociete();


            }
        });
        gestionDesProspectsButton.addActionListener(new ActionListener() {

            @Override
            /**
             * Quand on clic, on passe en TypeSociete = Prospect
             */
            public void actionPerformed(ActionEvent e) {

                typeSociete = TypeSociete.PROSPECT;
                choixTypeSociete();
            }
        });
        selectionSociete.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                boolean estActif = (null != selectionSociete.getSelectedItem());
                if (estActif) {
                    if (selectionSociete.getSelectedItem() instanceof Client) {
                        selectedSociete = (Client) selectionSociete.getSelectedItem();
                    }
                    if (selectionSociete.getSelectedItem() instanceof Prospect) {
                        selectedSociete = (Prospect) selectionSociete.getSelectedItem();
                    }
                }
                activerBoutonAction();

            }
        });
        quitterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        créerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FormulaireSociete formulaireSociete = new FormulaireSociete(typeSociete,
                        TypeAction.CREATE);
                formulaireSociete.pack();
                formulaireSociete.setVisible(true);
                majListeDeroulante();
            }
        });
        afficherLesContratsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GestionContrats gestionContrats = new GestionContrats((Client) selectedSociete,
                        TypeAction.AFFICHER);
                gestionContrats.pack();
                gestionContrats.setVisible(true);
            }
        });
        afficherButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FormulaireSociete formulaireSociete = new FormulaireSociete(typeSociete,
                        TypeAction.AFFICHER, selectedSociete);
                formulaireSociete.pack();
                formulaireSociete.setVisible(true);
            }
        });
        modifierButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FormulaireSociete formulaireSociete = new FormulaireSociete(typeSociete,
                        TypeAction.UPDATE, selectedSociete);
                formulaireSociete.pack();
                formulaireSociete.setVisible(true);
                majListeDeroulante();
            }
        });
        supprimerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int reponse = JOptionPane.showConfirmDialog(null,
                        new StringBuilder("Etes vous sur de vouloir supprimer le ").append(
                                        typeSociete.getAffichage()).append(" ")
                                .append(selectedSociete.getRaisonSociale()).toString(),
                        new StringBuilder("Suppression de ").append(
                                selectedSociete.getRaisonSociale()).toString(),
                        JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                if (reponse == JOptionPane.YES_OPTION) {
                    typeSociete.getListe().supprimer(selectedSociete.getRaisonSociale());
                    majListeDeroulante();
                }
            }
        });
    }


    private void activerBoutonAction() {

        boolean estActif = (null != selectionSociete.getSelectedItem()
                && selectionSociete.getSelectedIndex() != -1);
        // Boucle sur tous les composants DANS le panneau
        for (Component comp : paneauActionsOnSelection.getComponents()) {
            comp.setEnabled(estActif);
        }


    }

    /**
     * permet de contextualiser l'écran en fonction du type de société
     * <p>
     * on travail en générique / objet pour detourner les incompatibilités de type
     */
    private void choixTypeSociete() {

        description.setText(
                new StringBuilder("Gestion des ").append(typeSociete.getAffichage()).toString());
        paneauSociete.setVisible(true);
        afficherToutButton.setText(
                new StringBuilder("Afficher tous les ").append(typeSociete.getAffichage())
                        .toString());
        afficherLesContratsButton.setVisible(typeSociete.equals(TypeSociete.CLIENT));
        majListeDeroulante();

        activerBoutonAction();
    }

    private void majListeDeroulante() {
        selectionSociete.removeAllItems();

        if (typeSociete.getListe().getListeSocietes().isEmpty()) {
            selectionSociete.setEnabled(false);
        } else {
            selectionSociete.setEnabled(true);
            for (Object societe : typeSociete.getListe().getListeSocietes().values()) {
                selectionSociete.addItem(societe);

            }
            // -1 signifie "aucune sélection"
            selectionSociete.setSelectedIndex(-1);
        }
    }
}
