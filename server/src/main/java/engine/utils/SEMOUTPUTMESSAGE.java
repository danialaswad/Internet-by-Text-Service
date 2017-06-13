package engine.utils;

import java.util.concurrent.Semaphore;

/**
 * Created by Antoine on 13/06/2017.
 */
public class SEMOUTPUTMESSAGE {
        private static Semaphore semaphore;

        /** Constructeur privé */
        private SEMOUTPUTMESSAGE()
        {
                semaphore = new Semaphore(1);
        }

        /** Instance unique pré-initialisée */
        private static SEMOUTPUTMESSAGE INSTANCE = new SEMOUTPUTMESSAGE();

        /** Point d'accès pour l'instance unique du singleton */
        public static SEMOUTPUTMESSAGE getInstance()
        {
            return INSTANCE;
        }

        public static Semaphore getSemaphore(){
                return semaphore;
        }
}
