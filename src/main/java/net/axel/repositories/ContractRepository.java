package net.axel.repositories;

import net.axel.config.DatabaseConnection;
import net.axel.models.entities.Contract;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Date;

public class ContractRepository {

    private final String tableName = "contract";
    private final Connection connection = DatabaseConnection.getInstance().getConnection();

    public ContractRepository() throws SQLException {
    }
    public void addContract(Contract contract) throws  SQLException{
        String query = "INSERT INTO " + tableName + " (id, start_date, end_date, special_tariff, conditions_accord, renewable, contract_status, partner) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?::ContractStatus, ?)";
        try(PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setObject(1, contract.getId());
            stmt.setDate(2, new Date(contract.getStartDate().getTime()));
            stmt.setDate(3, new Date(contract.getEndDate().getTime()));
            stmt.setDouble(4, contract.getSpecialTariff());
            stmt.setString(5, contract.getConditionsAccord());
            stmt.setBoolean(6, contract.getRenewable());
            stmt.setString(7, contract.getContractStatus().name());
            stmt.setObject(8, contract.getPartner());
        }
    }
}
