package net.axel.models.dto;


import net.axel.models.enums.ContractStatus;

import java.util.Date;
import java.util.UUID;

public record ContractDto(
        Date startDate,
        Date endDate,
        double specialTariff,
        String conditionAccord,
        Boolean renewable,
        ContractStatus status,
        UUID partnerId
) {
}
