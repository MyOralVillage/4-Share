const express = require("express");
const basicAuth = require("express-basic-auth");
const path = require("path");
const { Pool } = require("pg");

const app = express();
const router = express.Router();
const pool = new Pool();

app.use(express.json());

async function areCurrenciesValid(currencies) {
  const resultSet = await pool.query({
    text: "SELECT code FROM currencies",
    rowMode: "array"
  });

  const validCurrencies = resultSet.rows.map(row => row[0]);
  return currencies.every(currency => validCurrencies.indexOf(currency) >= 0);
}

app.get("/api/countries", (req, res) => {
  pool
    .query(
      "SELECT name, code, currency, currencies[1] AS def FROM countries ORDER BY name"
    )
    .then(rset => res.json(rset.rows))
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

router.post("/api/currencies/:country", async (req, res) => {
  const currencies = req.body;

  if (await areCurrenciesValid(currencies)) {
    const resultSet = await pool.query(
      "UPDATE countries SET currencies = $1 WHERE name = $2",
      [currencies, req.params.country]
    );

    if (resultSet.length === 0) {
      res.status(400).end();
    } else {
      res.json(resultSet.rows[0]);
    }
  } else {
    res.status(400).end();
  }
});

router.get("/*", (req, res) => {
  res.sendFile(path.join(__dirname, "build/index.html"));
});

app.use(
  [
    basicAuth({
      users: { admin: process.env.SECRET || "password" },
      challenge: true
    }),
    express.static("build")
  ],
  router
);

const port = process.env.PORT || 5000;
console.log(`Attaching to port ${port}...`);

app.listen(port, () => {
  console.log("Listening on port", port);
});
