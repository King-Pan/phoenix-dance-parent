CREATE TABLE crm.sys_permission
(
    permission_id bigint(20) PRIMARY KEY NOT NULL AUTO_INCREMENT,
    create_time datetime,
    description varchar(2000),
    expression varchar(255),
    icon varchar(255),
    order_num int(11),
    parent_id bigint(20),
    parent_name varchar(255),
    permission_name varchar(100),
    permission_type varchar(1),
    status varchar(255),
    update_time datetime,
    url varchar(256)
);
INSERT INTO crm.sys_permission (permission_id, create_time, description, expression, icon, order_num, parent_id, parent_name, permission_name, permission_type, status, update_time, url) VALUES (1, '2018-07-12 08:43:12', '快速开发系统', '', '', 1, null, '', '快速开发系统', '0', '1', null, '/');
INSERT INTO crm.sys_permission (permission_id, create_time, description, expression, icon, order_num, parent_id, parent_name, permission_name, permission_type, status, update_time, url) VALUES (2, '2018-07-12 08:59:50', null, '', 'fa fa-cog', 1, 1, '快速开发系统', '系统管理', '1', '1', '2018-07-18 17:13:03', '');
INSERT INTO crm.sys_permission (permission_id, create_time, description, expression, icon, order_num, parent_id, parent_name, permission_name, permission_type, status, update_time, url) VALUES (3, '2018-07-18 11:40:37', null, 'permission:view', null, 2, 2, '系统管理', '资源管理', null, '1', null, '/permission/');
INSERT INTO crm.sys_permission (permission_id, create_time, description, expression, icon, order_num, parent_id, parent_name, permission_name, permission_type, status, update_time, url) VALUES (4, '2018-07-18 11:42:14', '', 'user:view', 'fa fa-user fa-fw', 3, 2, '系统管理', '用户管理', '0', '1', '2018-07-18 15:28:42', '/user/');
INSERT INTO crm.sys_permission (permission_id, create_time, description, expression, icon, order_num, parent_id, parent_name, permission_name, permission_type, status, update_time, url) VALUES (5, '2018-07-18 12:34:23', null, 'permission:add', '', 1, 3, '资源管理', '添加资源', null, '1', null, '');
INSERT INTO crm.sys_permission (permission_id, create_time, description, expression, icon, order_num, parent_id, parent_name, permission_name, permission_type, status, update_time, url) VALUES (6, '2018-07-18 12:34:23', null, 'permission:delete', '', 3, 3, '资源管理', '删除资源', null, '1', null, '');
INSERT INTO crm.sys_permission (permission_id, create_time, description, expression, icon, order_num, parent_id, parent_name, permission_name, permission_type, status, update_time, url) VALUES (7, '2018-07-18 12:34:23', null, 'permission:modify', '', 2, 3, '资源管理', '修改资源', null, '1', null, '');
INSERT INTO crm.sys_permission (permission_id, create_time, description, expression, icon, order_num, parent_id, parent_name, permission_name, permission_type, status, update_time, url) VALUES (8, '2018-07-18 15:58:03', null, 'user:add', '', 1, 4, '用户管理', '新增用户', '2', '1', null, '');