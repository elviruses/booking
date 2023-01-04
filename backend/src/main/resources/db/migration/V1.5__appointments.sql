INSERT INTO appointments (id, type, client_id, employee_id, service_id, start_time, end_time)
VALUES (
        DEFAULT,
        'CLIENT',
        (SELECT c.id FROM clients c WHERE c.first_name = 'КЛИЕНТ'),
        (SELECT e.id FROM employees e WHERE e.first_name = 'IVAN'),
        (SELECT s.id FROM services s WHERE s.name_service = 'Маникюр'),
        '2023-01-04 16:00:00',
        '2023-01-04 18:00:00'
        );
INSERT INTO appointments (id, type, client_id, employee_id, service_id, start_time, end_time)
VALUES (
           DEFAULT,
           'CLIENT',
           (SELECT c.id FROM clients c WHERE c.first_name = 'КЛИЕНТ2'),
           (SELECT e.id FROM employees e WHERE e.first_name = 'PETR'),
           (SELECT s.id FROM services s WHERE s.name_service = 'Стрижка'),
           '2023-01-04 15:00:00',
           '2023-01-04 16:00:00'
       );