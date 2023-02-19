<!DOCTYPE html>
<html>
<head>
    <title>Data generator</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z" crossorigin="anonymous">
    <link rel="stylesheet" type="text/css" href="/resources/static/style.css">
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">

    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script>
    $(document).ready(function() {
        $.getJSON('/resources/static/default_settings.json', function(data) {
            var placeholderText = JSON.stringify(data, null, 4);
            $('#settingsJson').val(placeholderText);
        });
    });
    </script>

</head>
<body>

<div class="home-button">
<a href="/">
    <img src="/resources/static/images/home_white_48dp.png" alt="Home" width="50" height="50">
</a>
</div>

<div class="container my-5">
    <h2 class="text-center white-bold-text">Dummy Table Generator</h2>


    <form action="/submit-dummy-table" method="post" class="form-horizontal">

        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <a href="https://github.com/AlexanderFokinDev/dummygen#readme" class="h3">Documentation</a>
            </div>
        </div>

        <div class="form-group">
        <label for="settingsJson" class="col-sm-10 control-label white-bold-text">Settings (json format):</label>
            <div class="col-sm-10">
                <textarea class="col-sm-10 form-control" id="settingsJson" name="settingsJson" rows="15"></textarea>
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