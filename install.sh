#!/usr/bin/env bash

# String formats
DIVIDER===========================
SUCCESS="SUCCESS";
ERROR="ERROR";
WARNING="WARNING";

# Colour formats
GREEN=$'\e[1;32m';
RED=$'\e[1;31m';
YELLOW=$'\e[1;33m';
END=$'\e[0m';

APP_DATA_DIR=./.data
DB_NAME=app
DB_FILE=$APP_DATA_DIR/$DB_NAME.db
DB_SCHEMA="CREATE TABLE $DB_NAME (id integer primary key autoincrement, title varchar not null, complete boolean default false, date_added datetime default current_timestamp, date_due datetime default null)";

function generate_db() {
  touch $DB_FILE;
  sqlite3 $DB_FILE "$DB_SCHEMA";
  ret=$?;

  if [ "$ret" -eq 0 ]; then
    DB_SUCCESS="${GREEN}$SUCCESS${END}... database generated.";
  else
    DB_SUCCESS="${RED}$ERROR${END}... could not generate database.";
  fi;

  printf "Generating database... %s\n" "${DB_SUCCESS}";
  return $ret;
}

function check_data_dir() {
  if [ -d "$APP_DATA_DIR" ]; then
    DIR_EXISTS="${GREEN}$SUCCESS${END}... already exists.";
    ret=0
  else
    DIR_EXISTS="${YELLOW}$WARNING${END}... doesn't exist.";
    ret=1
  fi

  printf "Checking data directory... %s\n" "${DIR_EXISTS}";
  return $ret;
}

function check_db_file() {
  if [[ -f "$DB_FILE" ]]; then
    DB_FILE_EXISTS="${GREEN}$SUCCESS${END}... already exists.";
    ret=0
  else
    DB_FILE_EXISTS="${YELLOW}$WARNING${END}... doesn't exist.";
    ret=1
  fi

  printf "Checking db file... %s\n" "${DB_FILE_EXISTS}";
  return $ret;
}

printf "INSTALLING\n%s\n\n" "$DIVIDER";

# Check the data directory exists - if not then generate it.
if ! check_data_dir; then
  mkdir $APP_DATA_DIR
  ret=$?
  if [ $ret ]; then
    printf "Creating data directory... %s\n" "${GREEN}$SUCCESS${END}";
  else
    printf "Creating data directory... %s... could not create the data directory, exiting.\n" "${RED}$ERROR${END}";
    exit $ret;
  fi;
fi;

# Check the db file exists - if not then generate it.
if ! check_db_file; then
  if ! generate_db; then
    printf "... fatal error, could not generate the database file, exiting.\n";
    exit $?
  fi;
fi
