<#-- @ftlvariable name="context.appId" type="java.lang.String" desc="the application id"-->
<html>
<head>
    <title>Vert.x &amp; OpenShift Cluster Examples - Counter</title>

    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">

</head>
<body>

<div class="container">

    <div>
        <div class="sect1">
            <h2 id="_http_booster">Shared Counter Example</h2>
            <div class="sectionbody">
                <div class="paragraph">
                    <p>This application retrieves and can also increment a counter shared among the different
                        application instances (<em>pods</em>). To retrieve the current counter value, click on the
                        <em>get</em> button. To increment and get the counter value, click on the <em>increment</em>
                        button.
                    </p>

                    <p>Your application has the id: <code>${context.appId}</code></p>
                </div>
            </div>
        </div>

        <form class="form-inline">
            <button id="retrieve" type="button" class="btn btn-success">get</button>
            <button id="increment" type="button" class="btn btn-success">increment</button>
        </form>

        <h3>Result:</h3>
        <pre><code id="result">Click on one of the button to see the result.</code></pre>
    </div>
</div>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

<script>
    $(document).ready(function () {
        $("#retrieve").click(function (e) {
            $.getJSON("/counter", function(res) {
                $("#result").text("Current counter value:" + JSON.stringify(res));
            });
            e.preventDefault();
        });

        $("#increment").click(function (e) {
            $.getJSON("/counter/inc", function(res) {
                $("#result").text("Current counter value (after increment):" + JSON.stringify(res));
            });
            e.preventDefault();
        });
    });
</script>

</body>
</html>