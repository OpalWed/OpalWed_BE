package com.exe201.opalwed.service.impl;

import com.exe201.opalwed.dto.CreateOrderResponse;
import com.exe201.opalwed.model.Order;
import com.exe201.opalwed.repository.OrderRepository;
import com.exe201.opalwed.service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.payos.PayOS;
import vn.payos.type.CheckoutResponseData;
import vn.payos.type.ItemData;
import vn.payos.type.PaymentData;
import vn.payos.type.PaymentLinkData;

import java.util.Date;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final PayOS payOS;
    private final ObjectMapper objectMapper;
    private final OrderRepository orderRepository;

    @Transactional(rollbackFor = Throwable.class)
    public CreateOrderResponse createPayOSPaymentUrl() {
        try {
            PaymentData paymentData = getPaymentRequestData();

            CheckoutResponseData data = payOS.createPaymentLink(paymentData);

            Order order = new Order();
            order.setPayOSOrderId(data.getOrderCode());
            order = orderRepository.save(order);

            return CreateOrderResponse.builder().orderId(order.getId()).payOSData(data).build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public PaymentLinkData getPayOSPaymentUrl(long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow();
        try {
            return payOS.getPaymentLinkInformation(order.getPayOSOrderId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public PaymentLinkData cancelPayment(long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow();
        try {
            return payOS.cancelPaymentLink(order.getPayOSOrderId(), null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private PaymentData getPaymentRequestData() {
        final String productName = "Dịch vụ OpalWed";
        final String description = "Test";
        final String returnUrl = "localhost:5173/payment-success";
        final String cancelUrl = "localhost:5173/payment-success";
        final int price = 50000;
        String currentTimeString = String.valueOf(String.valueOf(new Date().getTime()));
        long orderCode = Long.parseLong(currentTimeString.substring(currentTimeString.length() - 6));

        ItemData item = ItemData.builder()
                .name(productName)
                .price(price)
                .quantity(1)
                .build();

        return PaymentData.builder()
                .orderCode(orderCode)
                .description(description)
                .amount(price)
                .item(item)
                .returnUrl(returnUrl)
                .cancelUrl(cancelUrl)
                .build();
    }


}
