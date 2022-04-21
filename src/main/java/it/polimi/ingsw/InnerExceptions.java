package it.polimi.ingsw;

public class InnerExceptions {
    public static class NotValidCloudSizeException extends EriantysExceptions
    {
        public NotValidCloudSizeException(String msg) {
            super(msg);
        }
    }

    public static class NotEnoughStudentsINBagException extends EriantysExceptions
    {
        public NotEnoughStudentsINBagException(String msg) {
            super(msg);
        }
    }

    public static class IncompactIslandsException extends EriantysExceptions
    {
        public IncompactIslandsException(String msg) {
            super(msg);
        }
    }

    public static class NoMotherNatureException extends EriantysExceptions
    {
        public NoMotherNatureException(String msg) {
            super(msg);
        }
    }
}
