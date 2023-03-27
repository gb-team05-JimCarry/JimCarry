package com.app.jimcarry.domain.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class PaymentDTO {
    private Long userId;
    private String userIdentification;
    private String userPassword;
    private String userName;
    private String userEmail;
    private String userPhone;
    private String userAddress;
    private String userAddressDetail;
    private String userGender;
    private String userBirth;
    private Long payId;
    private Long storageId;
    private String paymentDate;
}