<html>
  <head>
    <title>Movie Theatre Application</title>
  </head>
  <center>
   <p3>Uploading events into the system (~\MovieTheater\src\main\webapp\WEB-INF\jsons\events)<p3>
   <br>
    <#if error??>
        <p3><b><font color="red">Error: ${error}</font<b><p3>
    </#if>
          <form method="post" action="/upload/events" enctype="multipart/form-data">
                      <table border="0">
                          <tr>
                              <td>Pick file #1:</td>
                              <td><input type="file" name="fileUpload" size="50" /></td>
                          </tr>
                          <tr>
                              <td>Pick file #2:</td>
                              <td><input type="file" name="fileUpload" size="50" /></td>
                          </tr>
                          <tr>
                              <td>Pick file #3:</td>
                              <td><input type="file" name="fileUpload" size="50" /></td>
                          </tr>
                          <tr>
                              <td colspan="2" align="center"><input type="submit" value="Upload" /></td>
                          </tr>
                      </table>
                  </form>
      </center>
  </body>
</html>