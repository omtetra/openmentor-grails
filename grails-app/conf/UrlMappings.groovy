class UrlMappings {

	static mappings = {
		"/$controller/$action?/$id?"{
			constraints {
				// apply constraints here
			}
		}

		"/"(controller:'home', action:"/index")
		"/debug"(view:"/debug")
		"500"(view:'/error')
	}
}
