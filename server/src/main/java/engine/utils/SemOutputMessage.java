package engine.utils;

import java.util.concurrent.Semaphore;

/**
 * Created by Antoine on 13/06/2017.
 */
public class SemOutputMessage {
        private static Semaphore semaphore;

        /** Constructeur privé */
        private SemOutputMessage()
        {
                semaphore = new Semaphore(1);
        }

        /** Instance unique pré-initialisée */
        private static SemOutputMessage INSTANCE = new SemOutputMessage();

        /** Point d'accès pour l'instance unique du singleton */
        public static SemOutputMessage getInstance()
        {
            return INSTANCE;
        }

        public static Semaphore getSemaphore(){
                return semaphore;
        }
}
