CREATE TABLE crm.sys_user_role
(
    user_id bigint(20) NOT NULL,
    role_id bigint(20) NOT NULL,
    CONSTRAINT `PRIMARY` PRIMARY KEY (user_id, role_id),
    CONSTRAINT FKb40xxfch70f5qnyfw8yme1n1s FOREIGN KEY (user_id) REFERENCES sys_user (user_id),
    CONSTRAINT FKhh52n8vd4ny9ff4x9fb8v65qx FOREIGN KEY (role_id) REFERENCES sys_role (role_id)
);
CREATE INDEX FKhh52n8vd4ny9ff4x9fb8v65qx ON crm.sys_user_role (role_id);
INSERT INTO crm.sys_user_role (user_id, role_id) VALUES (1, 1);
INSERT INTO crm.sys_user_role (user_id, role_id) VALUES (16, 1);
INSERT INTO crm.sys_user_role (user_id, role_id) VALUES (1, 2);
INSERT INTO crm.sys_user_role (user_id, role_id) VALUES (16, 2);