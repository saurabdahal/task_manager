CREATE TABLE IF NOT EXISTS goals (
                                     id INT NOT NULL AUTO_INCREMENT,
                                     name VARCHAR(255),
                                     purpose TEXT,
                                     completion DOUBLE,
                                     serial_id VARCHAR(70),
                                     deadline DATE,
                                     created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                     updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                     PRIMARY KEY (id)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS initiative (
                                          id INT NOT NULL AUTO_INCREMENT,
                                          name VARCHAR(50),
                                          serial_id VARCHAR(70),
                                          goal_id INT,
                                          completion DOUBLE,
                                          weight FLOAT,
                                          created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                          due_date DATE,
                                          updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                          PRIMARY KEY (id),
                                          CONSTRAINT fk_initiative_id FOREIGN KEY (goal_id) REFERENCES goals(id) ON DELETE CASCADE
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS task (
                                    id INT NOT NULL AUTO_INCREMENT,
                                    name VARCHAR(255),
                                    serial_id VARCHAR(70),
                                    description TEXT,
                                    initiative_id INT,
                                    completed BOOLEAN,
                                    weight FLOAT,
                                    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                    sequence INT,
                                    due_date DATE,
                                    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                    PRIMARY KEY (id),
                                    CONSTRAINT fk_task_id FOREIGN KEY (initiative_id) REFERENCES initiative(id) ON DELETE CASCADE
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS money (
                                     id INT NOT NULL AUTO_INCREMENT,
                                     i_have FLOAT,
                                     serial_id VARCHAR(255),
                                     category VARCHAR(255),
                                     yet_to_occur BOOLEAN,
                                     amount FLOAT,
                                     type INT,
                                     transaction_date DATE,
                                     created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                     updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                     PRIMARY KEY (id)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS news (
                                    id INT NOT NULL AUTO_INCREMENT,
                                    remark VARCHAR(50),
                                    serial_id VARCHAR(70),
                                    description TEXT,
                                    type INT,
                                    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                    occurred_date DATE,
                                    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                    PRIMARY KEY (id)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS job (
                                   id INT NOT NULL AUTO_INCREMENT,
                                   title VARCHAR(50),
                                   serial_id VARCHAR(70),
                                   company VARCHAR(50),
                                   link TEXT,
                                   applied_date DATE,
                                   response BOOLEAN,
                                   interview BOOLEAN,
                                   jd TEXT,
                                   created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                   updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                   PRIMARY KEY (id)
) ENGINE=InnoDB;
