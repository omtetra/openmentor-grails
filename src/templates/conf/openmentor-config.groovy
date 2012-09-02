// This is the default configuration file, read by the OpenMentor application
// and used to configure the web application. Settings here override the 
// default application settings.
//
// Note that you will need to create a MySQL database for this, and allow
// access with an appropriate user name and password. This configuration file
// will also support a 

dataSource {
    pooled = true
    driverClassName = 'com.mysql.jdbc.Driver'
    dbCreate = "update"
    url = "jdbc:mysql://localhost:3306/openmentor?characterEncoding=UTF-8"
    username='xxx'
    password='xxx'
}
