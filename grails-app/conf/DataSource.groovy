dataSource {
    pooled = true
    driverClassName = "org.h2.Driver"
    username = "sa"
    password = ""
}

hibernate {
    cache.use_second_level_cache = true
    cache.use_query_cache = true
    cache.provider_class = 'net.sf.ehcache.hibernate.EhCacheProvider'
}

// environment specific settings
environments {
    development {
        dataSource {
            url = "jdbc:mysql://localhost/bethany?useUnicode=yes&characterEncoding=UTF-8"
            dialect = org.hibernate.dialect.MySQL5InnoDBDialect
            driverClassName = "com.mysql.jdbc.Driver"
            username = 'root'
            password = ''
            dbCreate = 'update'
        }
    }
    test {
        dataSource {
            url = "jdbc:mysql://localhost/bethany?characterEncoding=UTF-8"
            driverClassName = "com.mysql.jdbc.Driver"
            dbCreate = "update" // one of 'create', 'create-drop','update'
        }
    }
    production {
        dataSource {
            url = "jdbc:mysql://localhost/openmentor?characterEncoding=UTF-8"
			driverClassName = "com.mysql.jdbc.Driver"
            dbCreate = "update"
	        username='root'
   	        password=''
        }
    }
}
