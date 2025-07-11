-- Insert default onboarding configuration
-- Page 2: About Me and Address components
-- Page 3: Birthdate component

INSERT IGNORE INTO onboarding_config (page_number, component_name, created_at) VALUES 
(2, 'ABOUT_ME', NOW()),
(2, 'ADDRESS', NOW()),
(3, 'BIRTHDATE', NOW());