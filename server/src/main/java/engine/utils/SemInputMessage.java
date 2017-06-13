package engine.utils;

import java.util.concurrent.Semaphore;

/**
 * Created by Antoine on 13/06/2017.
 */
public class SemInputMessage {
    private static Semaphore semaphore;

    /** Constructeur privé */
    private SemInputMessage()
    {
        semaphore = new Semaphore(1);
    }

    /** Instance unique pré-initialisée */
    private static SemInputMessage INSTANCE = new SemInputMessage();

    /** Point d'accès pour l'instance unique du singleton */
    public static SemInputMessage getInstance()
    {
        return INSTANCE;
    }

    public static Semaphore getSemaphore(){
        return semaphore;
    }
}
