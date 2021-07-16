<script>
    const user = {
        username: "",
        password: ""
    }

    let token = null;

    const login = () => {
        fetch("http://localhost:8080/login", {
            method: "POST",
            headers: {
                "Content-type": "application/json",
                "Accept": "application/json"
            },
            credentials: 'include',
            body: JSON.stringify(user)
        })
        .then(response => {
            console.log(response.headers)
            response.json()
            .then(data => {
                console.log(data.Auth)
                token = data.Auth
            })
        })
    }

    const test = () => {
		fetch("http://localhost:8080/api/test", {
			method: "POST",
			headers: {
                "Content-type": "application/json",
                "Auth": token
            },
            credentials: 'include',
			body: "errore"
		})
		.then(response => {
			console.log(response)
			response.json().then(data => {
				console.log(data)
			})
		})
	}


    /*
  @Bean
  fun corsConfigurer(): WebMvcConfigurer? {
      return object : WebMvcConfigurer {
          override fun addCorsMappings(registry: CorsRegistry) {
              registry.addMapping("/**")
                  .allowedOriginPatterns("*")
                  .allowedMethods("GET", "POST", "PUT", "DELETE", "HEAD", "PATCH")
                  .allowCredentials(true)
          }
      }
  }


  @Bean
  fun cookieProcessorCustomizer(): WebServerFactoryCustomizer<TomcatServletWebServerFactory>? {
      return WebServerFactoryCustomizer { serverFactory: TomcatServletWebServerFactory ->
          serverFactory.addContextCustomizers(
              TomcatContextCustomizer { context: Context ->
                  context.cookieProcessor = LegacyCookieProcessor()
              })
      }
  }
 */


</script>

<div class="container">
    <input type="text" id="username" placeholder="Username" bind:value={user.username} />
    <input type="password" id="password" placeholder="Password" bind:value={user.password} />
    <button type="button" on:click={login}>Login</button>


    <button on:click={test}>Test!</button>
</div>

<style>

</style>