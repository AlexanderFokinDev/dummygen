<!DOCTYPE html>
<html>
<head>
    <title>Data generator</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z" crossorigin="anonymous">
</head>
<link rel="stylesheet" href="style.css">
<body>

<div class="home-button">
   <a href="/">
      <img src="/resources/static/images/home_black_48dp.png" alt="Home" width="50" height="50">
   </a>
</div>

<div class="container my-5">
    <h2 class="text-center">Dummy Table Form</h2>
    <form action="/submit-dummy-table" method="post" class="form-horizontal">
        <div class="form-group">
            <label for="rows" class="col-sm-2 control-label">Rows:</label>
            <div class="col-sm-10">
                <input type="text" class="form-control" id="rows" name="rows">
            </div>
        </div>
        <div class="form-group">
            <label for="columns" class="col-sm-2 control-label">Columns:</label>
            <div class="col-sm-10">
                <input type="text" class="form-control" id="columns" name="columns">
            </div>
        </div>
        <div class="form-group">
            <label for="email" class="col-sm-2 control-label">Email:</label>
            <div class="col-sm-10">
                <input type="text" class="form-control" id="email" name="email">
            </div>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <button type="submit" class="btn btn-primary">Submit</button>
            </div>
        </div>
    </form>
</div>
</body>
</html>