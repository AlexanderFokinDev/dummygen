<!DOCTYPE html>
<html>
<head>
    <title>Data generator</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z" crossorigin="anonymous">
    <link rel="stylesheet" type="text/css" href="/resources/static/style.css">
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
</head>
<body>

<div class="home-button">
<a href="/">
    <img src="/resources/static/images/home_white_48dp.png" alt="Home" width="50" height="50">
</a>
</div>

<div class="container my-5">
    <h2 class="text-center white-bold-text">Dummy Table Form</h2>
    <form action="/submit-dummy-table" method="post" class="form-horizontal">
        <div class="form-group">
            <label for="rows" class="col-sm-2 control-label white-bold-text">Rows:</label>
            <div class="col-sm-10">
                <input type="text" class="form-control" id="rows" name="rows">
            </div>
        </div>
        <div class="form-group">
            <label for="columns" class="col-sm-2 control-label white-bold-text">Columns:</label>
            <div class="col-sm-10">
                <input type="text" class="form-control" id="columns" name="columns">
            </div>
        </div>
        <div class="form-group">
            <label for="formatSelect" class="col-sm-10 control-label white-bold-text">Select a response format:</label>
            <div class="col-sm-10">
                <select class="col-sm-6 form-control" id="formatSelect" name="formatSelect">
                    <option value="json">JSON</option>
                    <option value="xml">XML</option>
                    <option value="csv">CSV</option>
                    <option value="query_clickhouse">Clickhouse query</option>
                    <option value="query_sql">SQL query</option>
                </select>
            </div>
        </div>
        <div class="form-group">
            <label for="email" class="col-sm-2 control-label white-bold-text">Email:</label>
            <div class="col-sm-10">
                <input type="text" class="form-control" id="email" name="email">
            </div>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <button type="submit" class="btn btn-primary btn-lg">Submit</button>
            </div>
        </div>
    </form>
</div>
</body>
</html>