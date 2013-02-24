<head>
  <meta name='layout' content='main' />
  <title>Login</title>
</head>

<body>
	<div id='login'>
		<div class='inner'>
			<g:if test='${flash.message}'>
			  <div class="alert alert-info">
                <button type="button" class="close" data-dismiss="alert">&times;</button>
                <strong>Note:</strong> ${flash.message}
              </div>
			</g:if>
			<h3>Please Login...</h3>
			<form action='${postUrl}' method='POST' id='loginForm' class='cssform form-horizontal' autocomplete='off'>
			  <div class="control-group">
                <label class="control-label" for="username">Username</label>
                <div class="controls">
                  <input type='text' class='text_' name='j_username' id='username' />
                </div>
              </div>
              <div class="control-group">
                <label class="control-label" for="password">Password</label>
                <div class="controls">
                  <input type='password' class='text_' name='j_password' id='password' />
                </div>
              </div>
              <div class="control-group">
                <div class="controls">
                  <button type="submit" class="btn btn-primary">Login</button>
                </div>
              </div>
			</form>
		</div>
	</div>
<script type='text/javascript'>
<!--
(function(){
	document.forms['loginForm'].elements['j_username'].focus();
})();
// -->
</script>
</body>
