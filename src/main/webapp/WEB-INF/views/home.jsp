<%@ page import="com.sunbaseassignment.model.Customer" %>
<%@ page import="java.util.List" %>
<%@ page import="java.lang.String" %>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<html>
<head>
    <title>CRUD Operation</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" >

</head>
<body class="container mt-4">
<h2>CRUD Operation </h2>
    <form action="/createCustomer" method="post">
        <button type="submit" class="btn btn-primary">Add Customer</button>
    </form>

    <form action="/searchCustomers" method="get" class="mt-3">
        <div class="form-group row">

                <label for="searchField" class="col-sm-2 col-form-label">Select Field:</label>
                <div class="col-sm-4">
                  <select name="searchField" id="searchField" class="form-control">
                      <option value="all">All</option>
                      <option value="firstName">First Name</option>
                      <option value="lastName">Last Name</option>
                      <option value="street">Street</option>
                      <option value="address">Address</option>
                      <option value="city">City</option>
                      <option value="state">State</option>
                      <option value="email">Email</option>
                      <option value="phone">Phone</option>
                  </select>
                </div>


                <label for="searchValue" class="col-sm-2 col-form-label">Search Value:</label>
                <div class="col-sm-3">
                    <input type="text" name="searchValue" id="searchValue" class="form-control" placeholder="Showing All Customers">
                </div>

            <div class="col-sm-1">
                <button type="submit" class="btn btn-success">Search</button>
            </div>
        </div>
    </form>
<div class="form-inline">
        <form action="/home" method="post" class="mr-2">
            <input type ="submit" value="Home" class="btn btn-secondary"/>
        </form>
        <form action="/sync" method="get" class="d-inline">
            <button type="submit" class="btn btn-info">Sync Customers</button>
        </form>
</div>

<%
    List<Customer> customerList =(List<Customer>) request.getAttribute("customerList");
    int totalPages = request.getAttribute("totalPages") != null ? (int) request.getAttribute("totalPages") : 0;
    int currentPage = request.getAttribute("currentPage") != null ? (int) request.getAttribute("currentPage") : 0;
    if(customerList != null && !customerList.isEmpty())
    {
%>
<table class="table mt-3">
    <thead>
    <tr>
        <th><a href="customerList?sortField=firstName&sortDir=${sortDir == 'asc' ? 'desc' : 'asc'}">First Name</th>
        <th><a href="customerList?sortField=lastName&sortDir=${sortDir == 'asc' ? 'desc' : 'asc'}">Last Name</th>
        <th><a href="customerList?sortField=address&sortDir=${sortDir == 'asc' ? 'desc' : 'asc'}">Address</th>
        <th><a href="customerList?sortField=address&sortDir=${sortDir == 'asc' ? 'desc' : 'asc'}">Street</th>
        <th><a href="customerList?sortField=city&sortDir=${sortDir == 'asc' ? 'desc' : 'asc'}">City</th>
        <th><a href="customerList?sortField=state&sortDir=${sortDir == 'asc' ? 'desc' : 'asc'}">State</th>
        <th><a href="customerList?sortField=email&sortDir=${sortDir == 'asc' ? 'desc' : 'asc'}">Email</th>
        <th><a href="customerList?sortField=phone&sortDir=${sortDir == 'asc' ? 'desc' : 'asc'}">Phone</th>
        <th>Action</th>
        <th></th>
    </tr>
    </thead>
    <tbody>
    <%
        String customerBeingEdited = (String) request.getAttribute("customerBeingEdited");
        for(Customer c : customerList) {
    %>

        <tr>
        <%
            if(c.getCustomerId().equals(customerBeingEdited)) {
        %>

            <form action="/updateCustomer" method="post">
            <td><input type="text" name="firstName" class="form-control" value="<%= c.getFirstName() %>"></td>
            <td><input type="text" name="lastName" class="form-control" value="<%= c.getLastName() %>"></td>
            <td><input type="text" name="address" class="form-control" value="<%= c.getAddress() %>"></td>
                <td><input type="text" name="street" class="form-control" value="<%= c.getStreet() %>"></td>
            <td><input type="text" name="city" class="form-control" value="<%= c.getCity() %>"></td>
            <td><input type="text" name="state" class="form-control" value="<%= c.getState() %>"></td>
            <td><input type="text" name="email" class="form-control" value="<%= c.getEmail() %>"></td>
            <td><input type="text" name="phone" class="form-control" value="<%= c.getPhone() %>"></td>
            <input type="hidden" name="customerId" value="<%= c.getCustomerId() %>">
            <td><div class="btn-group"><button type="submit" class="btn btn-success">Save</button>
                <a href="/home" class="btn btn-secondary cancel-button">Cancel</a></div></td>
                                </form>

            <% } else { %>

            <td><%= c.getFirstName() %></td>
            <td><%= c.getLastName() %></td>
            <td><%= c.getAddress() %></td>
            <td><%= c.getStreet() %></td>
            <td><%= c.getCity() %></td>
            <td><%= c.getState() %></td>
            <td><%= c.getEmail() %></td>
            <td><%= c.getPhone() %></td>
            <td><form action="/editCustomer" method="post">
                <input type="hidden" name="customerId" value="<%= c.getCustomerId() %>"/>
                                    <input type="submit" class="btn btn-primary" value="Edit"/>
            </form>

            <% } %>
            <td><form action="/removeCustomer" method="post">
                <input type="hidden" name="customerId" value="<%= c.getCustomerId() %>">
                <button type="submit" class="btn btn-danger">Remove Customer</button>
            </form>
            </td>

        </tr>
     <% } %>
    </tbody>
</table>


<div class="mt-3">
    <!-- Pagination links -->
    <% if(totalPages > 1) { %>
        <nav aria-label="Page navigation">
                        <ul class="pagination">
            <% for(int i=0;i<totalPages;i++) { %>

                <li class="page-item">
                    <% if(i== currentPage) {%>
                            <span class="page-link"><%= (i+1) %></span>
                    <% } else { %>

                    <form action="/home" method="get" id="homeForm">
                        <input type="hidden" name="page" value="<%= i%>" >
                        <input type="hidden" name="size" value="5">
                        <button type="submit" class="page-link"><%= (i+1) %></button>
                    </form>

                        <% } %>
                </li>
            <% } %>
        </ul>
        </nav>
    <% } %>
</div>

<% }else{%>
<h2>
    No Customer to display</h2>
<% }%>

</body>
</html>