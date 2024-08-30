--  create DB
CREATE DATABASE IF NOT EXISTS EcoMove ;

-- use EcoMove
 USE EcoMove;


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
    companyName VARCHAR(255) NOT NULL,
    comercialContact VARCHAR(255),
    transportType TransportType NOT NULL,
    geographicalArea VARCHAR(255),
    specialConditions TEXT,
    partnerStatus PartnerStatus NOT NULL,
    creationDate DATE NOT NULL
);

CREATE TABLE IF NOT EXISTS Contract (
    id UUID PRIMARY KEY,
    startDate DATE NOT NULL,
    endDate DATE NOT NULL,
    specialTariff REAL,
    conditionsAccord TEXT,
    renewable BOOLEAN NOT NULL,
    contractStatus ContractStatus NOT NULL,
    partnerId UUID,
    FOREIGN KEY (partnerId) REFERENCES Partner(id)
);

CREATE TABLE IF NOT EXISTS Promotion (
    id UUID PRIMARY KEY,
    offerName VARCHAR(255) NOT NULL,
    description TEXT,
    startDate DATE NOT NULL,
    endDate DATE NOT NULL,
    reductionType ReductionType NOT NULL,
    reductionValue REAL NOT NULL,
    conditions TEXT,
    offerStatus OfferStatus NOT NULL,
    contractId UUID,
    FOREIGN KEY (contractId) REFERENCES Contract(id)
);

CREATE TABLE IF NOT EXISTS Ticket (
    id UUID PRIMARY KEY,
    transportType TransportType NOT NULL,
    purchasePrice REAL NOT NULL,
    resellPrice REAL NOT NULL,
    saleDate TIMESTAMP,
    ticketStatus TicketStatus NOT NULL,
    contractId UUID,
    FOREIGN KEY (contractId) REFERENCES Contract(id)
);

