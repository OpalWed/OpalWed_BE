package com.exe201.opalwed.service;

import com.exe201.opalwed.dto.CreateOrderResponse;
import vn.payos.type.PaymentLinkData;

public interface OrderService {
    public CreateOrderResponse createPayOSPaymentUrl();
    public PaymentLinkData getPayOSPaymentUrl(long orderId);

    PaymentLinkData cancelPayment(long orderId);
}
