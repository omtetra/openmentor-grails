
<%@ page import="uk.org.openmentor.domain.Summary" %>
<%@ page import="uk.org.openmentor.util.MultiMap" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <title>What is Open Mentor?</title>
    </head>
    <body>
        <div id="pageBody">
            <h2>What is Open Mentor?</h2>
            
    <p>
      The teaching model underlying the system recognises that
      students need an explicit level of socio-emotive support as well
      as direct instruction.  It recommends tutors praise the student
      for their achievement.  Point out mistakes in a constructive
      manner and offer explicit direction for student improvement in a
      specific piece of work and for future assignments.  It is
      especially important that the feedback students receive from
      teaching staff provides both the subject specific guidance they
      require and the support they need for further
      development. Initial research showed (see Whitelock et al 2004)
      that there was convincing evidence of systematic connections
      between different types of tutor comments and level of
      attainment in assessment and this was the platform for the
      development of OpenMentor.
    </p>
    
	<p>
    <a href="http://dx.doi.org/10.1080/0968776030110304">
      Whitelock, D., Watt, S., Raw, Y. and Moreale, E. (2004)
      Analysing tutor feedback to students: first steps towards
      constructing an electronic monitoring system.  Association for
      Learning Technology Journal (ALT-J), Volume 11, No. 3.pp.31-42
      ISSN 0968-7769.
    </a>
    </p>
    
    <a name="workinpractice"></a>
    <h3>How does it work in practice?</h3>
    <p>
      OpenMentor analyses the tutors comments and sifts them to see if
      the underlying teaching model applies.  In fact there are four
      major categories that capture the activities of teaching using
      this modus operandi; which are positive reactions, negative
      reactions, questions posed to the student and solutions to
      specific difficulties presented in the assignments.  These
      interactional categories illustrate the balance of
      socio-emotional comments that support the student. Best practice
      has revealed that this balance of comments maintains student
      motivation.  The tutor also can use a range of questions in a
      number of different ways, some to stimulate further reflection,
      but others to point out, in a nice way, that there are problems
      with part of an assignment.
    </p>
    <p>
      Open Mentor works by
      stripping out the tutor comments on an electronic assignment,
      applying a set of rules to these comments in order to put them
      into four major categories of Positive reactions, Teaching
      points, Questions and Negative reactions. It then provides
      feedback to the tutor about how their comments provide support
      to the tutor.
    </p>
        
    <!--Neat table background -->
    <table class="table table-striped">
      <caption><strong>Table 1: Bales' Interaction
      Process used for the analysis in OpenMentor</strong></caption>
            <tr>
              <td colspan="2">
                <strong>Categories</strong>
              </td>
              <td>
                <strong>Specific Examples</strong>
              </td>
            </tr>
            <tr>
              <td colspan="3">
                <strong>Positive Reactions</strong>
              </td>
            </tr>
            <tr>
              <td>A1</td>
              <td>
                1. Shows solidarity 
              </td>
              <td>
                Jokes, gives help, rewards others
              </td>
            </tr>
            <tr>
              <td>A2</td>
              <td>
                2. Shows tension release
              </td>
              <td>
                Laughs, shows satisfaction
              </td>
            </tr>
            <tr>
              <td>A3</td>
              <td>3. Shows agreement</td>
              <td>
                Understands, concurs, complies, passively accepts
              </td>
            </tr>
            <tr>
              <td colspan="3">
                <strong>Teaching Points</strong>
              </td>
            </tr>
            <tr>
              <td>B1</td>
              <td>4. Gives suggestion</td>
              <td>Directs, proposes, controls</td>
            </tr>
            <tr>
              <td>B2</td>
              <td>5. Gives opinion</td>
              <td>
                Evaluates, analyses, expresses feelings or wishes
              </td>
            </tr>
            <tr>
              <td>B3</td>
              <td>6. Gives information</td>
              <td>
                Orients, repeats, clarifies, confirms
              </td>
            </tr>
            <tr>
              <td colspan="3">
                <strong>Questions</strong>
              </td>
            </tr>
            <tr>
              <td>C1</td>
              <td>7. Asks for information</td>
              <td>
                Requests orientation, repetition, confirmation,
                clarification
              </td>
            </tr>
            <tr>
              <td>C2</td>
              <td>8. Asks for opinion</td>
              <td>
                Requests evaluation, analysis, expression of feeling
                or wishes
              </td>
            </tr>
            <tr>
              <td>C3</td>
              <td>9. Asks for suggestion</td>
              <td>
                Requests directions, proposals
              </td>
            </tr>
            <tr>
              <td colspan="3">
                <strong>Negative Reactions</strong>
              </td>
            </tr>
            <tr>
              <td>D1</td>
              <td>10. Shows disagreement </td>
              <td>
                Passively rejects, resorts to formality, withholds
                help
              </td>
            </tr>
            <tr>
              <td>D2</td>
              <td>11. Shows tension</td>
              <td>Asks for help, withdraws</td>
            </tr>
            <tr>
              <td>D3</td>
              <td>12. Shows antagonism</td>
              <td>
                Deflates others, defends or asserts self
              </td>
            </tr>
    </table> 
    
    <h3>What can it tell you?</h3>
        
    <a name="groupA"></a>
    <h4>Comment analysis for a whole course </h4>
    <p>
      Peter White is a tutor on a science course.  He sent all the
      student assignments to OpenMentor for analysis.  Then he
      asked used the course report to investigate his use of comments
      in the feedback to students on these assignments.
    </p>
    <p>
      OpenMentor produced a graph as shown below.
    </p>
	<table id="commentA_table_1-table" class="actual-ideal table table-striped table-condensed">
	    <thead>
	        <tr> 
	             <td></td>
	             <th scope="col">Ideal</th>
	             <th scope="col">Actual</th>
	        </tr>
	    </thead>
	    <tbody>
			<tr class='bullet'><td class='bullet-label'>A</td><td class='bullet-ideal'>123</td><td class='bullet-actual'>92</td></tr>
			<tr class='bullet'><td class='bullet-label'>B</td><td class='bullet-ideal'>145</td><td class='bullet-actual'>121</td></tr>
			<tr class='bullet'><td class='bullet-label'>C</td><td class='bullet-ideal'>113</td><td class='bullet-actual'>139</td></tr>
			<tr class='bullet'><td class='bullet-label'>D</td><td class='bullet-ideal'>27</td><td class='bullet-actual'>23</td></tr>
	    </tbody>
	</table>
    <p>
      The graph indicated that Peter had given rather fewer positive 
      comments and teaching points that might be expected for the grades
      that he had given. He had also asked more questions than might
      be expected, although his use of negative comments was about
      in line.
    </p>
    <p>
      Peter could then explore the comments of each type in a little 
      more detail, looking at each student's work individually, and 
      considering whether his use of feedback needed a little refinement.
    </p>

    <a name="groupB"></a>
    <h4>Explore differences within a course</h4>
    <p>	
      Karen Green is a tutor on a business course. After assessing
      the written work for five students, she used OpenMentor to 
      compare the comment use for each student. OpenMentor produced
      four different graphs, one for each type of comment. 
    </p>
    <p>
      For the teaching points (group B) OpenMentor produced a graph as shown below.
    </p>
    <table id="commentB_table_1-table" class="actual-ideal table table-striped table-condensed">
        <thead>
            <tr> 
                 <td></td>
                 <th scope="col">Ideal</th>
                 <th scope="col">Actual</th>
            </tr>
        </thead>
        <tbody>
            <tr class='bullet'><td class='bullet-label'>09000031</td><td class='bullet-ideal'>14</td><td class='bullet-actual'>19</td></tr>
            <tr class='bullet'><td class='bullet-label'>09000032</td><td class='bullet-ideal'>10</td><td class='bullet-actual'>6</td></tr>
            <tr class='bullet'><td class='bullet-label'>09000033</td><td class='bullet-ideal'>14</td><td class='bullet-actual'>13</td></tr>
            <tr class='bullet'><td class='bullet-label'>09000034</td><td class='bullet-ideal'>18</td><td class='bullet-actual'>17</td></tr>
            <tr class='bullet'><td class='bullet-label'>09000035</td><td class='bullet-ideal'>18</td><td class='bullet-actual'>19</td></tr>
        </tbody>
    </table>
    <p>
      This graph illustrates that in the first assignment she gave more
      teaching points than expected for the mark awarded. However for the
      second student she gave less than expected. The other three were about
      right, i.e., there was very little distance from the ideal line.
    </p>
    <p>
      Examples of comments relating to teaching points by a business
      school tutor:
    </p>
    <ul class="list">
      <li>
        'I was looking for ideas here e.g. discuss strategic issues
        (internal and external and then follow with some analysis of
        the external environment).'
      </li>
      <li>
        'In order to look at the long term suitability of the
        strategies a good starting point could be the strategic issues
        the company is facing'
      </li>
      <li>
        'It would have been appropriate here to have listed the main
        strategic issues facing the company'
      </li>
    </ul>
    
    <a name="groupC"></a>
    <h4>The visual representation: bullet graphs</h4>
    
    <p>
      OpenMentor uses a visual representation called "bullet graphs"
      designed by Stephen Few to make it easy to compare actual 
      to ideal values. The actual count is shown by
      a solid bar, and the ideal count is shown by a small black line.
      When the bar and the line meet, the actual and ideal values are
      close. When there is enough data, OpenMentor shades the area 
      around the ideal value -- anything in the shaded region is close
      enough to be considered normal. When the bar is outside the shaded
      region, either too far beyond the line (actual is a lot higher)
      or a long way short of the line (actual is a lot lower) this is
      worth investigating in more detail.
    <p>
      You can read more about bullet graphs:
    </p>
    <ul class="list">
      <li>
        On Wikipedia: see: <a href="http://en.wikipedia.org/wiki/Bullet_graph">http://en.wikipedia.org/wiki/Bullet_graph</a>
      </li>
      <li>
        Use the original specification for bullet graphs: see: <a href="http://www.perceptualedge.com/articles/misc/Bullet_Graph_Design_Spec.pdf">http://www.perceptualedge.com/articles/misc/Bullet_Graph_Design_Spec.pdf</a>
      </li>
    </ul>
    </div>

    	</div>
        <g:javascript>
jQuery(document).ready(function() {
  jQuery(".actual-ideal").bulletChart();
});
        </g:javascript>
    </body>
</html>