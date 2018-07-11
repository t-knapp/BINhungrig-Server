const express = require("express");
const dishes = require("./routes/dishes");

const app = express();

app.get("/", (req, res) => {
    res.status(200).send();
});

app.use("/dishes", dishes);

app.listen(
    3000,
    () => {
        console.log("Started listen on port 3000");
    }
);