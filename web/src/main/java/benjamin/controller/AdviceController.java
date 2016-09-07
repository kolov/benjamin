package benjamin.controller;

import benjamin.ErrorResponse;
import benjamin.exception.ConnectionException;
import benjamin.exception.NotAuthenticatedException;
import benjamin.exception.NotAuthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class AdviceController {

    private static final Logger LOG = LoggerFactory.getLogger(AdviceController.class);

    @ExceptionHandler(DataAccessResourceFailureException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    public Object mongoTimedOut(Exception e) {
        return new ErrorResponse("Error connecting to database");
    }

    @ExceptionHandler(NotAuthenticatedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public Object notAuthenticated(Exception e) {
        return new ErrorResponse("You need to log in first");
    }

    @ExceptionHandler(NotAuthorizedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ResponseBody
    public Object notAuthorized(Exception e) {
        return new ErrorResponse("You are mot allowed to do that");
    }

    @ExceptionHandler(ConnectionException.class)
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    @ResponseBody
    public Object serviceUnavailable(Exception e) {
        return new ErrorResponse("Could not connect to back-end system");
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    @ResponseBody
    public Object error(Exception e) {
        LOG.error("error", e);
        return new ErrorResponse("error: " + e.getMessage());
    }
}
