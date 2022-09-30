#!/usr/bin/env bash
set -e

psql -v ON_ERROR_STOP=1 --username "postgres" --dbname "bank" <<-EOSQL
	CREATE TABLE account (
  	id serial PRIMARY KEY,
  	document_number VARCHAR ( 50 )
  );

INSERT INTO account(document_number)
VALUES ('12345678900');

CREATE TABLE account_transaction (
	id serial PRIMARY KEY,
	account_id serial,
	operation_type varchar( 50 ),
	amount float8,
	event_date TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO account_transaction(account_id, operation_type, amount)
VALUES (1, 4, 123.45);
EOSQL

