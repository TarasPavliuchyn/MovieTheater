<html>
  <head>
    <title>Movie Theatre Application</title>
  </head>
  <center>
  <a href="/">Home page</a><br>
   <p3>Uploading ${action} into the system (MovieTheater\src\main\webapp\WEB-INF\jsons\${action})<p3>
   <#if error??>
   <br>
        <p3><font color="red">Error with message '${error}'.</font><p3>
    </#if>
   <br>
          <form method="post" action="/upload/${action}" enctype="multipart/form-data">
                      <table border="0">
                          <tr>
                              <td>Pick file #1:</td>
                              <td><input type="file" name="fileUpload" size="50" /></td>
                          </tr>
                          <tr>
                              <td>Pick file #2:</td>
                              <td><input type="file" name="fileUpload" size="50" /></td>
                          </tr>
                          <#if action == 'events'>
                               <tr>
                                   <td>Pick file #3:</td>
                                   <td><input type="file" name="fileUpload" size="50" /></td>
                               </tr>
                          </#if>
                          <tr>
                              <td colspan="2" align="center"><input type="submit" value="Upload ${action}" /></td>
                          </tr>
                      </table>
                  </form>
      </center>
  </body>
</html>