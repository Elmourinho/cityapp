package com.elmar.cityapp.exception;


import com.elmar.cityapp.errors.ErrorResponse;
import java.util.Locale;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.text.StringSubstitutor;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;

@Slf4j
public class CityException extends RuntimeException {

  static final long serialVersionUID = 1L;

  private final ErrorResponse errorResponse;
  private final Map<String, Object> messageArguments;

  public ErrorResponse getErrorResponse() {
    return errorResponse;
  }

  public CityException(ErrorResponse errorResponse, Map<String, Object> messageArguments) {
    this.errorResponse = errorResponse;
    this.messageArguments = messageArguments;
  }

  @Override
  public String getMessage() {
    return messageArguments.isEmpty() ? errorResponse.getMessage() :
           StringSubstitutor.replace(errorResponse.getMessage(), messageArguments, "{", "}");
  }

  public String getLocalizedMessage(Locale locale, MessageSource messageSource) {
    try {
      String localizedMessage = messageSource.getMessage(errorResponse.getKey(), new Object[]{}, locale);
      return messageArguments.isEmpty() ? localizedMessage :
             StringSubstitutor.replace(localizedMessage, messageArguments, "{", "}");
    } catch (NoSuchMessageException exception) {
      log.warn("Please consider adding localized message for key {} and locale {}",
               errorResponse.getKey(), locale);
    }
    return getMessage();
  }


}
