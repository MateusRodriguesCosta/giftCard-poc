package com.costasolutions.giftcards.controllers;

import com.costasolutions.security.controller.AuthController;
import com.costasolutions.security.dto.AuthRequest;
import com.costasolutions.security.dto.RegisterRequest;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Hidden
@RestController
public class ApiRootController {

    @GetMapping(path = "/")
    public RepresentationModel<?> root() {
        RepresentationModel<?> model = new RepresentationModel<>();
        AuthRequest dummyLogin = new AuthRequest("username", "password");
        model.add(linkTo(methodOn(AuthController.class).login(dummyLogin))
                .withRel("login"));
        RegisterRequest dummyRegister = new RegisterRequest("username", "password");
        model.add(linkTo(methodOn(AuthController.class).register(dummyRegister))
                .withRel("register"));
        model.add(linkTo(methodOn(GiftCardController.class).getAll(0, 10, null))
                .withRel("giftcards"));
        model.add(linkTo(methodOn(GiftCardController.class).createGiftCard(null))
                .withRel("create-giftcard"));

        String apiDocs = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/v3/api-docs")
                .toUriString();
        model.add(Link.of(apiDocs, "api-docs"));

        String swaggerUi = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/swagger-ui/index.html")
                .toUriString();
        model.add(Link.of(swaggerUi, "swagger-ui"));

        return model;
    }
}