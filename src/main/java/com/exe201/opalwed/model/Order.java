package com.exe201.opalwed.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "Order")
@Getter
@Setter
public class Order {

    @Id
    private Long id;

    private Long payOSOrderId;

}
