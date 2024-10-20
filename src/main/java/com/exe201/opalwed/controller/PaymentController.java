package com.exe201.opalwed.controller;

import com.exe201.opalwed.config.VNPayConfig;
import com.exe201.opalwed.dto.ResponseObject;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("${api.endpoint.base-url}/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final VNPayConfig vnPayConfig;
    @GetMapping("/create-payment-vnpay")
    public ResponseObject createPayment(HttpServletRequest request) {

        Map<String, String> vnpParamsMap = vnPayConfig.getVNPayConfig();
        vnpParamsMap.put("vnp_Amount", String.valueOf(10_000 * 100L));
        vnpParamsMap.put("vnp_IpAddr", vnPayConfig.getIpAddress(request));

        String queryUrl = vnPayConfig.getPaymentURL(vnpParamsMap, true);
        String hashData = vnPayConfig.getPaymentURL(vnpParamsMap, false);
        String vnpSecureHash = vnPayConfig.hmacSHA512(vnPayConfig.secretKey, hashData);
        queryUrl += "&vnp_SecureHash=" + vnpSecureHash;
        String paymentUrl = vnPayConfig.vnpPayUrl + "?" + queryUrl;


        return ResponseObject.builder()
                .status(HttpStatus.OK)
                .message("Đường dẫn thanh toán VNPay")
                .data(paymentUrl)
                .build();
    }

    @Operation(description = "Xử lý riêng cho vnpay call backend")
    @GetMapping("/vn-pay-callback")
    public String payCallbackHandler(HttpServletRequest request) {
        String status = request.getParameter("vnp_ResponseCode");
        if (status.equals("00")) {
            return "ok";
        } else {
            return "error";
        }
    }


}
