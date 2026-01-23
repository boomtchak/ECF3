package fr.cda.java.vue;

import fr.cda.java.AccesDonnees.Services.SocieteService;
import fr.cda.java.gestionErreurs.Exceptions.TreatedException;
import fr.cda.java.model.gestion.Client;
import fr.cda.java.model.gestion.Prospect;
import fr.cda.java.model.gestion.Societe;
import fr.cda.java.utilitaire.AppContext;
import fr.cda.java.utilitaire.Severite;
import fr.cda.java.utilitaire.TypeAction;
import fr.cda.java.utilitaire.TypeSociete;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;
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
    SocieteService service;
    List<Societe> listeSociete;

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
                ListeSocietes formulaireSociete = new ListeSocietes(typeSociete, listeSociete);
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
                try {
                    listeSociete = service.getListeSociete();
                } catch (TreatedException ex) {
                    afficherErreurFatale(ex);
                }

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
                try {
                    listeSociete = service.getListeSociete();
                } catch (TreatedException ex) {
                    afficherErreurFatale(ex);
                }
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
                GestionContrats gestionContrats = new GestionContrats(
                        selectedSociete.getIdentifiant(), selectedSociete.getRaisonSociale(),
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
                    if (typeSociete.equals(TypeSociete.PROSPECT)) {

                        try {
                            AppContext.typeBDD.getDaoFactory().getProspectDao()
                                    .delete(selectedSociete.getIdentifiant());
                        } catch (TreatedException ex) {
                            throw new RuntimeException(ex);
                        }
                    } else if (typeSociete.equals(TypeSociete.CLIENT)) {

                        try {
                            AppContext.typeBDD.getDaoFactory().getClientDao()
                                    .delete(selectedSociete.getIdentifiant());
                        } catch (TreatedException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                    listeSociete.remove(selectedSociete);
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

        try {
            service = new SocieteService(typeSociete);
        } catch (TreatedException ex) {
            throw new RuntimeException(ex);
        }
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

        if (listeSociete.isEmpty()) {
            selectionSociete.setEnabled(false);
        } else {
            selectionSociete.setEnabled(true);
            for (Object societe : listeSociete) {
                selectionSociete.addItem(societe);

            }
            // -1 signifie "aucune sélection"
            selectionSociete.setSelectedIndex(-1);
        }
    }

    /**
     * Modal de fermeture forcée. PAs le choix, si on a pas l'acces au donnée, aucune fonctionalité
     * n'existe.
     */
    private static void afficherErreurFatale(TreatedException ex) {

        JOptionPane.showMessageDialog(null,
                ex.getMessage(),
                "Erreur Fatale",
                JOptionPane.ERROR_MESSAGE);

        // Sortie du système avec code d'erreur 1
        System.exit(1);
    }

    /**
     * Peut être amélioré. En tout cas dans l'acceuil, les erreurs doivent être bien vue et validées
     * par l'user.
     *
     * @param ex L'exception capturée depuis le service.
     */
    public static void afficherErreur(TreatedException ex) {

        if (ex.getSeverite() == Severite.URGENT) {
            afficherErreurFatale(ex);
        } else {
            // On définit le titre et l'icône selon la sévérité
            String titre = "Alerte Système";
            int icone = JOptionPane.INFORMATION_MESSAGE;

            if (ex.getSeverite() == Severite.MOYENNE) {
                titre = "Attention - Avertissement";
                icone = JOptionPane.WARNING_MESSAGE;
            }

            // showMessageDialog est bloquant : l'utilisateur DOIT cliquer sur OK
            JOptionPane.showMessageDialog(
                    null,
                    ex.getMessage(),
                    titre,
                    icone
            );
        }
    }
}
