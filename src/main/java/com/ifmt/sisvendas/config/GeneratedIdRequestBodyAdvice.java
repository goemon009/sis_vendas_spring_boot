package com.ifmt.sisvendas.config;

import java.lang.reflect.Field;
import java.lang.reflect.Type;

import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@RestControllerAdvice
public class GeneratedIdRequestBodyAdvice extends RequestBodyAdviceAdapter {

    @Override
    public boolean supports(MethodParameter methodParameter,
                            Type targetType,
                            Class<? extends HttpMessageConverter<?>> converterType) {

        return ehEntidadeJpa(methodParameter.getParameterType())
                && ehMetodoDeEscrita(methodParameter);
    }

    @Override
    public Object afterBodyRead(Object body,
                                HttpInputMessage inputMessage,
                                MethodParameter parameter,
                                Type targetType,
                                Class<? extends HttpMessageConverter<?>> converterType) {

        limparIdGerado(body);
        return body;
    }

    private boolean ehEntidadeJpa(Class<?> tipo) {
        return tipo.isAnnotationPresent(Entity.class);
    }

    private boolean ehMetodoDeEscrita(MethodParameter methodParameter) {
        return methodParameter.hasMethodAnnotation(PostMapping.class)
                || methodParameter.hasMethodAnnotation(PutMapping.class)
                || methodParameter.hasMethodAnnotation(PatchMapping.class);
    }

    private void limparIdGerado(Object body) {
        ReflectionUtils.doWithFields(body.getClass(), field -> limparCampoId(body, field));
    }

    private void limparCampoId(Object body, Field field) throws IllegalAccessException {
        if (!field.isAnnotationPresent(Id.class) || !field.isAnnotationPresent(GeneratedValue.class)) {
            return;
        }

        ReflectionUtils.makeAccessible(field);
        field.set(body, valorVazio(field.getType()));
    }

    private Object valorVazio(Class<?> tipo) {
        if (!tipo.isPrimitive()) {
            return null;
        }

        if (tipo == long.class) {
            return 0L;
        }
        if (tipo == int.class) {
            return 0;
        }
        if (tipo == short.class) {
            return (short) 0;
        }
        if (tipo == byte.class) {
            return (byte) 0;
        }

        throw new IllegalArgumentException("Tipo de id gerado nao suportado: " + tipo.getName());
    }
}
