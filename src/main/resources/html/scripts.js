let stompClient = null;

function joinRoom() {
    const username = document.getElementById('username').value.trim();
    const room = document.getElementById('room').value.trim();
    if (username && room) {
        window.location.href = `chat.html?room=${encodeURIComponent(room)}&username=${encodeURIComponent(username)}`;
    }
}

function getQueryParams() {
    const params = new URLSearchParams(window.location.search);
    return {
        room: params.get('room'),
        username: params.get('username')
    };
}

function initializeChat() {
    const { room, username } = getQueryParams();
    if (!room || !username) {
        window.location.href = 'index.html';
        return;
    }

    document.getElementById('room-name').textContent = room;
    document.getElementById('username').textContent = username;

    connect(room, username);

    const messageInput = document.getElementById('message-input');
    messageInput.addEventListener('keypress', (e) => {
        if (e.key === 'Enter') {
            sendMessage();
        }
    });
}

function connect(room, username) {
    const socket = new SockJS('http://localhost:8080/chat-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, () => {
        stompClient.subscribe(`/topic/${room}`, (message) => {
            showMessage(JSON.parse(message.body));
        });
    }, () => {
        console.error('Connection failed');
    });
}

function sendMessage() {
    const messageInput = document.getElementById('message-input');
    const messageContent = messageInput.value.trim();
    const { room, username } = getQueryParams();

    if (messageContent && stompClient) {
        const message = {
            username: username,
            content: messageContent,
            room: room
        };
        stompClient.send(`/app/chat/${room}`, {}, JSON.stringify(message));
        messageInput.value = '';
    }
}

function showMessage(message) {
    const chatBox = document.getElementById('chat-box');
    const messageElement = document.createElement('p');
    messageElement.textContent = `${message.username}: ${message.content}`;
    chatBox.appendChild(messageElement);
    chatBox.scrollTop = chatBox.scrollHeight;
}