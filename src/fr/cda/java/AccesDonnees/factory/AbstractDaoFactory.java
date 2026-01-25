package fr.cda.java.AccesDonnees.factory;

import fr.cda.java.AccesDonnees.DaoInterface;
import fr.cda.java.gestionErreurs.Exceptions.TreatedException;
import fr.cda.java.gestionErreurs.Logger.AppLogger;
import fr.cda.java.AccesDonnees.Services.TreatedExceptionService;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 * AbstractDaoFactory
 *
 * <p>Description : …</p>
 *
 * @author CDA-09
 * @version 1.0
 * @since 21/01/2026
 */
public abstract class AbstractDaoFactory {

    Map<Class<?>, DaoInterface> listeDao = new HashMap<>();
    TreatedExceptionService gestionDesErreurs = new TreatedExceptionService();

    /**
     * renvoie un dao qui gere la table prospect.
     * @return
     * @throws TreatedException
     */
    public abstract DaoInterface getProspectDao() throws TreatedException;

    /**
     * renvoie un dao qui gere la table contrat.
     * @return
     * @throws TreatedException
     */
    public abstract DaoInterface getContratDao() throws TreatedException;

    /**
     * renvoie un dao qui gere la table adresse.
     * @return
     * @throws TreatedException
     */
    public abstract DaoInterface getAdresseDao() throws TreatedException;

    /**
     * renvoie un dao qui gere la table client.
     * @return
     * @throws TreatedException
     */
    public abstract DaoInterface getClientDao() throws TreatedException;

    /**
     * permet d'éviter de recréer des dao a chaques fois.
     * @param classe
     * @return
     * @throws TreatedException
     */
    DaoInterface getInstance(Class<?> classe) throws TreatedException {
        DaoInterface retour = null;
        if (listeDao.containsKey(classe)) {
            retour = listeDao.get(classe);
        } else {
            try {
                retour = (DaoInterface) classe.getDeclaredConstructor().newInstance();
                listeDao.put(classe, retour);

            } catch (NoSuchMethodException e) {
                AppLogger.LOGGER.severe(
                        "Constructeur introuvable pour " + classe.getSimpleName());
                throw AppLogger.log(gestionDesErreurs.handleException(e));

            } catch (InstantiationException e) {
                AppLogger.LOGGER.severe(
                        new StringBuilder
                                ("Impossible d'instancier la classe abstraite ou interface")
                                .append(classe.getSimpleName()).toString());
                throw AppLogger.log(gestionDesErreurs.handleException(e));

            } catch (IllegalAccessException e) {
                AppLogger.LOGGER.severe(
                        new StringBuilder
                                (" Accès refuse au constructeur de")
                                .append(classe.getSimpleName()).toString());
                throw AppLogger.log(gestionDesErreurs.handleException(e));

            } catch (InvocationTargetException e) {
                AppLogger.LOGGER.severe(
                        new StringBuilder
                                (" Le constructeur de ").append(classe.getSimpleName())
                                .append(" a leve une exception : ").append(e.getCause())
                                .toString());
                throw AppLogger.log(gestionDesErreurs.handleException(e));
            }
        }
        return retour;
    }


}
