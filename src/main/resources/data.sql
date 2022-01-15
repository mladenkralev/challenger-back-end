INSERT INTO `users` VALUES (0,true,'test@abv.bg','$2a$10$PdLu3gaQSeZNvTJtz5qdbOmsTrJQ4bTHIr40mNgc//Fa8OW7b2Vla','ROLE_ADMIN','test');
INSERT INTO `challenges` (`id`, `description`, `end_date`, `occurrences`, `start_date`, `title`) VALUES ('1', 'Read a book every week!', '2022-01-31 00:00:00.000000', 'DAY', '2022-01-05 00:00:00.000000', 'Knowledge is power!');
INSERT INTO `challenges` (`id`, `description`, `end_date`, `occurrences`, `start_date`, `title`) VALUES ('2', 'Train every day for at least 20 minutes', '2022-01-31 00:00:00.000000', 'DAY', '2022-01-05 00:00:00.000000', 'You are a MACHINE!');

INSERT INTO `assigned_to_user_challenges` (`user_id`, `challenge_id`) VALUES ('0', '1');
INSERT INTO `assigned_to_user_challenges` (`user_id`, `challenge_id`) VALUES ('0', '2');

INSERT INTO created_by_user_challenges (`user_id`, `challenge_id`) VALUES ('0', '1');
INSERT INTO created_by_user_challenges (`user_id`, `challenge_id`) VALUES ('0', '2');
