<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
 <head>
   <meta charset="UTF-8">
   <title>Вход</title>
 </head>
 <body>

   <h4 style="color:green">${logout}</h4>
   <h4 style="color:yellow">${emptyLogin}</h4>
   <h4 style="color:red">${error}</h4>

   <form action="sign-in" method="post">
     <table>
       <tr>
         <td>Логин:</td>
         <td><input type="text" name="username" value='<%=request.getAttribute("login")%>' /></td>
         <td><a href="${pageContext.request.contextPath}/sign-up">Регистрация</a></td>
       <tr>
         <td>Пароль:</td>
         <td><input type="password" name="password" /></td>
         <td></td>
       <tr>
         <td></td>
         <td><input type="submit" value="Войти" /></td>
         <td></td>
       </tr>
     </table>
   </form>
  </body>
</html>