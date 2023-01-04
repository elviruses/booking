INSERT INTO employee_skill (service_id, employee_id)
VALUES (
        (SELECT s.id FROM services s WHERE s.name_service = 'Маникюр'),
        (SELECT e.id FROM employees e WHERE e.first_name = 'IVAN')
        );
INSERT INTO employee_skill (service_id, employee_id)
VALUES (
           (SELECT s.id FROM services s WHERE s.name_service = 'Стрижка'),
           (SELECT e.id FROM employees e WHERE e.first_name = 'PETR')
       );