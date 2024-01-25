package ru.andreev.ufanet.event;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
public class OrderRegisteredEvent extends Event {
    private UUID orderId;
    private int employeeId;
    private int customerId;
    private String deliveryTimeLeft;
    private int productId;
    private BigDecimal price;
    @SerializedName("eventDateTime")
    private LocalDateTime dateTime;
}
