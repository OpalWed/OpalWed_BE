package com.exe201.opalwed.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import vn.payos.type.CheckoutResponseData;

@Getter
@Setter
@Builder
public class CreateOrderResponse {

    Long orderId;

    CheckoutResponseData payOSData;

}
