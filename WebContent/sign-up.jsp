<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
 <head>
   <meta charset="UTF-8">
   <title>Simple Web Application</title>
 </head>
 <body>

   <h4 style="color:red">${error}</h4>

   <form action="sign-up" method="post">
     <table>
       <tr>
         <td>Имя пользователя</td>
         <td><input type="text" name="username" /></td>
       <tr>
         <td>Пароль</td>
         <td><input type="password" name="password" /></td>
       <tr>
         <td>Повтор пароля</td>
         <td><input type="password" name="passwordRepeat" /></td>
       <tr>
         <td></td>
         <td><input type="submit" value="Зарегистрироваться" /></td>
         <td></td>
       </tr>
     </table>
   </form>
  </body>
</html>