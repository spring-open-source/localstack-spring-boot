CREATE TABLE persons
(
    id UUID NOT NULL DEFAULT uuid_generate_v4(),
    email CHARACTER VARYING (255) NOT NULL UNIQUE,
    full_name CHARACTER VARYING (255) NOT NULL,
    age INTEGER NOT NULL,
    image_key CHARACTER VARYING (255) NOT NULL,
    is_spared BOOLEAN DEFAULT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL
);

CREATE trigger persons_creation_timestamp_trigger
   BEFORE INSERT ON persons
   for each row EXECUTE procedure creation_timestamp_handler();
