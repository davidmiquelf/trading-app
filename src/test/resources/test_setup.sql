
TRUNCATE quote CASCADE;
TRUNCATE trader RESTART IDENTITY CASCADE;
TRUNCATE account RESTART IDENTITY CASCADE;
TRUNCATE security_order RESTART IDENTITY CASCADE;

begin transaction;

INSERT INTO quote (ticker, last_price, bid_price, bid_size, ask_price, ask_size)
VALUES
  ('A', 1.1, 1.1, 1, 1.1, 1),
  ('B', 2.2, 2.2, 2, 2.2, 2),
  ('C', 3.3, 3.3, 3, 3.3, 3);

end transaction;
begin transaction;

INSERT INTO trader (first_name, last_name, dob, country, email)
VALUES
  ('David', 'Miquel', '1994-03-12', 'Canada', 'david.email@email.david'),
  ('Harry', 'Potter', '1995-01-01', 'England', 'harry.email@email.harry');

end transaction;
begin transaction;

INSERT INTO account (trader_id, amount)
VALUES
  (1, 100.0),
  (2, 1000.0);

end transaction;
begin transaction;

INSERT INTO security_order (account_id, status, ticker, "size", price, notes)
VALUES
  (1, 'FILLED', 'C', 1, 1.1, 'this is a note'),
  (2, 'FILLED', 'B', 1, 1.1, 'this is also a note');

end transaction;