<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <title><g:layoutTitle default="OpenMentor" /></title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">

    <!-- Le styles -->
    <link rel="stylesheet" href="${resource(dir:'css/jqueryui',file:'jquery-ui-1.9.2.custom.css')}" />
    <link rel="stylesheet" href="${resource(dir:'css',file:'bootstrap.css')}">
    <style type="text/css">
      body {
        padding-top: 60px;
        padding-bottom: 40px;
      }
      .sidebar-nav {
        padding: 9px 0;
      }
    </style>
    <link rel="stylesheet" href="${resource(dir:'css',file:'bootstrap-responsive.css')}">
    <link rel="stylesheet" href="${resource(dir:'css',file:'chosen.css')}">
    <link rel="stylesheet" href="${resource(dir:'css',file:'main.css')}">
    <g:javascript src='libs/jquery-1.8.3.min.js'/>
    <g:javascript src='libs/bootstrap.min.js'/>
    <g:javascript src='libs/jquery-ui-1.9.2.custom.min.js'/>
    <g:javascript src='libs/bootstrap.file-input.js'/>
    <g:javascript src='libs/excanvas.min.js'/>
    <g:javascript src='libs/jquery.flot.js'/>
    <g:javascript src='libs/chosen.jquery.min.js'/>
    <g:layoutHead />

    <!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
    <!--[if lt IE 9]>
      <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->
  </head>

  <body>

    <div class="navbar navbar-inverse navbar-fixed-top">
      <div class="navbar-inner">
        <div class="container-fluid">
          <a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </a>
          <a class="brand" href="${resource(dir:'/',file:'')}">OpenMentor</a>
          <div class="nav-collapse collapse">
            <p class="navbar-text pull-right">
              <sec:ifLoggedIn>
              Logged in as <sec:username/> (<g:link controller='logout' class='navbar-link'>Logout</g:link>)
              </sec:ifLoggedIn>
              <sec:ifNotLoggedIn>
              <g:link controller='login' class='navbar-link'>Log in</g:link>
              </sec:ifNotLoggedIn>
            </p>
            <ul class="nav">
              <li class="active"><a href="${resource(dir:'/',file:'')}">Home</a></li>
              <li><g:link action="index" controller="help">About</g:link></li>
              <li><g:link action="contact" controller="help">Contact</g:link></li>
            </ul>
          </div><!--/.nav-collapse -->
        </div>
      </div>
    </div>

    <div class="container-fluid">
      <div class="row-fluid">
        <div class="span3">
          <div class="well sidebar-nav">
            <ul class="nav nav-list">
              <g:if test="${! session.current_course}">
                <li>
                  <g:link class="active" action="select" controller="course">Choose course</g:link>
                </li>
                </g:if>
                <g:else>
                <li>
                  <g:link action="select" controller="course">Using course: ${session.current_course} (Change)</g:link>
                </li>
                <li>
                  <g:link action="index" controller="assignment">Assignments</g:link>
                </li>
                <li>
                  <g:link action="upload" controller="submission">Upload submissions</g:link>
                </li>
                <li>
                  <g:link action="index" controller="report">View reports</g:link>
                </li>
              </g:else>
              <sec:ifAnyGranted roles="ROLE_OPENMENTOR-ADMIN">
                <li class="divider"></li>
                <li>
                  <g:link action="index" controller="course">Courses</g:link>
                </li>
                <li>
                  <g:link action="index" controller="student">Students</g:link>
                </li>
                <li>
                  <g:link action="index" controller="tutor">Tutors</g:link>
                </li>
              </sec:ifAnyGranted>
              <sec:ifAnyGranted roles="ROLE_OPENMENTOR-ADMIN">
                <li class="divider"></li>
                <li>
                  <g:link action="index" controller="user">Users</g:link>
                </li>
                <li>
                  <g:link action="index" controller="history">History</g:link>
                </li>
              </sec:ifAnyGranted>
              </ul>
          </div><!--/.well -->
        </div><!--/span-->
        <div class="span9">
          <g:layoutBody />
        </div><!--/span-->
      </div><!--/row-->

      <hr>

      <footer>
        <p>
        <small>OpenMentor version <g:meta name="app.version"/> (available from <a href="https://github.com/omtetra/openmentor-grails">Github</a>)
        </small></p>
      </footer>

    </div><!--/.fluid-container-->

    <div id="jqdialog"></div>
    <div id="postJQuery">
      <g:pageProperty name="page.postJQuery" />
    </div>
  </body>
</html>
