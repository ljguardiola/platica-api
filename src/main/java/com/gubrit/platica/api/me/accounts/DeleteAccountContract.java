package com.gubrit.platica.api.me.accounts;

import com.gubrit.platica.annotations.CurrentUser;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

public interface DeleteAccountContract {

  @DeleteMapping("/me/accounts/{accountId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  void delete(
      @CurrentUser UUID userId,
      @PathVariable UUID accountId
  );

}
