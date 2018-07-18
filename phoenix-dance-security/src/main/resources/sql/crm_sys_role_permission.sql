CREATE TABLE crm.sys_role_permission
(
    role_id bigint(20) NOT NULL,
    permission_id bigint(20) NOT NULL,
    CONSTRAINT `PRIMARY` PRIMARY KEY (role_id, permission_id),
    CONSTRAINT FK9q28ewrhntqeipl1t04kh1be7 FOREIGN KEY (role_id) REFERENCES sys_role (role_id),
    CONSTRAINT FKomxrs8a388bknvhjokh440waq FOREIGN KEY (permission_id) REFERENCES sys_permission (permission_id)
);
CREATE INDEX FKomxrs8a388bknvhjokh440waq ON crm.sys_role_permission (permission_id);
INSERT INTO crm.sys_role_permission (role_id, permission_id) VALUES (1, 4);
INSERT INTO crm.sys_role_permission (role_id, permission_id) VALUES (1, 5);
INSERT INTO crm.sys_role_permission (role_id, permission_id) VALUES (1, 6);
INSERT INTO crm.sys_role_permission (role_id, permission_id) VALUES (1, 7);