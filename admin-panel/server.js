const express = require("express");
const basicAuth = require("express-basic-auth");
const path = require("path");
const { Pool } = require("pg");

const app = express();
app.use(express.static(path.join(__dirname, "build")));
app.use(express.json());

const pool = new Pool();

app.get("/api/countries", (req, res) => {
  pool
    .query({ text: "SELECT name FROM countries", rowMode: "array" })
    .then(rset => res.json(rset.rows.map(item => item[0])))
    .catch(e => console.error(e.stack));
});

app.get("/api/currencies/:country", (req, res) => {
  pool
    .query("SELECT currencies::text[] FROM countries WHERE name = $1", [
      req.params.country
    ])
    .then(rset => {
      if (rset.length === 0) {
        res.status(400).end();
      } else {
        res.json(rset.rows[0]);
      }
    })
    .catch(e => console.error(e.stack));
});

app.post(
  "/api/currencies/:country",
  basicAuth({
    users: { admin: process.env.SECRET || "password" },
    challenge: true
  }),
  (req, res) => {
    // TODO: verify the currencies are in the database
    const currencies = req.body["array"];
    pool
      .query("UPDATE countries SET currencies = $1 WHERE name = $2", [
        currencies,
        req.params.country
      ])
      .then(rset => {
        if (rset.length === 0) {
          res.status(400).end();
        } else {
          res.json(rset.rows[0]);
        }
      })
      .catch(e => console.error(e.stack));
  }
);

app.get("*", (req, res) => {
  res.sendFile(path.join(__dirname, "build/index.html"));
});

const port = process.env.PORT || 5000;
app.listen(port, () => {
  console.log("Listening on port", port);
});
