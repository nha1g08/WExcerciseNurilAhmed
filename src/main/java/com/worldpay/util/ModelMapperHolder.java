package com.worldpay.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ModelMapperHolder {

  private static final ModelMapper MODEL_MAPPER = new ModelMapper();

  public static ModelMapper get() {
    return MODEL_MAPPER;
  }

}
