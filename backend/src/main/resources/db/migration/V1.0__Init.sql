CREATE TABLE employees (
    id          UUID                        NOT NULL DEFAULT gen_random_uuid(),
    first_name  varchar(255)                NOT NULL,
    last_name   varchar(255)                NOT NULL,
    birthday    timestamp without time zone NOT NULL,
    start_time  time without time zone      NOT NULL,
    end_time    time without time zone      NOT NULL,
    lunch_start time without time zone,
    lunch_end   time without time zone,
    work_days   smallint                    NOT NULL,
    off_days    smallint                    NOT NULL,
    CONSTRAINT pk_employees PRIMARY KEY (id)
);

CREATE TABLE free_time (
   id          UUID                        NOT NULL DEFAULT gen_random_uuid(),
   employee_id UUID                        NOT NULL,
   free_time  time without time zone      NOT NULL,
   CONSTRAINT pk_free_time PRIMARY KEY (id),
   CONSTRAINT fk_free_time_to_employees FOREIGN KEY (employee_id) REFERENCES employees (id)
);

CREATE TABLE clients (
   id         UUID         NOT NULL DEFAULT gen_random_uuid(),
   first_name varchar(255) NOT NULL,
   last_name  varchar(255),
   phone      bigint       NOT NULL UNIQUE,
   chat_id    varchar(50),
   sent_code  varchar(10),
   CONSTRAINT pk_clients PRIMARY KEY (id)
);

CREATE TABLE services (
     id           UUID     NOT NULL DEFAULT gen_random_uuid(),
     name_service TEXT     NOT NULL,
     description  TEXT,
     time_service smallint NOT NULL,
     CONSTRAINT pk_services PRIMARY KEY (id)
);

CREATE TABLE appointments (
      id          UUID                        NOT NULL DEFAULT gen_random_uuid(),
      type        varchar(50)                 NOT NULL,
      client_id   UUID                        NOT NULL,
      employee_id UUID                        NOT NULL,
      service_id  UUID                        NOT NULL,
      start_time  timestamp without time zone NOT NULL,
      end_time    timestamp without time zone NOT NULL,
      CONSTRAINT pk_appointments              PRIMARY KEY (id),
      CONSTRAINT fk_appointments_to_clients   FOREIGN KEY (client_id)   REFERENCES clients (id),
      CONSTRAINT fk_appointments_to_employees FOREIGN KEY (employee_id) REFERENCES employees (id),
      CONSTRAINT fk_appointments_to_services  FOREIGN KEY (service_id)  REFERENCES services (id)
);

CREATE TABLE employee_skill (
      service_id  UUID NOT NULL,
      employee_id UUID NOT NULL,
      CONSTRAINT fk_employee_skill_to_services  FOREIGN KEY (service_id)  REFERENCES services (id),
      CONSTRAINT fk_employee_skill_to_employees FOREIGN KEY (employee_id) REFERENCES employees (id)
);