package fr.cda.java.AccesDonnees.Services;

import fr.cda.java.AccesDonnees.DaoInterface;
import fr.cda.java.gestionErreurs.Exceptions.TreatedException;
import fr.cda.java.gestionErreurs.Exceptions.UniciteException;
import fr.cda.java.model.gestion.Adresse;
import fr.cda.java.model.gestion.Societe;
import fr.cda.java.utilitaire.AppContext;
import fr.cda.java.utilitaire.TypeSociete;
import java.util.List;

/**
 * fr.cda.java.AccesDonnees.dao.services.fr.cda.java.AccesDonnees.dao.SocieteService
 *
 * <p>Description : évite d'appeler un dao dans un dao.
 * Gère la composition des sociétés.</p>
 *
 * @author CDA-09
 * @version 1.0
 * @since 21/01/2026
 */
public class SocieteService {

    DaoInterface societeDao;
    DaoInterface adresseDao = AppContext.typeBDD.getDaoFactory().getAdresseDao();
    TreatedExceptionService gestionDesErreurs = new TreatedExceptionService();

    TypeSociete typeSociete;

    public Societe sauvegarder(Societe societe) throws TreatedException {
        DaoInterface daoConcurrent = null;
        if (typeSociete == TypeSociete.CLIENT) {

            daoConcurrent = AppContext.typeBDD.getDaoFactory().getProspectDao();

        } else if (typeSociete == TypeSociete.PROSPECT) {
            daoConcurrent = AppContext.typeBDD.getDaoFactory().getClientDao();
        }
        if (daoConcurrent.nameExist(societe.getRaisonSociale())) {
            throw gestionDesErreurs.handleException(
                    new UniciteException(societe.getRaisonSociale()));
        } else {
            if (societe.getIdentifiant() != 0) {
                societeDao.update(societe);
                adresseDao.update(societe.getAdresse());

            } else {
                societe.setAdresse((Adresse) adresseDao.create(societe.getAdresse()));
                societe = (Societe) societeDao.create(societe);

            }
        }
        return societe;
    }


    public SocieteService(TypeSociete type) throws TreatedException {
        typeSociete = type;

        if (type.equals(TypeSociete.PROSPECT)) {
            societeDao = AppContext.typeBDD.getDaoFactory().getClientDao();

        } else if (type.equals(TypeSociete.CLIENT)) {
            societeDao = AppContext.typeBDD.getDaoFactory().getProspectDao();
        }
    }


    public Societe getSociete(int id) throws TreatedException {
        Societe societe = (Societe) societeDao.getById(id);
        societe.setAdresse((Adresse) adresseDao.getById(societe.getIdAdresse()));
        return societe;
    }

    public List<Societe> getListeSociete() throws TreatedException {

        List<Societe> liste = societeDao.findAll();
        for (Societe s : liste) {
            // On récupère les adresses via le parentId (id de la société)
            s.setAdresse((Adresse)
                    adresseDao.getById(s.getIdAdresse()));
        }
        return liste;
    }

}
