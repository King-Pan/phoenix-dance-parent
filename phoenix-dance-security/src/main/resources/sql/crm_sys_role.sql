CREATE TABLE crm.sys_role
(
    role_id bigint(20) PRIMARY KEY NOT NULL AUTO_INCREMENT,
    create_time datetime,
    remark varchar(2000),
    role_code varchar(56),
    role_name varchar(256),
    status varchar(10),
    update_time datetime
);
INSERT INTO crm.sys_role (role_id, create_time, remark, role_code, role_name, status, update_time) VALUES (1, '2018-07-11 04:36:50', '超级管理员221', 'ADMIN', '超级管理员', '1', '2018-07-11 12:32:01');
INSERT INTO crm.sys_role (role_id, create_time, remark, role_code, role_name, status, update_time) VALUES (2, '2018-07-11 12:34:02', '角色管理员--卡卡卡2', 'ROLE_MGR', '角色管理员', '1', '2018-07-11 12:34:10');
INSERT INTO crm.sys_role (role_id, create_time, remark, role_code, role_name, status, update_time) VALUES (3, '2018-07-11 12:52:03', '用户管理员--HR专用', 'USER_MGR', '用户管理员HR', '1', '2018-07-11 13:05:27');
INSERT INTO crm.sys_role (role_id, create_time, remark, role_code, role_name, status, update_time) VALUES (4, '2018-07-11 17:55:35', 'A1', 'A1', 'A1', '1', null);
INSERT INTO crm.sys_role (role_id, create_time, remark, role_code, role_name, status, update_time) VALUES (5, '2018-07-11 17:55:41', 'A2', 'A2', 'A2', '1', null);
INSERT INTO crm.sys_role (role_id, create_time, remark, role_code, role_name, status, update_time) VALUES (6, '2018-07-11 17:55:50', 'A3', 'A3', 'A3', '1', null);
INSERT INTO crm.sys_role (role_id, create_time, remark, role_code, role_name, status, update_time) VALUES (7, '2018-07-11 17:55:57', 'A4', 'A6', 'A4', '1', null);
INSERT INTO crm.sys_role (role_id, create_time, remark, role_code, role_name, status, update_time) VALUES (8, '2018-07-11 17:56:05', 'A4', 'A4', 'A5', '1', null);
INSERT INTO crm.sys_role (role_id, create_time, remark, role_code, role_name, status, update_time) VALUES (9, '2018-07-11 17:56:17', 'A7', 'A7', 'A6', '1', null);
INSERT INTO crm.sys_role (role_id, create_time, remark, role_code, role_name, status, update_time) VALUES (10, '2018-07-11 17:56:25', 'A7', 'A6', 'A7', '1', null);
INSERT INTO crm.sys_role (role_id, create_time, remark, role_code, role_name, status, update_time) VALUES (11, '2018-07-11 17:56:33', 'A8', 'A8', 'A8', '1', null);
INSERT INTO crm.sys_role (role_id, create_time, remark, role_code, role_name, status, update_time) VALUES (12, '2018-07-11 17:59:40', 'A9', 'A9', 'A9', '1', null);