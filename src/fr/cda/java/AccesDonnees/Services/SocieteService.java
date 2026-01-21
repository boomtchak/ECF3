package fr.cda.java.AccesDonnees.Services;

import fr.cda.java.AccesDonnees.DaoInterface;
import fr.cda.java.gestionErreurs.Exceptions.TreatedException;
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
    DaoInterface contratDao = AppContext.typeBDD.getDaoFactory().getContratDao();

    TypeSociete typeSociete;

    public SocieteService(TypeSociete type) throws TreatedException {
        typeSociete = type;

        if (type.equals(TypeSociete.PROSPECT)) {
            societeDao = AppContext.typeBDD.getDaoFactory().getProspectDao();

        } else if (type.equals(TypeSociete.CLIENT)) {
            societeDao = AppContext.typeBDD.getDaoFactory().getProspectDao();
        }
    }


    public Societe instancierSociete(int id) throws TreatedException {
        Societe societe = (Societe) societeDao.getById(id);
        societe.setAdresse((Adresse) adresseDao.getByParentId(societe.getIdentifiant()));
        return societe;
    }

    public List<Societe> instancierListeSociete() throws TreatedException {

        List<Societe> liste = societeDao.findAll();
        liste.forEach(s -> {
            // On récupère les adresses via le parentId (id de la société)
            s.setAdresse((Adresse) adresseDao.getByParentId(s.getIdentifiant()));

            // Si c'est un Client, on récupère aussi les contrats
            // je garde ce code pour ma biblio perso, mais en fait
            // je vais charger les contrats a l'ouverture de l'écran...
        /*    if (s instanceof Client client) {
                client.setListeContrats(
                        (Map<Integer, Contrat>) contratDao.getByParentId(s.getIdentifiant()));
            } */
        });
        return liste;
    }

}
