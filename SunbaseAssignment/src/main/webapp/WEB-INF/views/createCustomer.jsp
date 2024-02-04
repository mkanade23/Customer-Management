<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<html>
<head>
    <title>Create Customer</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">

</head>
<body class="container mt-4">

<h2>
    Create Customer
</h2>
<form action="addCustomer" method="get">
    <div class="form-group">
    <label for="firstName"> First  Name: </label>
    <input type="text" id="firstName" name="firstName" class="form-control" required>
    </div>


    <div class="form-group">
        <label for="lastName"> Last  Name: </label>
    <input type="text" id="lastName" name="lastName" class="form-control" required>
    </div>


    <div class="form-group">
     <label for="street"> Street: </label>
    <input type="text" id="street" name="street" class="form-control" required>
    </div>


    <div class="form-group">
     <label for="address"> Address: </label>
    <input type="text" id="address" name="address" class="form-control" required>
    </div>


    <div class="form-group">
    <label for="city"> City: </label>
    <input type="text" id="city" name="city" class="form-control" required>
    </div>


    <div class="form-group">
        <label for="state">State: </label>
    <input type="text" id="state" name="state" class="form-control" required>
    </div>


    <div class="form-group">
    <label for="email"> Email: </label>
    <input type="text" id="email" name="email" class="form-control" required>
    </div>


    <div class="form-group">
     <label for="phone"> Phone: </label>
    <input type="text" id="phone" name="phone" class="form-control" required>
    </div>

    <input type ="submit" value="Add Customer" class="btn btn-primary"/>
</form>
<form action="/home" method="post">
    <input type ="submit" value="Home" class="btn btn-secondary"/>
</form>
</body>
</html>