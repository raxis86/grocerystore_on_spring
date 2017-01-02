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
    <title>Список заказов</title>
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
              <li><a href="/Index">Главная</a></li>
              <c:if test="${empty sessionScope.user}" >
                  <li><a href="/GroceryList">Каталог товаров</a></li>
              </c:if>
              <c:if test="${!empty sessionScope.user}" >
                  <c:if test="${!sessionScope.role.getName().equals('admin')}" >
                      <li><a href="/GroceryList">Каталог товаров</a></li>
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

    <div>Редактируемый заказ:</div>
    <form action="/OrderEdit" method="post">
        <input type="hidden" name="orderid" value="${order.getId()}">
        <Table>
            <th>Статус</th>
            <th>Дата</th>
            <th>Сумма</th>
            <th>Адрес доставки</th>
            <th></th>
            <tr>
                <td>${order.getStatus()}</td>
                <td>${order.getDate()}</td>
                <td>${order.getPrice()}</td>
                <td>${order.getAddress()}</td>
                <td>
                    <p><select size="order.getStatuses().size()" name="statusid">
                        <c:forEach items="${order.getStatuses()}" var="status">
                            <option name="statusid" value="${status.key}">${status.value}</option>
                        </c:forEach>
                    </select></p>
                </td>
            </tr>
            <tr>
                <table>
                    <th>Наименование</th>
                    <th>Количество</th>
                    <c:forEach items="${order.getGroceries()}" var="map">
                        <tr>
                            <td>${map.key}</td>
                            <td>${map.value}</td>
                        </tr>
                        </br>
                    </c:forEach>
                </table>
            </tr>
        </Table>
        <br>
        <input type="submit" value="Сохранить">
    </form >

  </body>
</html>
