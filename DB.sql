--  create DB
-- CREATE DATABASE ecomove ;

-- use EcoMove
 \c ecomove;


-- create enums
 CREATE TYPE partnerStatus AS ENUM ('ACTIVE', 'INACTIVE', 'SUSPENDED');
 CREATE TYPE transportType AS ENUM ('PLANE', 'TRAIN', 'BUS', 'TAXI');
 CREATE TYPE ticketStatus AS ENUM ('SOLD', 'CANCELLED', 'PENDING');
 CREATE TYPE contractStatus AS ENUM ('IN_PROGRESS', 'COMPLETE', 'SUSPENDED');
 CREATE TYPE reductionType AS ENUM ('FIXED_AMOUNT', 'PERCENTAGE');
 CREATE TYPE offerStatus AS ENUM ('ACTIVE', 'EXPIRED', 'SUSPENDED');


-- create tables

CREATE TABLE IF NOT EXISTS Partner (
    id UUID PRIMARY KEY,
    company_name VARCHAR(255) NOT NULL,
    comercial_contact VARCHAR(255),
    transport_type TransportType NOT NULL,
    geographical_area VARCHAR(255),
    special_conditions TEXT,
    partner_status PartnerStatus NOT NULL,
    creation_date DATE NOT NULL
);

CREATE TABLE IF NOT EXISTS Contract (
    id UUID PRIMARY KEY,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    special_tariff REAL,
    conditions_accord TEXT,
    renewable BOOLEAN NOT NULL,
    contract_status ContractStatus NOT NULL,
    partner_id UUID,
    FOREIGN KEY (partner_id) REFERENCES Partner(id)
);

CREATE TABLE IF NOT EXISTS Promotion (
    id UUID PRIMARY KEY,
    offer_name VARCHAR(255) NOT NULL,
    description TEXT,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    reduction_type ReductionType NOT NULL,
    reduction_value REAL NOT NULL,
    conditions TEXT,
    offer_status OfferStatus NOT NULL,
    contract_id UUID,
    FOREIGN KEY (contract_id) REFERENCES Contract(id)
);

CREATE TABLE IF NOT EXISTS Ticket (
    id UUID PRIMARY KEY,
    transport_type TransportType NOT NULL,
    purchase_price REAL NOT NULL,
    resell_price REAL NOT NULL,
    sale_date TIMESTAMP,
    ticket_status TicketStatus NOT NULL,
    contract_id UUID,
    FOREIGN KEY (contract_id) REFERENCES Contract(id)
);

