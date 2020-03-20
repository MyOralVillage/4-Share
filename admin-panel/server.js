const express = require("express");
const path = require("path");
const bodyParser = require("body-parser");

const app = express();
app.use(express.static(path.join(__dirname, "build")));
app.use(bodyParser.json());

app.get("/api/countries", (req, res) => {});

app.get("/api/currencies/:country", (req, res) => {});

app.post("/api/currencies/:country", (req, res) => {});

app.get("*", (req, res) => {
  res.sendFile(path.join(__dirname, "build/index.html"));
});

const port = process.env.PORT || 5000;
app.listen(port, () => {
  console.log("Listening on port", port);
});
