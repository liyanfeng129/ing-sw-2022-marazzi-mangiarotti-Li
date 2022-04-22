package it.polimi.ingsw;

public class InnerExceptions {
    public static class NotValidCloudSizeException extends EriantysExceptions
    {
        public NotValidCloudSizeException(String msg) {
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
    public static class EmptyBag extends EriantysExceptions
    {
        public EmptyBag(String msg) {
            super(msg);
        }
    }
    public static class NegativeValue extends EriantysExceptions
    {
        public NegativeValue(String msg) {
            super(msg);
        }
    }
    public static class BagMax26 extends EriantysExceptions
    {
        public BagMax26(String msg) {
            super(msg);
        }
    }
}
