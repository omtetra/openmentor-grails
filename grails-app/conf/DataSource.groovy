dataSource {
    pooled = true
    driverClassName = "org.h2.Driver"
    username = "sa"
    password = ""
}
hibernate {
	jdbc.batch_size = 0
    cache.use_second_level_cache = true
    cache.use_query_cache = true
    cache.provider_class = 'net.sf.ehcache.hibernate.EhCacheProvider'
}

// environment specific settings
environments {
    development {
        dataSource {
            dbCreate = "update" // one of 'create', 'create-drop','update'
            url = "jdbc:mysql://localhost:3306/test_openmentor?characterEncoding=UTF-8"
			username='root'
			password=''
        }
    }
    test {
        dataSource {
            dbCreate = "update" // one of 'create', 'create-drop','update'
            url = "jdbc:mysql://localhost:3306/test_openmentor?characterEncoding=UTF-8"
			username='root'
			password=''
        }
    }
    staging {
        dataSource {
            dbCreate = "update"
            url = "jdbc:mysql://localhost:3306/test_openmentor?characterEncoding=UTF-8"
			username='root'
			password=''
        }
    }
    production {
        dataSource {
            dbCreate = "update"
            url = "jdbc:mysql://localhost:3306/openmentor?characterEncoding=UTF-8"
			username='root'
			password=''
        }
    }
}
