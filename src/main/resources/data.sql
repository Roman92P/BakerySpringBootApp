INSERT INTO role (id ,name) VALUES (1,'ROLE_ADMIN');
INSERT INTO role (id,name) VALUES (2,'ROLE_USER');
INSERT INTO role (id,name) VALUES (3,'ROLE_GUEST');
INSERT INTO ingredients (liters, name, quantity, weight) VALUES (20.00,'mleko',0,0);
INSERT INTO ingredients (liters, name, quantity, weight) VALUES (0,'jajka', 10, 0);
INSERT INTO ingredients (liters, name, quantity, weight) VALUES (0,'mąka',0,20.00);
INSERT INTO ingredients (liters, name, quantity, weight) VALUES (1,'woda',0,0);
INSERT INTO recipes (name) VALUES ('chleb');
INSERT INTO recipes (name) VALUES ('bułka');
INSERT INTO recipe_item(ingredient_quantity, ingredients_id, recipe_id) VALUES (2.00,1,1);
INSERT INTO recipe_item(ingredient_quantity, ingredients_id, recipe_id) VALUES (2.00,2,1);
INSERT INTO recipe_item(ingredient_quantity, ingredients_id, recipe_id) VALUES (2.00,3,1);
INSERT INTO recipe_item(ingredient_quantity, ingredients_id, recipe_id) VALUES (2.00,1,2);
INSERT INTO recipe_item(ingredient_quantity, ingredients_id, recipe_id) VALUES (2.00,2,2);
INSERT INTO recipe_item(ingredient_quantity, ingredients_id, recipe_id) VALUES (2.00,3,2);
INSERT INTO products(name, price, recipe_id)VALUES ('Gruziński chleb żytni', 4.50, 1);
INSERT INTO products(name, price, recipe_id)VALUES ('Chleb polski', 2.56, 1);
INSERT INTO products(name, price, recipe_id)VALUES ('Chleb na maślance', 1.50, 1);
INSERT INTO products(name, price, recipe_id)VALUES ('Chleb górski', 5.50, 1);
INSERT INTO products(name, price, recipe_id)VALUES ('Chleb wrocławski', 8.55, 1);
INSERT INTO products(name, price, recipe_id)VALUES ('Chleb jakiś', 4.49, 1);
INSERT INTO products(name, price, recipe_id)VALUES ('Chleb wiejski', 3.15, 1);
INSERT INTO products(name, price, recipe_id)VALUES ('Chleb bułka', 4.50, 1);
INSERT INTO products(name, price, recipe_id)VALUES ('Chleb ciabata', 4.50, 1);
INSERT INTO stock(product_name, product_price, product_quantity) VALUES ('Gruziński chleb żytni',4.50,3);
INSERT INTO persons(id,active, email, enabled, first_name, last_name, password, username) VALUES (1,true,'forcodeemailroman@gmail.com',true,'Admin','Admin','$2a$10$scw/kihZM9TA8/9JshJnVuc38aqBUI2j75gWsIfuXfTkoIx3Lrc6K','Admin');
INSERT INTO persons(id,active, email, enabled, first_name, last_name, password, username) VALUES (2,true,'test@test.com',true,'User','User','$2a$10$scw/kihZM9TA8/9JshJnVuc38aqBUI2j75gWsIfuXfTkoIx3Lrc6K','Usero');
INSERT INTO user_role(person_id, role_id) values (1, 1);
INSERT INTO user_role(person_id, role_id) values (2, 2);
INSERT INTO manufactureds(created_on,finalized_work_order,updated_on,user_id)VALUES('2020-11-15 08:44:59.236143',true,'2020-11-15 08:46:59.236143',1);
INSERT INTO manufacture_items( quantity, manufactured_id, product_id) VALUES (3, 1, 1);
INSERT INTO bill(created_on, real_sum_of_order, sum_of_customer_order, updated_on, user_id) VALUES ('2020-01-06 08:44:59.236143',11.50,11.50,'2020-01-06 08:44:59.236143',1);
INSERT INTO bill(created_on, real_sum_of_order, sum_of_customer_order, updated_on, user_id) VALUES ('2020-10-01 08:44:59.236143',1.50,1.50,'2020-10-01 08:44:59.236143',1);
INSERT INTO bill(created_on, real_sum_of_order, sum_of_customer_order, updated_on, user_id) VALUES ('2020-10-01 08:44:59.236143',2.50,2.50,'2020-10-01 08:44:59.236143',1);
INSERT INTO bill(created_on, real_sum_of_order, sum_of_customer_order, updated_on, user_id) VALUES ('2020-10-02 08:44:59.236143',12.50,12.50,'2020-10-02 08:44:59.236143',1);
INSERT INTO bill(created_on, real_sum_of_order, sum_of_customer_order, updated_on, user_id) VALUES ('2020-10-03 08:44:59.236143',12.50,12.50,'2020-10-03 08:44:59.236143',1);
INSERT INTO bill(created_on, real_sum_of_order, sum_of_customer_order, updated_on, user_id) VALUES ('2020-10-03 08:44:59.236143',12.50,12.50,'2020-10-03 08:44:59.236143',1);
INSERT INTO bill(created_on, real_sum_of_order, sum_of_customer_order, updated_on, user_id) VALUES ('2020-10-04 08:44:59.236143',12.50,12.50,'2020-10-04 08:44:59.236143',1);
INSERT INTO bill(created_on, real_sum_of_order, sum_of_customer_order, updated_on, user_id) VALUES ('2020-10-04 08:44:59.236143',12.50,12.50,'2020-10-04 08:44:59.236143',1);
INSERT INTO bill(created_on, real_sum_of_order, sum_of_customer_order, updated_on, user_id) VALUES ('2020-10-05 08:44:59.236143',12.50,12.50,'2020-10-05 08:44:59.236143',1);
INSERT INTO bill(created_on, real_sum_of_order, sum_of_customer_order, updated_on, user_id) VALUES ('2020-10-15 08:44:59.236143',12.50,12.50,'2020-10-15 08:44:59.236143',1);
INSERT INTO bill(created_on, real_sum_of_order, sum_of_customer_order, updated_on, user_id) VALUES ('2020-10-15 08:44:59.236143',12.50,12.50,'2020-10-15 08:44:59.236143',1);
INSERT INTO bill(created_on, real_sum_of_order, sum_of_customer_order, updated_on, user_id) VALUES ('2020-10-17 08:44:59.236143',12.50,12.50,'2020-10-17 08:44:59.236143',1);
INSERT INTO bill(created_on, real_sum_of_order, sum_of_customer_order, updated_on, user_id) VALUES ('2020-10-18 08:44:59.236143',12.50,12.50,'2020-10-18 08:44:59.236143',1);
INSERT INTO bill(created_on, real_sum_of_order, sum_of_customer_order, updated_on, user_id) VALUES ('2020-10-19 08:44:59.236143',12.50,12.50,'2020-10-19 08:44:59.236143',1);
INSERT INTO bill(created_on, real_sum_of_order, sum_of_customer_order, updated_on, user_id) VALUES ('2020-10-20 08:44:59.236143',12.50,12.50,'2020-10-20 08:44:59.236143',1);
INSERT INTO bill(created_on, real_sum_of_order, sum_of_customer_order, updated_on, user_id) VALUES ('2020-10-21 08:44:59.236143',12.50,12.50,'2020-10-21 08:44:59.236143',1);
INSERT INTO bill(created_on, real_sum_of_order, sum_of_customer_order, updated_on, user_id) VALUES ('2020-10-22 08:44:59.236143',12.50,12.50,'2020-10-22 08:44:59.236143',1);
INSERT INTO bill(created_on, real_sum_of_order, sum_of_customer_order, updated_on, user_id) VALUES ('2020-10-22 08:44:59.236143',12.50,12.50,'2020-10-22 08:44:59.236143',1);
INSERT INTO bill(created_on, real_sum_of_order, sum_of_customer_order, updated_on, user_id) VALUES ('2020-10-22 08:44:59.236143',12.50,12.50,'2020-10-22 08:44:59.236143',1);
INSERT INTO bill(created_on, real_sum_of_order, sum_of_customer_order, updated_on, user_id) VALUES ('2020-10-23 08:44:59.236143',12.50,12.50,'2020-10-23 08:44:59.236143',1);
INSERT INTO bill(created_on, real_sum_of_order, sum_of_customer_order, updated_on, user_id) VALUES ('2020-10-24 08:44:59.236143',12.50,12.50,'2020-10-24 08:44:59.236143',1);
INSERT INTO bill(created_on, real_sum_of_order, sum_of_customer_order, updated_on, user_id) VALUES ('2020-10-25 08:44:59.236143',12.50,12.50,'2020-10-25 08:44:59.236143',1);
INSERT INTO bill(created_on, real_sum_of_order, sum_of_customer_order, updated_on, user_id) VALUES ('2020-10-26 08:44:59.236143',12.50,12.50,'2020-10-26 08:44:59.236143',1);
INSERT INTO bill(created_on, real_sum_of_order, sum_of_customer_order, updated_on, user_id) VALUES ('2020-10-27 08:44:59.236143',12.50,12.50,'2020-10-27 08:44:59.236143',1);
INSERT INTO bill(created_on, real_sum_of_order, sum_of_customer_order, updated_on, user_id) VALUES ('2020-10-28 08:44:59.236143',12.50,12.50,'2020-10-28 08:44:59.236143',1);
INSERT INTO bill(created_on, real_sum_of_order, sum_of_customer_order, updated_on, user_id) VALUES ('2020-10-29 08:44:59.236143',12.50,12.50,'2020-10-29 08:44:59.236143',1);
INSERT INTO bill(created_on, real_sum_of_order, sum_of_customer_order, updated_on, user_id) VALUES ('2020-10-30 08:44:59.236143',12.50,12.50,'2020-10-30 08:44:59.236143',1);
INSERT INTO bill(created_on, real_sum_of_order, sum_of_customer_order, updated_on, user_id) VALUES ('2020-11-01 08:44:59.236143',12.50,12.50,'2020-11-01 08:44:59.236143',1);
INSERT INTO bill(created_on, real_sum_of_order, sum_of_customer_order, updated_on, user_id) VALUES ('2020-11-02 08:44:59.236143',12.50,12.50,'2020-11-02 08:44:59.236143',1);
INSERT INTO bill(created_on, real_sum_of_order, sum_of_customer_order, updated_on, user_id) VALUES ('2020-11-02 08:44:59.236143',12.50,12.50,'2020-11-02 08:44:59.236143',1);
INSERT INTO bill(created_on, real_sum_of_order, sum_of_customer_order, updated_on, user_id) VALUES ('2020-11-04 08:44:59.236143',12.50,12.50,'2020-11-04 08:44:59.236143',1);
INSERT INTO bill(created_on, real_sum_of_order, sum_of_customer_order, updated_on, user_id) VALUES ('2020-11-05 08:44:59.236143',12.50,12.50,'2020-11-05 08:44:59.236143',1);
INSERT INTO bill(created_on, real_sum_of_order, sum_of_customer_order, updated_on, user_id) VALUES ('2020-11-06 08:44:59.236143',12.50,12.50,'2020-11-06 08:44:59.236143',1);
INSERT INTO bill(created_on, real_sum_of_order, sum_of_customer_order, updated_on, user_id) VALUES ('2020-11-07 08:44:59.236143',12.50,12.50,'2020-11-07 08:44:59.236143',1);
INSERT INTO bill(created_on, real_sum_of_order, sum_of_customer_order, updated_on, user_id) VALUES ('2020-11-08 08:44:59.236143',12.50,12.50,'2020-11-08 08:44:59.236143',1);
INSERT INTO bill(created_on, real_sum_of_order, sum_of_customer_order, updated_on, user_id) VALUES ('2020-12-01 08:44:59.236143',3.50,3.50,'2020-12-01 08:44:59.236143',1);
INSERT INTO bill(created_on, real_sum_of_order, sum_of_customer_order, updated_on, user_id) VALUES ('2020-12-02 08:44:59.236143',10.50,10.50,'2020-12-02 08:44:59.236143',1);
INSERT INTO bill(created_on, real_sum_of_order, sum_of_customer_order, updated_on, user_id) VALUES ('2020-12-02 08:44:59.236143',3.50,3.50,'2020-12-02 08:44:59.236143',1);
INSERT INTO bill(created_on, real_sum_of_order, sum_of_customer_order, updated_on, user_id) VALUES ('2020-12-03 08:44:59.236143',0.50,0.50,'2020-12-03 08:44:59.236143',1);
INSERT INTO bill(created_on, real_sum_of_order, sum_of_customer_order, updated_on, user_id) VALUES ('2020-12-03 08:44:59.236143',4.50,4.50,'2020-12-03 08:44:59.236143',1);
INSERT INTO bill(created_on, real_sum_of_order, sum_of_customer_order, updated_on, user_id) VALUES ('2020-12-05 08:44:59.236143',8.50,8.50,'2020-12-05 08:44:59.236143',1);
INSERT INTO bill(created_on, real_sum_of_order, sum_of_customer_order, updated_on, user_id) VALUES ('2020-12-06 08:44:59.236143',11.50,11.50,'2020-12-06 08:44:59.236143',1);
INSERT INTO bill_item(sold_product_name,sold_product_price,sold_product_quantity,bill_id) VALUES ('Gruziński chleb żytni',4.50, 1, 1);
INSERT INTO bill_item(sold_product_name,sold_product_price,sold_product_quantity,bill_id) VALUES ('Chleb polski',2.56, 1, 1);
INSERT INTO bill_item(sold_product_name,sold_product_price,sold_product_quantity,bill_id) VALUES ('Gruziński chleb żytni',4.50, 1, 2);
INSERT INTO bill_item(sold_product_name,sold_product_price,sold_product_quantity,bill_id) VALUES ('Chleb polski',2.56, 1, 2);
INSERT INTO bill_item(sold_product_name,sold_product_price,sold_product_quantity,bill_id) VALUES ('Gruziński chleb żytni',4.50, 1, 3);
INSERT INTO bill_item(sold_product_name,sold_product_price,sold_product_quantity,bill_id) VALUES ('Chleb polski',2.56, 1, 3);
INSERT INTO bill_item(sold_product_name,sold_product_price,sold_product_quantity,bill_id) VALUES ('Chleb na maślance',1.50, 1, 4);
INSERT INTO bill_item(sold_product_name,sold_product_price,sold_product_quantity,bill_id) VALUES ('Chleb na maślance',1.50, 1, 4);
INSERT INTO bill_item(sold_product_name,sold_product_price,sold_product_quantity,bill_id) VALUES ('Chleb na maślance',1.50, 1, 5);
INSERT INTO bill_item(sold_product_name,sold_product_price,sold_product_quantity,bill_id) VALUES ('Chleb na maślance',1.50, 1, 5);
INSERT INTO bill_item(sold_product_name,sold_product_price,sold_product_quantity,bill_id) VALUES ('Chleb górski',5.50, 1, 6);
INSERT INTO bill_item(sold_product_name,sold_product_price,sold_product_quantity,bill_id) VALUES ('Chleb górski',5.50, 1, 6);
INSERT INTO bill_item(sold_product_name,sold_product_price,sold_product_quantity,bill_id) VALUES ('Gruziński chleb żytni', 4.50, 1, 7);
INSERT INTO bill_item(sold_product_name,sold_product_price,sold_product_quantity,bill_id) VALUES ('Chleb polski',2.56, 1, 7);
INSERT INTO bill_item(sold_product_name,sold_product_price,sold_product_quantity,bill_id) VALUES ('Gruziński chleb żytni', 4.50, 1, 8);
INSERT INTO bill_item(sold_product_name,sold_product_price,sold_product_quantity,bill_id) VALUES ('Chleb polski',2.56, 1, 8);
INSERT INTO bill_item(sold_product_name,sold_product_price,sold_product_quantity,bill_id) VALUES ('Chleb wrocławski',5.50, 1, 9);
INSERT INTO bill_item(sold_product_name,sold_product_price,sold_product_quantity,bill_id) VALUES ('Chleb polski',2.56, 1, 10);
INSERT INTO bill_item(sold_product_name,sold_product_price,sold_product_quantity,bill_id) VALUES ('Chleb polski',2.56, 1, 11);
INSERT INTO bill_item(sold_product_name,sold_product_price,sold_product_quantity,bill_id) VALUES ('Gruziński chleb żytni',4.50, 1, 11);
INSERT INTO lost_products(created_on, value, stock_id) VALUES ('2020-10-01 08:44:59.236143',1,1);
INSERT INTO lost_products(created_on, value, stock_id) VALUES ('2020-10-01 08:44:59.236143',2,1);
INSERT INTO lost_products(created_on, value, stock_id) VALUES ('2020-10-01 08:44:59.236143',3,1);
INSERT INTO lost_products(created_on, value, stock_id) VALUES ('2020-10-01 08:44:59.236143',4,1);
INSERT INTO lost_products(created_on, value, stock_id) VALUES ('2020-10-10 08:44:59.236143',5,1);
INSERT INTO lost_products(created_on, value, stock_id) VALUES ('2020-10-12 08:44:59.236143',6,1);
INSERT INTO lost_products(created_on, value, stock_id) VALUES ('2020-10-14 08:44:59.236143',7,1);
INSERT INTO lost_products(created_on, value, stock_id) VALUES ('2020-10-16 08:44:59.236143',1,1);
INSERT INTO lost_products(created_on, value, stock_id) VALUES ('2020-10-18 08:44:59.236143',1,1);
INSERT INTO lost_products(created_on, value, stock_id) VALUES ('2020-10-20 08:44:59.236143',1,1);
INSERT INTO lost_products(created_on, value, stock_id) VALUES ('2020-10-22 08:44:59.236143',2,1);
INSERT INTO lost_products(created_on, value, stock_id) VALUES ('2020-10-24 08:44:59.236143',3,1);
INSERT INTO lost_products(created_on, value, stock_id) VALUES ('2020-10-26 08:44:59.236143',5,1);
INSERT INTO lost_products(created_on, value, stock_id) VALUES ('2020-10-28 08:44:59.236143',3,1);
INSERT INTO lost_products(created_on, value, stock_id) VALUES ('2020-10-30 08:44:59.236143',10,1);
INSERT INTO lost_products(created_on, value, stock_id) VALUES ('2020-11-01 08:44:59.236143',9,1);
INSERT INTO lost_products(created_on, value, stock_id) VALUES ('2020-11-01 08:44:59.236143',8,1);
INSERT INTO lost_products(created_on, value, stock_id) VALUES ('2020-11-01 08:44:59.236143',7,1);
INSERT INTO lost_products(created_on, value, stock_id) VALUES ('2020-11-01 08:44:59.236143',6,1);
INSERT INTO lost_products(created_on, value, stock_id) VALUES ('2020-11-09 08:44:59.236143',5,1);
INSERT INTO lost_products(created_on, value, stock_id) VALUES ('2020-11-11 08:44:59.236143',1,1);
INSERT INTO lost_products(created_on, value, stock_id) VALUES ('2020-11-13 08:44:59.236143',2,1);
INSERT INTO lost_products(created_on, value, stock_id) VALUES ('2020-11-15 08:44:59.236143',3,1);
INSERT INTO lost_products(created_on, value, stock_id) VALUES ('2020-11-17 08:44:59.236143',4,1);
INSERT INTO lost_products(created_on, value, stock_id) VALUES ('2020-11-19 08:44:59.236143',8,1);
INSERT INTO lost_products(created_on, value, stock_id) VALUES ('2020-11-21 08:44:59.236143',5,1);
INSERT INTO lost_products(created_on, value, stock_id) VALUES ('2020-11-23 08:44:59.236143',8,1);
INSERT INTO lost_products(created_on, value, stock_id) VALUES ('2020-11-25 08:44:59.236143',3,1);
INSERT INTO lost_products(created_on, value, stock_id) VALUES ('2020-11-27 08:44:59.236143',9,1);
INSERT INTO lost_products(created_on, value, stock_id) VALUES ('2020-11-29 08:44:59.236143',2,1);