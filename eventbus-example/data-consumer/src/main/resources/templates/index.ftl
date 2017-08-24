<#-- @ftlvariable name="context.appId" type="java.lang.String" desc="the application id"-->
<html>
<head>
    <title>Vert.x &amp; OpenShift Cluster Examples - Event Bus and SockJS</title>

    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">

</head>
<body>

<div class="container">

    <div>
        <div class="sect1">
            <h2 id="_http_booster">Eventbus and SockJS Example</h2>
            <div class="sectionbody">
                <div class="paragraph">
                    <p>This application retrieves data produced by another Vert.x application on the cluster. The
                        data is transferred into a SockJS channel (<em>i.e.</em>a web socket) and displayed on the page.
                    </p>

                    <p>Your application has the id: <code>${context.appId}</code></p>
                </div>
            </div>
        </div>

        <h3>Data:</h3>
        <pre><code id="result">Once received the data will be displayed here.</code></pre>
    </div>
</div>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script src="https://cdn.jsdelivr.net/sockjs/1.1.4/sockjs.min.js"></script>
<script src='https://cdnjs.cloudflare.com/ajax/libs/vertx/3.4.2/vertx-eventbus.js'></script>

<script>
    $(document).ready(function () {
        var eb = new EventBus('/eventbus');
        eb.onopen = function () {
            eb.registerHandler('data', function (error, msg) {
                $("#result").html("<code>" + JSON.stringify(msg.body) + "</code>");
            });
        };
    });
</script>

</body>
</html>