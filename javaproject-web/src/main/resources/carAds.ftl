<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="style.css">
    <title>Car Ads</title>
</head>
<body>
    <form method="POST">
        <input type="submit" value="kijelentkezes">
    </form>

    <#list carads as carad>
        <p>${carad.id}:${carad.model}:${carad.manufacturer}:${carad.price}:${carad.quality}:${carad.yearOfMake}</p>
        <br>
    </#list>
</body>
</html>
