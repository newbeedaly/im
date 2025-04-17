<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Chat Room: ${room}</title>
    <style>
        body { font-family: Arial, sans-serif; padding: 20px; }
        #chat-box { border: 1px solid #ccc; height: 400px; overflow-y: scroll; padding: 10px; margin-bottom: 10px; }
        #message-input { width: 80%; padding: 8px; }
        button { padding: 8px; }
    </style>
    <#--<script src="https://cdnjs.cloudflare.com/ajax/libs/sockjs-client/1.6.1/sockjs.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/stomp.js/2.3.3/stomp.min.js"></script>-->
    <script src="/js/stomp.js"></script>
    <script src="/js/sockjs.js"></script>
</head>
<body>
<h2>Room: ${room}</h2>
<p>Username: ${username}</p>
<div id="chat-box"></div>
<input type="text" id="message-input" placeholder="Type a message...">
<button onclick="sendMessage()">Send</button>

<script>
    var stompClient = null;

    function connect() {
        var socket = new SockJS('/chat-websocket');
        stompClient = Stomp.over(socket);
        stompClient.connect({}, function (frame) {
            stompClient.subscribe('/topic/${room}', function (message) {
                showMessage(JSON.parse(message.body));
            });
        });
    }

    function sendMessage() {
        var messageContent = document.getElementById('message-input').value.trim();
        if (messageContent && stompClient) {
            var message = {
                username: "${username}",
                content: messageContent,
                room: "${room}"
            };
            stompClient.send("/app/chat/${room}", {}, JSON.stringify(message));
            document.getElementById('message-input').value = '';
        }
    }

    function showMessage(message) {
        var chatBox = document.getElementById('chat-box');
        var messageElement = document.createElement('p');
        messageElement.textContent = message.username + ': ' + message.content;
        chatBox.appendChild(messageElement);
        chatBox.scrollTop = chatBox.scrollHeight;
    }

    document.getElementById('message-input').addEventListener('keypress', function (e) {
        if (e.key === 'Enter') {
            sendMessage();
        }
    });

    connect();
</script>
</body>
</html>