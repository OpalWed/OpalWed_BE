package com.exe201.opalwed.controller;


import com.exe201.opalwed.dto.CreateOrderResponse;
import com.exe201.opalwed.dto.ResponseObject;
import com.exe201.opalwed.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import vn.payos.type.PaymentLinkData;

@RestController
@RequestMapping("${api.endpoint.base-url}/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping()
    public ResponseObject createOrder() {
        CreateOrderResponse data = orderService.createPayOSPaymentUrl();
        return ResponseObject.builder().data(data).build();
    }

    @GetMapping(path = "payOS-payment-detail/{orderId}")
    public ResponseObject getPaymentDetailByOrderId(@PathVariable("orderId") long orderId) {
        PaymentLinkData payOSData = orderService.getPayOSPaymentUrl(orderId);
        return ResponseObject.builder().data(payOSData).build();
    }

    @DeleteMapping(path = "delete-payment/{orderId}")
    public ResponseObject cancelPayment(@PathVariable("orderId") long orderId) {
        PaymentLinkData payOSData = orderService.cancelPayment(orderId);
        return ResponseObject.builder().data(payOSData).build();
    }
}
