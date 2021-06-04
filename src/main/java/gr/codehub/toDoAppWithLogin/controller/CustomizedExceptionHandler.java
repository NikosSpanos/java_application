package gr.codehub.toDoAppWithLogin.controller;

import gr.codehub.toDoAppWithLogin.base.AbstractLogEntity;
import gr.codehub.toDoAppWithLogin.exception.EmptyItemDescriptionException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * This class is responsible for handling all errors, exceptions in a wider sense, that can be thrown while handling the
 * incoming request.
 */
@ControllerAdvice
public class CustomizedExceptionHandler extends AbstractLogEntity {
    @ExceptionHandler(EmptyItemDescriptionException.class)
    public final String handleAllExceptions(final Exception ex, RedirectAttributes redirectAttributes) throws Exception {
        logger.error("Attempted to add an item without description. Details: {}.", ex.getMessage());
        redirectAttributes.addFlashAttribute("isItemDescriptionEmpty", ex.getMessage());
        return "redirect:/";
    }
}
