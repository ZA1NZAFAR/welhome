package fr.efrei.database_service.exception;



public class DatabaseExceptions {

    //entity already exists exception
    public static class EntityAlreadyExistsException extends RuntimeException {
        public EntityAlreadyExistsException() {
            super();
        }
    }

    //entity not found exception
    public static class EntityNotFoundException extends RuntimeException {

        public EntityNotFoundException() {
            super();
        }
    }

    // bad request exception
    public static class BadRequestException extends RuntimeException {
        public BadRequestException(String message) {
            super(message);
        }
    }
}
