const express = require("express");
const app = express();
const http = require("http");
const cors = require("cors");
const { Server } = require("socket.io");
app.use(cors());

const server = http.createServer(app);

const io = new Server(server, {
  cors: {
    origin: "http://localhost:3000",
    methods: ["GET", "POST"],
  },
});

// Example: User data stored in an array
const users = [
  { email: "example@example.com", password: "password123" },
  // Add more users here if needed
];

io.on("connection", (socket) => {
  console.log(`User Connected`);

  socket.on("disconnect", () => {
    console.log("User Disconnected");
  });

  socket.on("current_User", (userData) => {
    console.log(`Received current user data from client:`, userData);
    console.log(userData.email);
    console.log(userData.password);
    
    socket.broadcast.emit("email", userData.email);
    console.log(userData.email);
    socket.broadcast.emit("password", userData.password);
    console.log(userData.password);
    // Here you can perform any necessary actions with the received user data
  });

});

server.listen(3001, () => {
  console.log("Server running");
});
