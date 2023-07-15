DROP DATABASE IF EXISTS db_TrafficSystemMock;
CREATE DATABASE IF NOT EXISTS db_TrafficSystemMock;
USE db_TrafficSystemMock;

-- ####
-- -- SELECT table_name FROM information_schema.tables   WHERE table_schema = 'db_TrafficSystemMock';
-- SELECT concat('TRUNCATE TABLE `', TABLE_NAME, '`;')
--   FROM INFORMATION_SCHEMA.TABLES
--   WHERE table_schema = 'db_TrafficSystemMock';
--     -- AND TABLE_NAME LIKE 'inventory%';

SET FOREIGN_KEY_CHECKS=0;  -- turn off foreign key checks
-- TRUNCATE TABLE `hibernate_sequence`; TRUNCATE TABLE `id_generator`; 
TRUNCATE TABLE `ball`; TRUNCATE TABLE `entity_general`; TRUNCATE TABLE `location_generic`; TRUNCATE TABLE `map_file`; TRUNCATE TABLE `map_file_roadway_solid_road`; TRUNCATE TABLE `map_file_traffic_detector`; TRUNCATE TABLE `map_file_vehicle`; TRUNCATE TABLE `roadway_abstract`; TRUNCATE TABLE `roadway_abstract_roadway_dir_linker_component`; TRUNCATE TABLE `roadway_cross_point`; TRUNCATE TABLE `roadway_dir_linker_component`; TRUNCATE TABLE `roadway_generic_location_segment`; TRUNCATE TABLE `roadway_normal_point`; TRUNCATE TABLE `roadway_normal_segment`; TRUNCATE TABLE `roadway_objective_path`; TRUNCATE TABLE `roadway_point`; TRUNCATE TABLE `roadway_point_roadway_dir_linker_component`; TRUNCATE TABLE `roadway_segment`; TRUNCATE TABLE `roadway_segment_pseudo_begin`; TRUNCATE TABLE `roadway_solid_road`; TRUNCATE TABLE `traffic_detector`; TRUNCATE TABLE `traffic_detector_vehicle_info_traffic_detector_dto`; TRUNCATE TABLE `vehicle`; TRUNCATE TABLE `vehicle_info_traffic_detector_dto`; TRUNCATE TABLE `vehicle_inventory`; TRUNCATE TABLE `vehicle_inventory_vehicle`;
SET FOREIGN_KEY_CHECKS=1;  -- turn on foreign key checks



-- SELECT * FROM db_trafficsystemmock.hibernate_sequence;
SELECT * FROM db_trafficsystemmock.id_generator;
SELECT * FROM `entity_general`;

-- insert into db_trafficsystemmock.id_generator values (0);

-- ####
SELECT * FROM db_trafficsystemmock.roadway_abstract;
-- SELECT * FROM db_trafficsystemmock.roadway_solid_road;
SELECT * FROM db_trafficsystemmock.roadway_point;
-- SELECT * FROM db_trafficsystemmock.roadway_normal_point;
SELECT * FROM db_trafficsystemmock.roadway_segment;
SELECT * FROM db_trafficsystemmock.roadway_dir_linker_component;

SELECT * FROM db_trafficsystemmock.roadway_abstract_roadway_dir_linker_component;
SELECT * FROM db_trafficsystemmock.roadway_point_roadway_dir_linker_component;

-- ####
SELECT * FROM db_trafficsystemmock.map_file;

SELECT * FROM db_trafficsystemmock.map_file_roadway_solid_road;
SELECT * FROM db_trafficsystemmock.map_file_vehicle;

-- ####
SELECT * FROM db_trafficsystemmock.vehicle;

SELECT * FROM db_trafficsystemmock.vehicle_inventory;
SELECT * FROM db_trafficsystemmock.vehicle_inventory_vehicle;

-- ####
-- SELECT *, MM.id_sql as MM_id_sql, RR.id_sql as RR_id_sql 
-- FROM map_file as MM 
-- CROSS JOIN roadway_abstract as RR; 

-- SELECT *, RR.id_sql as RR_id_sql, DD.id_sql as DD_id_sql
-- FROM roadway_abstract as RR 
-- CROSS JOIN roadway_dir_linker_component as DD; 

SELECT *, RR.id_sql as RR_id_sql, DD.id_sql as DD_id_sql
FROM 
  roadway_abstract_roadway_dir_linker_component as ASO
  INNER JOIN roadway_abstract as RR 
    ON ASO.roadway_abstract_id_sql = RR.id_sql
  INNER JOIN roadway_dir_linker_component as DD 
    ON ASO.arr_roadway_dir_linker_component_id_sql = DD.id_sql;


-- ####

-- CREATE TEMPORARY TABLE temp_table
-- SELECT * FROM (
--     SELECT *, RR.id_sql as RR_id_sql, DD.id_sql as DD_id_sql
--     FROM roadway_abstract as RR 
--     INNER JOIN roadway_dir_linker_component as DD
-- ) as TP;
-- ALTER TABLE temp_table
-- DROP COLUMN id_sql;
-- SELECT * FROM temp_table;
-- drop TEMPORARY TABLE temp_table;


drop procedure if exists selectWithoutDupIdsql;
DELIMITER $$
CREATE PROCEDURE selectWithoutDupIdsql(
    IN tableName  VARCHAR(50),
	IN prependName VARCHAR(50)
)
BEGIN
     SET @t1 = CONCAT('CREATE TEMPORARY TABLE temp_table SELECT *, id_sql as ', prependName, '_id_sql FROM ', tableName);
     PREPARE stmtM FROM @t1;
     EXECUTE stmtM;
     DEALLOCATE PREPARE stmtM;
     
    ALTER TABLE temp_table
    DROP COLUMN id_sql;
    SELECT * FROM temp_table;
    drop TEMPORARY TABLE temp_table;
     
END $$
DELIMITER ;

-- SET @prependNameIn := "AA";
-- DECLARE prependName VARCHAR(50);
call selectWithoutDupIdsql('map_file', 'AA');

-- ####

-- DELIMITER $$
-- CREATE PROCEDURE loopOver()
-- BEGIN
--   DECLARE finished INTEGER DEFAULT 0;
--   DECLARE str_tableName varchar(255) DEFAULT "";

--   DEClARE cur_tableName CURSOR FOR 
--     SELECT table_name FROM information_schema.tables   WHERE table_schema = 'db_TrafficSystemMock';

--   DECLARE CONTINUE HANDLER 
--     FOR NOT FOUND SET finished = 1;

--   OPEN cur_tableName;

--   loop_tableName: LOOP
--     FETCH cur_tableName INTO str_tableName;
--     IF finished = 1 THEN
--       LEAVE loop_tableName;
--     END IF;
--     
--     -- Do Logic
--     
--   END LOOP loop_tableName;
--   
--   CLOSE cur_tableName;

-- END$$
-- DELIMITER ;

-- ####

-- SELECT * FROM `entity_general`
-- JOIN `event_sig` USING(id_sql)
-- LEFT JOIN `node_event` USING(id_sql)
-- LEFT JOIN `node_created_event` USING(id_sql)
-- LEFT JOIN `node_shape_size_changed_event` USING(id_sql)
-- LEFT JOIN `node_moved_event` USING(id_sql)
-- ORDER BY `creation_time` ASC, `id_sql` ASC;
