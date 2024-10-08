package net.axel.repositories.implementations;

import net.axel.config.DatabaseConnection;
import net.axel.models.entities.Contract;
import net.axel.models.entities.Partner;
import net.axel.models.enums.ContractStatus;
import net.axel.models.enums.PartnerStatus;
import net.axel.models.enums.TransportType;
import net.axel.repositories.interfaces.IContractRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ContractRepository implements IContractRepository {

    private final String tableName = "contracts";
    private final Connection connection = DatabaseConnection.getInstance().getConnection();

    public ContractRepository() throws SQLException {
    }

    @Override
    public void addContract(Contract contract) {
        String query = "INSERT INTO " + tableName + " (id, start_date, end_date, special_tariff, conditions_accord, renewable, contract_status, partner_id) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?::ContractStatus, ?)";
        try(PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setObject(1, contract.getId());
            stmt.setDate(2, new Date(contract.getStartDate().getTime()));
            stmt.setDate(3, new Date(contract.getEndDate().getTime()));
            stmt.setDouble(4, contract.getSpecialTariff());
            stmt.setString(5, contract.getConditionsAccord());
            stmt.setBoolean(6, contract.getRenewable());
            stmt.setString(7, contract.getContractStatus().name());
            stmt.setObject(8, contract.getPartner().getId());
            stmt.executeUpdate();
        } catch(SQLException e) {
            System.err.println("Error adding a Contract : " + e.getMessage());
        }
    }

    @Override
    public Contract getContractById(UUID id) {
        String query = "SELECT c.*, p.* FROM " + tableName + " c " +
                "JOIN partners p ON c.partner_id = p.id " +
                "WHERE c.id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setObject(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapToContract(resultSet);
                }
            }
        } catch(SQLException e) {
            e.printStackTrace();
            System.err.println("Error getting Contract by ID : " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Contract> getAllContracts() {
        List<Contract> contracts = new ArrayList<>();
        String query = "SELECT c.* , p.* FROM " + tableName + " c JOIN partners p ON c.partner_id = p.id";
        try(Statement stmt = connection.createStatement()) {
            ResultSet resultSet = stmt.executeQuery(query);
            while (resultSet.next()) {
                contracts.add(mapToContract(resultSet));
            }
        return contracts;
        } catch(SQLException e) {
            e.printStackTrace();
            System.err.println("Error getting all contract : " + e.getMessage());
            return null;
        }
    }

    @Override
    public void updateContract(Contract contract) {
        String query = "UPDATE " + tableName + " SET start_date = ?, end_date = ?, special_tariff = ?, conditions_accord = ?, renewable = ?, contract_status = ?::ContractStatus, partner_id = ? WHERE id = ?";
        try(PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setDate(1, new Date(contract.getStartDate().getTime()));
            stmt.setDate(2, new Date(contract.getEndDate().getTime()));
            stmt.setDouble(3, contract.getSpecialTariff());
            stmt.setString(4, contract.getConditionsAccord());
            stmt.setBoolean(5, contract.getRenewable());
            stmt.setString(6, contract.getContractStatus().name());
            stmt.setObject(7, contract.getPartner().getId());
            stmt.setObject(8, contract.getId());
            stmt.executeUpdate();
        } catch(SQLException e) {
            e.printStackTrace();
            System.err.println("Error updating contract : " + e.getMessage());
        }
    }

    @Override
    public void deleteContract(UUID id) {
        String query = "DELETE FROM " + tableName + " WHERE id = ?";
        try(PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setObject(1, id);
            stmt.executeUpdate();
        } catch(SQLException e) {
            e.printStackTrace();
            System.err.println("Error deleting a contract : " + e.getMessage());
        }
    }

    private Contract mapToContract(ResultSet resultSet) throws SQLException {
        UUID contractId = UUID.fromString(resultSet.getString("id"));
        Date startDate = resultSet.getDate("start_date");
        Date endDate = resultSet.getDate("end_date");
        double specialTariff = resultSet.getDouble("special_tariff");
        String conditionsAccord = resultSet.getString("conditions_accord");
        Boolean renewable = resultSet.getBoolean("renewable");
        ContractStatus contractStatus = ContractStatus.valueOf(resultSet.getString("contract_status"));

        UUID partnerId = UUID.fromString(resultSet.getString("partner_id"));
        String companyName = resultSet.getString("company_name");
        String comercialContact = resultSet.getString("comercial_contact");
        String geographicalArea = resultSet.getString("geographical_area");
        String specialConditions = resultSet.getString("special_conditions");
        TransportType transportType = TransportType.valueOf(resultSet.getString("transport_type"));
        PartnerStatus partnerStatus = PartnerStatus.valueOf(resultSet.getString("partner_status"));
        Date creationDate = resultSet.getDate("creation_date");

        Partner partner = new Partner(partnerId, companyName, comercialContact, transportType, geographicalArea, specialConditions, partnerStatus, creationDate);

        return new Contract(contractId, startDate, endDate, specialTariff, conditionsAccord, renewable, contractStatus, partner);
    }
}
