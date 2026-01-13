package fr.cda.java.vue;

import fr.cda.java.Exceptions.AffichageException;
import fr.cda.java.Exceptions.MandatoryDataException;
import fr.cda.java.Exceptions.RegexException;
import fr.cda.java.Exceptions.UniciteException;
import fr.cda.java.model.gestion.Client;
import fr.cda.java.model.gestion.Prospect;
import fr.cda.java.model.gestion.Societe;
import fr.cda.java.model.util.Adresse;
import fr.cda.java.model.util.Interet;
import fr.cda.java.model.util.TypeAction;
import fr.cda.java.model.util.TypeSociete;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class FormulaireSociete extends JDialog {

    private JPanel contentPane;
    private JPanel erreur;
    private JPanel blocFormulaireComplet;
    private JPanel panneauProspect;
    private JPanel panneauClient;
    private JLabel erreurLabel;
    private JTextField idTextField;
    private JTextField raisonSocialeTextField;
    private JTextField numRueTextField;
    private JTextField nomRueTextField;
    private JTextField cpTextField;
    private JTextField villeTextField;
    private JTextField telTextField;
    private JTextField mailTextField;
    private JTextField nbEmployeTextField;
    private JTextField chiffreAffaireTextField;
    private JTextField dateProspectTextField;
    private JComboBox interetCombo;
    private JTextArea comTextField;
    private JButton voirLesContratsButton;
    private JButton buttonOK;
    private JButton buttonCancel;
    ArrayList<JComponent> listeChampsFormulaire = new ArrayList<JComponent>();

    TypeSociete typeSociete;
    TypeAction typeAction;
    Societe societe;

    public FormulaireSociete(TypeSociete typeSociete, TypeAction typeAction) {
        this(typeSociete, typeAction, null);
    }

    public FormulaireSociete(TypeSociete typeSociete, TypeAction typeAction, Societe client) {
        this.typeSociete = typeSociete;
        this.typeAction = typeAction;
        this.societe = client;
        this.setTitle(new StringBuilder(typeAction.toString()).append(typeSociete.getAffichage())
                .toString());
        voirLesContratsButton.setVisible(!typeAction.equals(TypeAction.CREATE));
        creerListeComposants();
        if (typeSociete.equals(TypeSociete.PROSPECT)) {
            voirLesContratsButton.setVisible(false);
            for (Interet interet : Interet.values()) {
                interetCombo.addItem(interet);
            }
        }
        disableFormulaire();
        if (typeAction.equals(TypeAction.UPDATE) || typeAction.equals(TypeAction.AFFICHER)) {
            bindingSetters();
        } else {
            interetCombo.setSelectedItem(Interet.INCONNU);
            idTextField.setText(String.valueOf(typeSociete.getListe().getCompteurIdentifiant()));
        }
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        this.setTitle(
                new StringBuilder(typeAction.toString()).append(typeSociete).toString());
        panneauProspect.setVisible(typeSociete.equals(TypeSociete.PROSPECT));
        panneauClient.setVisible(typeSociete.equals(TypeSociete.CLIENT));

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

        voirLesContratsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                GestionContrats gestionContrats = new GestionContrats((Client) societe,
                        typeAction.equals(TypeAction.AFFICHER));
                gestionContrats.pack();
                gestionContrats.setVisible(true);

            }
        });
    }


    private void creerListeComposants() {
        listeChampsFormulaire.add(dateProspectTextField);
        listeChampsFormulaire.add(interetCombo);
        listeChampsFormulaire.add(numRueTextField);
        listeChampsFormulaire.add(nomRueTextField);
        listeChampsFormulaire.add(cpTextField);
        listeChampsFormulaire.add(villeTextField);
        listeChampsFormulaire.add(telTextField);
        listeChampsFormulaire.add(mailTextField);
        listeChampsFormulaire.add(nbEmployeTextField);
        listeChampsFormulaire.add(chiffreAffaireTextField);
        listeChampsFormulaire.add(raisonSocialeTextField);
        listeChampsFormulaire.add(comTextField);
    }

    /**
     * sauvegarde. et affiche les erreurs.
     */
    private void onOK() {

        try {
            if (typeSociete.equals(TypeSociete.PROSPECT)) {
                Prospect prospect = (Prospect)getSociete();
                if (typeAction.equals(TypeAction.UPDATE)) {
                    prospect.setIdentifiant(getSociete().getIdentifiant());
                    typeSociete.getListe().replace(societe.getRaisonSociale(), prospect);
                } else {
                    typeSociete.getListe().ajouter(prospect);
                }
            } else {
                // inspiré de la factory.
                Client client = (Client)getSociete();
                Client clientTmp = (Client) societe;
                if(null != clientTmp.getListeContrats())
                client.setListeContrats(clientTmp.getListeContrats());
                // les listes gerent toutes seul l'auto incrément uniquement en create.
                if (typeAction.equals(TypeAction.UPDATE)) {
                    client.setIdentifiant(societe.getIdentifiant());
                    typeSociete.getListe().replace(societe.getRaisonSociale(), client);
                } else {
                    typeSociete.getListe().ajouter(client);

                }
            }
            dispose();
        } catch (DateTimeParseException e) {
            afficherErreur(e.getMessage());
        } catch (MandatoryDataException e) {
            afficherErreur(e.getMessage());
        } catch (RegexException e) {
            afficherErreur(e.getMessage());
        } catch (UniciteException e) {
            afficherErreur(e.getMessage());
        } catch (AffichageException e) {
            afficherErreur(e.getMessage());
        }
    }

    /**
     * Delegue les controles d'affichage
     * TODO -> mettre ca dans la futur Classe UtilConversion avec les TU associés.
     *
     * @param champs
     * @return
     * @throws AffichageException
     */
    private Object validerReglesAffichage(String champs) throws AffichageException {
        Object retour = null;
        switch (champs) {
            case "dateProspect":
                try {
                    retour = LocalDate.parse(dateProspectTextField.getText(),
                            DateTimeFormatter.ofPattern("dd/MM/uuuu"));

                } catch (DateTimeParseException e) {
                    throw new AffichageException(
                            "La date ne respecte pas les standards (JJ/MM/AAAA)");
                }
                break;
            case "nbEmploye":

                try {
                    retour = Integer.parseInt(nbEmployeTextField.getText());
                } catch (NumberFormatException e) {
                    throw new AffichageException("Le nombre d'employés doit être un chiffre");
                }
                break;

            case "chiffreAffaire":
                try {
                    retour = Long.parseLong(chiffreAffaireTextField.getText());
                } catch (NumberFormatException e) {
                    throw new AffichageException("Le chiffre d'affaire doit être un chiffre");
                }
                break;
        }
        return retour;

    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    /**
     * permet de binder le contenu de mon objet avec les textfields (synchronizeViewToModel)
     */
    private void bindingSetters() {
        if (typeSociete.equals(TypeSociete.CLIENT)) {
            Client client = (Client) societe;
            nbEmployeTextField.setText(String.valueOf(client.getNombreEmployes()));
            chiffreAffaireTextField.setText(String.valueOf(client.getChiffreAffaire()));
        } else if (typeSociete.equals(TypeSociete.PROSPECT)) {
            Prospect prospect = (Prospect) societe;
            dateProspectTextField.setText(prospect.getDateProspection()
                    .format(DateTimeFormatter.ofPattern("dd/MM/uuuu")));
            interetCombo.setSelectedItem(prospect.getInteret());


        }
        telTextField.setText(societe.getTelephone());
        numRueTextField.setText(societe.getAdresse().getNumeroDeRue());
        nomRueTextField.setText(societe.getAdresse().getNomDeRue());
        cpTextField.setText(societe.getAdresse().getCodePostal());
        villeTextField.setText(societe.getAdresse().getVille());
        mailTextField.setText(societe.getAdresseMail());
        raisonSocialeTextField.setText(societe.getRaisonSociale());
        idTextField.setText(String.valueOf(societe.getIdentifiant()));
        comTextField.setText(societe.getCommentaire());
    }


    /**
     *
     * @return
     * @throws MandatoryDataException
     * @throws RegexException
     * @throws UniciteException
     */
    private Societe getSociete() throws MandatoryDataException, RegexException, UniciteException {
        Adresse adresse = new Adresse(numRueTextField.getText(), nomRueTextField.getText(),
                cpTextField.getText(), villeTextField.getText());
        Societe retour = null;
        if (typeSociete.equals(TypeSociete.CLIENT)){

        retour = new Client(raisonSocialeTextField.getText(), adresse, telTextField.getText(), mailTextField.getText(),
                comTextField.getText(), Long.parseLong( nbEmployeTextField.getText()), Integer.getInteger(chiffreAffaireTextField.getText()));
        }
        if (typeSociete.equals(TypeSociete.PROSPECT)){
            retour = new Prospect(raisonSocialeTextField.getText(), adresse, telTextField.getText(), mailTextField.getText(),
                    comTextField.getText(), (LocalDate) validerReglesAffichage("dateProspect"),
                    (Interet) interetCombo.getSelectedItem());
        }
            return retour;
    }

    /**
     * TODO : mettre une liste de composant boucler dessus.
     */
    private void disableFormulaire() {
        for (JComponent component : listeChampsFormulaire) {
            component.setEnabled(!typeAction.equals(TypeAction.AFFICHER));
        }
    }

    void afficherErreur(String message) {
        erreur.setVisible(!message.isEmpty());
        erreurLabel.setText(message);
    }

}
