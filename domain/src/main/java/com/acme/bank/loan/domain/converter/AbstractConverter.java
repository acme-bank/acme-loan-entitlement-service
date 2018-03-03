package com.acme.bank.loan.domain.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.support.ConfigurableConversionService;

@SuppressWarnings({"WeakerAccess", "unused", "unchecked"})
public abstract class AbstractConverter<S, T> implements Converter<S, T>, InitializingBean {

    protected ConfigurableConversionService conversionService;

    @Override
    public void afterPropertiesSet() throws Exception {
        conversionService.addConverter(this);
    }

    @Autowired
    public void setConversionService(ConfigurableConversionService conversionService) {
        this.conversionService = conversionService;
    }

    protected <S1, T2> List<T2> convertList(List<S1> sourceList, Class<S1> sourceClass, Class<T2> targetClass) {
        if (sourceList == null) {
            return new ArrayList<>();
        }
        TypeDescriptor sourceType = TypeDescriptor.collection(List.class, TypeDescriptor.valueOf(sourceClass));
        TypeDescriptor targetType = TypeDescriptor.collection(List.class, TypeDescriptor.valueOf(targetClass));
        return (List<T2>) this.conversionService.convert(sourceList, sourceType, targetType);
    }
}
