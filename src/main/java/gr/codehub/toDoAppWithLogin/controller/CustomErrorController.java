package gr.codehub.toDoAppWithLogin.controller;

import gr.codehub.toDoAppWithLogin.base.AbstractLogEntity;
import gr.codehub.toDoAppWithLogin.exception.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CustomErrorController extends AbstractLogEntity {

    @GetMapping("/301")
    public void movedPermanently() {
        logger.error("A request redirected to the 301 error page.");
        throw new MovedPermanentlyException();
    }

    @GetMapping("/401")
    public void unauthorized() {
        logger.error("A request redirected to the 401 error page.");
        throw new UnauthorizedException();
    }

    @GetMapping("/405")
    public void methodNotAllowed() {
        logger.error("A request redirected to the 405 error page.");
        throw new MethodNotAllowedException();
    }

    @GetMapping("/500")
    public void internalServerError() {
        logger.error("A request redirected to the 500 error page.");
        throw new InternalServerErrorException();
    }

    @GetMapping("/502")
    public void badGateway() {
        logger.error("A request redirected to the 502 error page.");
        throw new BadGatewayException();
    }
}
