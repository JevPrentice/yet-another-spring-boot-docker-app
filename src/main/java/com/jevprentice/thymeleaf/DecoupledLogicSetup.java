package com.jevprentice.thymeleaf;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;

import javax.annotation.PostConstruct;

@Slf4j
@Component
public class DecoupledLogicSetup {

    private final SpringResourceTemplateResolver templateResolver;

    public DecoupledLogicSetup(@NonNull final SpringResourceTemplateResolver templateResolver) {
        this.templateResolver = templateResolver;
    }

    @PostConstruct
    public void init() {
        templateResolver.setUseDecoupledLogic(true);
    }
}
