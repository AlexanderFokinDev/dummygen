## Dummy generator description

This web-server app takes a JSON file as input and returns a file in the chosen format. The input file specifies the columns to be generated and their respective data types, generators and parameters. The generated output can be formatted as CSV, XML, JSON, SQL or Clickhouse SQL.

The input file can be sent from a web form or via the REST API. [Description is here](docs/API.md)

### Input file structure

The input file has the following structure:

```json
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
      "name": "name",
      "dataType": "string",
      "generator": "random",
      "params": {
        "length": 10
      }
    },
    {
      "name": "email",
      "dataType": "string",
      "generator": "email"
    },
    {
      "name": "dob",
      "dataType": "date",
      "generator": "date",
      "params": {
        "format": "YYYY-MM-DD"
      }
    },
    {
      "name": "age",
      "dataType": "int",
      "generator": "range",
      "params": {
        "min": 18,
        "max": 65
      }
    },
    {
      "name": "saller",
      "dataType": "string",
      "generator": "default",
      "params": {
        "value": "MC Company"
      }
    }
  ],
  "rows": 15,
  "format": "json"
}
```

Each object in the columns array specifies a column to be included in the output file. The name attribute specifies the name of the column, dataType specifies the data type of the column, and generator specifies the method to be used to generate data for the column.

The params object is optional and specifies parameters to be used by the generator. The rows attribute specifies the number of rows to generate, and the format attribute specifies the format of the output file.

If the generator is set to default, the params.value attribute will be included in the column for each row.

The app currently supports the following generators:

- increment: generates a sequence of incrementing numbers starting from the specified start value with a step of step
- random: generates a random string of the specified length
- email: generates a random email address
- date: generates a random date in the format specified by params.format
- range: generates a random integer between the min and max values specified in params
- default: takes the same default value for each row.

To use the app, send a POST request to the server with the input file in the request body. The response will be the generated file in the format specified in the input file.

