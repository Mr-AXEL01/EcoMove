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
 CREATE TYPE bookingStatus AS ENUM ('CONFIRMED', 'PENDING', 'CANCELLED');


-- create tables

CREATE TABLE IF NOT EXISTS Partners (
    id UUID PRIMARY KEY,
    company_name VARCHAR(255) NOT NULL,
    comercial_contact VARCHAR(255),
    transport_type TransportType NOT NULL,
    geographical_area VARCHAR(255),
    special_conditions TEXT,
    partner_status PartnerStatus NOT NULL,
    creation_date DATE NOT NULL DEFAULT CURRENT_DATE
);

CREATE TABLE IF NOT EXISTS Contracts (
    id UUID PRIMARY KEY,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    special_tariff REAL,
    conditions_accord TEXT,
    renewable BOOLEAN NOT NULL,
    contract_status ContractStatus NOT NULL,
    partner_id UUID,
    FOREIGN KEY (partner_id) REFERENCES Partners(id)
);

CREATE TABLE IF NOT EXISTS Promotions (
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
    FOREIGN KEY (contract_id) REFERENCES Contracts(id)
);

CREATE TABLE IF NOT EXISTS Stations (
    id UUID PRIMARY KEY ,
    name VARCHAR(255) NOT NULL,
    location VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS journeys(
    id UUID PRIMARY KEY,
    start_station UUID,
    end_station UUID,
    FOREIGN KEY (start_station) REFERENCES stations(id),
    FOREIGN KEY (end_station) REFERENCES stations(id)
);

CREATE TABLE IF NOT EXISTS Tickets (
    id UUID PRIMARY KEY,
    transport_type TransportType NOT NULL,
    purchase_price REAL NOT NULL,
    resell_price REAL NOT NULL,
    sale_date TIMESTAMP,
    ticket_status TicketStatus NOT NULL,
    contract_id UUID,
    journey_id UUID,
    FOREIGN KEY (contract_id) REFERENCES Contracts(id),
    FOREIGN KEY (journey_id) REFERENCES journeys(id)
);

CREATE TABLE IF NOT EXISTS Clients (
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    phone VARCHAR(255),

    PRIMARY KEY (email)
);

CREATE TABLE IF NOT EXISTS Bookings (
    id UUID PRIMARY KEY,
    booking_status BookingStatus NOT NULL,
    client_email UUID,
    FOREIGN KEY (client_email) REFERENCES Clients(email)
);

CREATE TABLE IF NOT EXISTS Favorites (
    id UUID PRIMARY KEY,
    start_station VARCHAR(255) NOT NULL,
    end_station VARCHAR(255) NOT NULL,
    departure_time TIMESTAMP NOT NULL,
    transport_type TransportType,
    client_email UUID,
    FOREIGN KEY (client_email) REFERENCES Clients(email)
);
