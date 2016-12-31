<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: raxis
  Date: 20.12.2016
  Time: 16:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:set var="req" value="${pageContext.request}" />
<html>
  <head>
    <title>Оформление заказа</title>
  </head>
  <body>
  <div class="menu">
      <c:if test="${empty sessionScope.user}" >
          <div> <a href="/Login">Вход</a> </div>
          <div> <a href="/Signin">Регистрация</a> </div>
      </c:if>
      <c:if test="${!empty sessionScope.user}" >
          <div> ${sessionScope.user.getName()}!</div>
          <div> <a href="/Logout">Выход</a> </div>
      </c:if>
  </div>

  <div>
      <nav>
          <ul id="menu">
              <li><a href="index.jsp">Главная</a></li>
              <c:if test="${empty sessionScope.user}" >
                  <li><a href="/GroceryListController">Каталог товаров</a></li>
              </c:if>
              <c:if test="${!empty sessionScope.user}" >
                  <c:if test="${!sessionScope.role.getName().equals('admin')}" >
                      <li><a href="/GroceryListController">Каталог товаров</a></li>
                      <li><a href="/CartList">Корзина покупок</a></li>
                      <li><a href="/OrderList">Список заказов</a></li>
                  </c:if>
                  <c:if test="${sessionScope.role.getName().equals('admin')}" >
                      <li><a href="/OrderListAdmin">Список заказов (админ-режим)</a></li>
                      <li><a href="/GroceryListAdmin">Каталог товаров (админ-режим)</a></li>
                  </c:if>
              </c:if>
          </ul>
      </nav>
  </div>


    <div>Вы заказываете следующие продукты:</div>
    <Table>
      <tr>
        <th>Наименование</th>
        <th>Цена</th>
        <th>Количество</th>
        <th></th>
      </tr>
      <c:forEach items="${cart.getMap()}" var="item">
        <tr>
          <td>${item.key.getName()}</td>
          <td>${item.key.getPrice()}</td>
          <td>${item.value}</td>
        </tr>
        </br>
      </c:forEach>
    </Table>
    <br>
    <div>Итого:${totalprice}</div>
    <br>
    <div>Ваши данные:</div>
    <form action="/OrderAdd" method="post">
      <input type="hidden" name="userid" value="${user.getId()}">
      Имя: <input type="text" name="name" value="${user.getName()}"> <br>
      Фамилия: <input type="text" name="lastname" value="${user.getLastName()}"> <br>
      Отчество: <input type="text" name="surname" value="${user.getSurName()}"> <br>
      Телефон: <input type="text" name="phone" value="${user.getPhone()}"> <br>
      Адрес доставки: <input type="textarea" name="address" value="${user.getAddress()}"> <br>
      <%--<input type="hidden" name="returnurl" value="${req.requestURI}">--%>
      <input type="submit" value="Оформить">
    </form>

  </body>
</html>
