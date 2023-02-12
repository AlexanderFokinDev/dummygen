# API v1 

## Method: getDummyTable

**Description:**

This API method is used to generate a file in CSV, TXT, JSON, or XML format based on the provided settings. The method accepts a JSON payload containing the settings for generating the dummy table.

>URL: POST /getDummyTable

**Request Parameters:**

>**settingsJson** (required): A JSON payload containing the settings for generating the dummy table.
Response:

- If the request is successful, a file in CSV, TXT, JSON, or XML format will be returned as a binary stream in the response body. The content type of the response will be set according to the file format generated.

- If the request fails, an error response will be returned with an appropriate HTTP status code.
Example Request:

http://localhost:8080/api/v1/getDummyTable

```bash
POST /getDummyTable HTTP/1.1
Host: example.com
Content-Type: application/json

{
  "columns": [
    {
      "name": "id",
      "dataType": "int",
      "generator": "increment",
      "params": {
        "start": 1,
        "step": 1
      }
    },
    {
      "name": "column1",
      "dataType": "string",
      "generator": "random",
      "params": {
        "length": 10
      }
    },
	{
      "name": "column2",
      "dataType": "string",
      "generator": "random",
      "params": {
        "length": 10
      }
    },
	{
      "name": "column3",
      "dataType": "string",
      "generator": "random",
      "params": {
        "length": 10
      }
    }
  ],
  "rows": 10,
  "format": "query_sql"
}
```

Example Response:
```java
HTTP/1.1 200 OK
Content-Type: application/octet-stream
Content-Disposition: attachment; filename="dummy_4b07b47159eb492aab25de1290ea537e.txt"

        CREATE TABLE dummy_table (
        id INT NOT NULL,
        column1 VARCHAR(255) NOT NULL,
        column2 VARCHAR(255) NOT NULL,
        column3 VARCHAR(255) NOT NULL,
        PRIMARY KEY (id)
        );

        INSERT INTO dummy_table (id, column1, column2, column3) VALUES (1, 'w2DX1sAkFR', 'TynCvAO42Y', 'NEG08ooBBG');
        INSERT INTO dummy_table (id, column1, column2, column3) VALUES (2, 'GaTTOsR7Cj', 'oFg1t1n7Wh', 'w8WW9sGZeg');
        INSERT INTO dummy_table (id, column1, column2, column3) VALUES (3, 'nKOfX9VBkh', 'IxaMwCA1Bn', 'uibyLAD0uf');
        INSERT INTO dummy_table (id, column1, column2, column3) VALUES (4, 'tuArpX0GB8', 'VHRHxHaocx', '0799ZE1KCX');
        INSERT INTO dummy_table (id, column1, column2, column3) VALUES (5, 'xeQhbLazqn', 'mdqYdeyaD0', 'f9PC5HQAaW');
        INSERT INTO dummy_table (id, column1, column2, column3) VALUES (6, 'hM3TKLpoqx', 'gDTe34mKaV', 'lVFmR9HXhL');
        INSERT INTO dummy_table (id, column1, column2, column3) VALUES (7, 'kfmpH6fpSn', 'aGJZGvGTU3', 'hNmH3TlBoL');
        INSERT INTO dummy_table (id, column1, column2, column3) VALUES (8, 'RWomg2pZ0v', 'uCGDYopiVG', 'K96pnn8mAw');
        INSERT INTO dummy_table (id, column1, column2, column3) VALUES (9, 'ofbROY4B0q', 'Mcai5ryz2q', 'RRM3wijmHg');
        INSERT INTO dummy_table (id, column1, column2, column3) VALUES (10, 'hag06gMTwY', 'xxvWdwQeLY', '3H1YzXS2FC');
```