DROP TRIGGER IF EXISTS generate_username;
DROP TRIGGER IF EXISTS update_username;
DROP TRIGGER IF EXISTS generate_school_code;

DELIMITER $$

CREATE TRIGGER generate_username BEFORE INSERT
  ON schoolportal.users
  FOR EACH ROW
  BEGIN
    SET @school_code = (select code from schools WHERE id = NEW.school_id);
    IF NEW.username LIKE CONCAT(@school_code, '%') THEN
      SET @last_username = (select username FROM users WHERE username LIKE NEW.username ORDER BY username desc LIMIT 1);
      IF @last_username IS NULL THEN
        SET @last_username = CONCAT(NEW.username, '000');
      END IF;
      SET NEW.username = @last_username + 1;
    END IF;
  END;

CREATE TRIGGER update_username BEFORE UPDATE
ON schoolportal.users
FOR EACH ROW
  BEGIN
    SET @school_code = (select code from schools WHERE id = NEW.school_id);
    IF NEW.username != OLD.username THEN
      SET @last_username = (select username FROM users WHERE username LIKE NEW.username ORDER BY username desc LIMIT 1);
      IF @last_username IS NULL THEN
        SET @last_username = CONCAT(NEW.username, '000');
      END IF;
      SET NEW.username = @last_username + 1;
    END IF;
  END;

CREATE TRIGGER generate_school_code BEFORE INSERT
  ON schoolportal.schools
  FOR EACH ROW
  BEGIN
    SET @last_code = (SELECT code FROM schools ORDER BY code DESC LIMIT 1);
    IF @last_code IS NULL THEN
      SET @last_code = '100';
    END IF;
    SET NEW.code = @last_code + 1;
  END;

DELIMITER ;