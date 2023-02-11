<!DOCTYPE html>
<html>
<head>
    <title>Data generator</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z" crossorigin="anonymous">
    <link rel="stylesheet" type="text/css" href="/resources/static/style.css">
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">

    <script>
        function onFormatSelectChange() {
        	const formatSelect = document.getElementById("formatSelect");
        	const selectedFormat = formatSelect.options[formatSelect.selectedIndex].value;
        	const rowsInput = document.getElementById("rows");
        	const columnsInput = document.getElementById("columns");
        	const settingsLabel = document.querySelector("label[for='settings_json']");
        	const settingsInput = document.getElementById("settings_json");
        	const submitButton = document.querySelector("button[type='submit']");

        	if (selectedFormat === "settings_from_file") {
        		rowsInput.disabled = true;
        		columnsInput.disabled = true;
        		settingsInput.disabled = false;
        	} else {
        		rowsInput.disabled = false;
        		columnsInput.disabled = false;
        		settingsInput.disabled = true;
        	}
        }
    </script>

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
            <label for="formatSelect" class="col-sm-10 control-label white-bold-text">Select a response format:</label>
            <div class="col-sm-10">
                <select class="form-control col-sm-6" id="formatSelect" name="formatSelect" onchange="onFormatSelectChange()">
                    <option value="json">JSON</option>
                    <option value="xml">XML</option>
                    <option value="csv">CSV</option>
                    <option value="query_clickhouse">Clickhouse query</option>
                    <option value="query_sql">SQL query</option>
                    <option value="settings_from_file">Settings from file</option>
                </select>
            </div>
        </div>

        <div class="form-group">
            <label for="rows" class="col-sm-10 control-label white-bold-text">Rows:</label>
            <div class="col-sm-10">
                <input type="text" class="col-sm-6 form-control" id="rows" name="rows" value="10">
            </div>
        </div>

        <div class="form-group">
            <label for="columns" class="col-sm-10 control-label white-bold-text">Columns:</label>
            <div class="col-sm-10">
                <input type="text" class="col-sm-6 form-control" id="columns" name="columns" value="5">
            </div>
        </div>

        <div class="form-group">
        <label for="settings_json" class="col-sm-10 control-label white-bold-text">Settings (json format):</label>
            <div class="col-sm-10">
                <textarea class="col-sm-10 form-control" id="settings_json" name="settings_json" rows="5" disabled></textarea>
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