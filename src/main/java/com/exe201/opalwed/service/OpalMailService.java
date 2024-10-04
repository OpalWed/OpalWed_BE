package com.exe201.opalwed.service;

import com.exe201.opalwed.model.Account;
import jakarta.mail.MessagingException;

public interface OpalMailService {
    void sendOTP(Account account) throws MessagingException;

    void sendOTPForPassword(Account account) throws MessagingException;
}
