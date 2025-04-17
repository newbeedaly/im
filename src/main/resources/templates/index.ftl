<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Chat Application</title>
    <style>
        body { font-family: Arial, sans-serif; text-align: center; padding: 50px; }
        .container { max-width: 400px; margin: auto; }
        input { margin: 10px; padding: 8px; width: 200px; }
        button { padding: 8px 16px; }
    </style>
</head>
<body>
<div class="container">
    <h2>Join Chat</h2>
    <form action="/chat" method="get">
        <div>
            <label>Username:</label><br>
            <input type="text" name="username" required>
        </div>
        <div>
            <label>Room:</label><br>
            <input type="text" name="room" required>
        </div>
        <button type="submit">Join</button>
    </form>
</div>
</body>
</html>